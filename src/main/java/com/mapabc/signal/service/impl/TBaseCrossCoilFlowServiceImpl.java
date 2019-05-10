package com.mapabc.signal.service.impl;

import com.mapabc.signal.common.component.BaseSignal;
import com.mapabc.signal.common.component.ParamEntity;
import com.mapabc.signal.common.component.VendorResult;
import com.mapabc.signal.common.constant.Const;
import com.mapabc.signal.common.enums.BaseEnum;
import com.mapabc.signal.common.util.ListUtil;
import com.mapabc.signal.dao.mapper.TBaseCrossCoilFlowHisMapper;
import com.mapabc.signal.dao.mapper.TBaseCrossCoilFlowMapper;
import com.mapabc.signal.dao.model.TBaseCrossCoilFlow;
import com.mapabc.signal.dao.model.TBaseCrossCoilFlowHis;
import com.mapabc.signal.dao.model.TelesemeList;
import com.mapabc.signal.dao.vo.detector.Detector;
import com.mapabc.signal.dao.vo.signalvolume.SignalVolumeVo;
import com.mapabc.signal.service.TBaseCrossCoilFlowService;
import com.mapabc.signal.service.TelesemeListService;
import com.mapabc.signal.service.qs.QsGetSignalMethodService;
import com.mapabc.signal.service.source.SignalRunService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @Description: [路口线圈流量数据service实现]</p>
* @author yinguijin
* @version 1.0
* Created on 2019年05月08日
*/
@Service
public class TBaseCrossCoilFlowServiceImpl extends BaseServiceImpl<TBaseCrossCoilFlow, TBaseCrossCoilFlowMapper> implements TBaseCrossCoilFlowService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TBaseCrossCoilFlowServiceImpl.class);

    @Value("${signalamp.vendor.qs.switch}")
    private Boolean adSwitch;

    //青松接口
    @Resource
    private QsGetSignalMethodService qsGetSignalMethodService;

    //标准接口
    @Resource
    private SignalRunService signalRunService;

    //信号灯接口
    @Resource
    private TelesemeListService telesemeListService;

    //车流量历史
    @Resource
    private TBaseCrossCoilFlowHisMapper tBaseCrossCoilFlowHisMapper;

    /**
     * @description: 保存信号机流量信息
     * @author yinguijin
     * @date 2019/5/8 20:27
     */
    @Override
    public void saveCoilFlow() {
        //查询信号机列表
        Map<String, List<TelesemeList>> map = telesemeListService.selectByIds(null);
        //存储
        List<TBaseCrossCoilFlow> coilFlows = new ArrayList<>();
        for (String key : map.keySet()) {
            if (ListUtil.isEmpty(map.get(key))) {
                continue;
            }

            //开启适配服务
            if (key.equals(BaseEnum.VendorTypeEnum.QS.getNick()) && adSwitch) {
                //调用青松接口
                coilFlows = qsGetSignalMethodService.queryQsSignalVolume(map.get(key));
            } else {
                //设置参数
                ParamEntity<List<BaseSignal>> param = new ParamEntity<>();
                param.setSourceType(key);
                param.setUpdateTime(new Date());
                param.setDataContent(telesemeListService.getBaseSignals(map.get(key)));
                param.setSystemType(Const.SYSTEM_TYPE);
                //调用标准接口
                VendorResult<SignalVolumeVo> result = signalRunService.querySignalVolume(param);
                if (null == result) {
                    continue;
                }
                //封装对象进行存储
                List<SignalVolumeVo> dataContent = result.getDataContent();
                TBaseCrossCoilFlow coilFlow;
                for (SignalVolumeVo vo : dataContent) {
                    List<Detector> detectors = vo.getDetectors();
                    for (Detector detector : detectors) {
                        coilFlow = new TBaseCrossCoilFlow();
                        coilFlow.setSignalId(vo.getSignalType() + Const.SEPARATOR_UNDER_LINE + vo.getSignalId());
                        coilFlow.setSignalType(vo.getSignalType());
                        // 日期时间
                        coilFlow.setDateTime(vo.getDatetime());
                        // 时间粒度，单位：秒
                        coilFlow.setGranula(vo.getGranula());
                        // 线圈编号-检测器编号
                        coilFlow.setDetId(detector.getDetId());
                        // 总流量
                        coilFlow.setVolume(detector.getVolume());
                        // 车速
                        coilFlow.setSpeed(detector.getSpeed());
                        // 平均车长
                        coilFlow.setCarLength(detector.getLength());
                        // 时间占有率
                        coilFlow.setOccupancy(detector.getOccupancy());
                        // 车头时距
                        coilFlow.setHeadTime(detector.getHeadTime());
                        // 大型车流量
                        coilFlow.setBigVolume(detector.getBigVolume());
                        // 中型车流量
                        coilFlow.setMiddleVolume(detector.getMiddleVolume());
                        // 小型车流量
                        coilFlow.setSmallVolume(detector.getSmallVolume());
                        // 未知车型流量
                        coilFlow.setMiniVolume(detector.getMiniVolume());
                        // 排队长度
                        coilFlow.setQueueLength(null);
                        // 置信度
                        coilFlow.setCredible(null);
                        //判断重复数据
                        List<TBaseCrossCoilFlow> flows = select(coilFlow);
                        if (ListUtil.isEmpty(flows)) {
                            continue;
                        }
                        // 创建时间
                        coilFlow.setCreateTime(new Date());
                        coilFlows.add(coilFlow);
                    }
                }
            }
        }
        //删除一小时以前的数据
        myBaseMapper.deleteOneHouFlow();
        //存储流量数据
        insertList(coilFlows);
        //存储历史数据
        List<TBaseCrossCoilFlowHis> coilFlowHisList = new ArrayList<>();
        for (TBaseCrossCoilFlow coilFlow : coilFlows) {
            TBaseCrossCoilFlowHis crossCoilFlowHis = new TBaseCrossCoilFlowHis();
            BeanUtils.copyProperties(coilFlow, crossCoilFlowHis);
            coilFlowHisList.add(crossCoilFlowHis);
        }
        tBaseCrossCoilFlowHisMapper.insertList(coilFlowHisList);
    }
}
