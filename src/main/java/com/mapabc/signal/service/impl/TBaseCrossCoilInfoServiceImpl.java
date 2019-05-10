package com.mapabc.signal.service.impl;

import com.mapabc.signal.common.component.BaseSignal;
import com.mapabc.signal.common.component.ParamEntity;
import com.mapabc.signal.common.component.VendorResult;
import com.mapabc.signal.common.constant.Const;
import com.mapabc.signal.common.enums.BaseEnum;
import com.mapabc.signal.common.util.ListUtil;
import com.mapabc.signal.dao.mapper.TBaseCrossCoilInfoMapper;
import com.mapabc.signal.dao.model.TBaseCrossCoilInfo;
import com.mapabc.signal.dao.vo.detector.Detector;
import com.mapabc.signal.dao.vo.detector.DetectorVo;
import com.mapabc.signal.service.TBaseCrossCoilInfoService;
import com.mapabc.signal.service.TelesemeListService;
import com.mapabc.signal.service.qs.QsGetSignalMethodService;
import com.mapabc.signal.service.source.CrossingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @Description: [路口线圈信息表service实现]</p>
* @author yinguijin
* @version 1.0
* Created on 2019年05月08日
*/
@Service
public class TBaseCrossCoilInfoServiceImpl extends BaseServiceImpl<TBaseCrossCoilInfo, TBaseCrossCoilInfoMapper> implements TBaseCrossCoilInfoService {

    @Value("${signalamp.vendor.qs.switch}")
    private Boolean adSwitch;

    //青松接口
    @Resource
    private QsGetSignalMethodService qsGetSignalMethodService;

    //标准接口
    @Resource
    private CrossingService crossingService;

    //信号灯接口
    @Resource
    private TelesemeListService telesemeListService;


    @Async
    @Override
    public void initDetector(ParamEntity param) {
        List<TBaseCrossCoilInfo> coilInfos = new ArrayList<>();
        BaseSignal query = (BaseSignal) param.getDataContent();
        if (BaseEnum.VendorTypeEnum.QS.getNick().equalsIgnoreCase(param.getSourceType()) && adSwitch) {
            //青松接口 TODO
            return;
        } else {
            List<BaseSignal> signalList = telesemeListService.selectByBaseSignal(query);
            param.setDataContent(signalList);
            //标准接口
            VendorResult<DetectorVo> result = crossingService.queryDetector(param);
            if (null == result) {
                return;
            }
            List<DetectorVo> dataContent = result.getDataContent();
            TBaseCrossCoilInfo entity;
            for (DetectorVo vo : dataContent) {
                //获取接口下线圈信息
                List<Detector> detectors = vo.getDetectors();
                if (ListUtil.isEmpty(detectors)) {
                    continue;
                }
                //组装结果
                for (Detector detector : detectors) {
                    entity = new TBaseCrossCoilInfo();
                    //信号机类型
                    entity.setSignalType(vo.getSignalType());
                    //信号机ID
                    entity.setSignalId(vo.getSignalType() + Const.SEPARATOR_UNDER_LINE + vo.getSignalId());
                    //线圈编号
                    entity.setDetId(detector.getDetId());
                    //车道所在进口方向
                    entity.setDirection(detector.getDirection());
                    //车道编号
                    entity.setLaneId(detector.getLaneId());
                    coilInfos.add(entity);
                }
            }
        }
        if (ListUtil.isEmpty(coilInfos)) {
            return;
        }
        insertList(coilInfos);
    }
}
