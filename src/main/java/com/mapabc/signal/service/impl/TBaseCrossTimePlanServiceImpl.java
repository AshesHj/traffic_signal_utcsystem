package com.mapabc.signal.service.impl;

import com.mapabc.signal.common.component.BaseSignal;
import com.mapabc.signal.common.component.ParamEntity;
import com.mapabc.signal.common.component.VendorResult;
import com.mapabc.signal.common.constant.Const;
import com.mapabc.signal.common.enums.BaseEnum;
import com.mapabc.signal.common.util.ListUtil;
import com.mapabc.signal.dao.mapper.TBaseCrossTimePlanMapper;
import com.mapabc.signal.dao.model.TBaseCrossTimePlan;
import com.mapabc.signal.dao.model.TelesemeList;
import com.mapabc.signal.dao.vo.phase.Phase;
import com.mapabc.signal.dao.vo.timeplan.Ring;
import com.mapabc.signal.dao.vo.timeplan.Step;
import com.mapabc.signal.dao.vo.timeplan.TimePlanVo;
import com.mapabc.signal.service.TBaseCrossTimePlanService;
import com.mapabc.signal.service.TelesemeListService;
import com.mapabc.signal.service.qs.QsGetSignalMethodService;
import com.mapabc.signal.service.source.CrossingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* @Description: [路口配时方案表service实现]</p>
* @author yinguijin
* @version 1.0
* Created on 2019年05月06日
*/
@Service
public class TBaseCrossTimePlanServiceImpl extends BaseServiceImpl<TBaseCrossTimePlan, TBaseCrossTimePlanMapper> implements TBaseCrossTimePlanService {

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

    /**
     * @param param 请求参数配时方案
     * @return TimePlanVo 路口的配时方案数据集合
     * @description: 路口的配时方案信息获取
     * @author yinguijin
     * @date 2019/4/19 14:49
     */
    @Override
    public void initTimePlan(ParamEntity param) {
        List<TBaseCrossTimePlan> timeplans = new ArrayList<>();
        BaseSignal query = (BaseSignal) param.getDataContent();
        if (BaseEnum.VendorTypeEnum.QS.getNick().equalsIgnoreCase(param.getSourceType()) && adSwitch) {
            List<TelesemeList> telesemeLists = telesemeListService.selectByTeleseme(query);
            //青松接口
            timeplans = qsGetSignalMethodService.queryQsTimePlan(telesemeLists);
        } else {
            List<BaseSignal> signalList = telesemeListService.selectByBaseSignal(query);
            param.setDataContent(signalList);
            //标准接口
            VendorResult<TimePlanVo> result = crossingService.queryTimePlan(param);
            if (null == result) {
                return;
            }
            List<TimePlanVo> dataContent = result.getDataContent();
            //封装对象
            TBaseCrossTimePlan entity;
            for (TimePlanVo vo : dataContent) {
                entity = new TBaseCrossTimePlan();
                // --- 路口编号
                entity.setCrossId(vo.getSignalId());
                // --- 信号机编号
                entity.setSignalId(vo.getSignalType() + Const.SEPARATOR_UNDER_LINE + vo.getSignalId());
                // --- 信号机类型
                entity.setSignalType(vo.getSignalType());
                // --- 配时方案编号
                entity.setTimePlanId(vo.getTimePlanId());
                // --- 配时方案描述
                entity.setTimePlanDesc(vo.getTimePlanDesc());
                // --- 相位方案编号
                entity.setPhasePlanId(vo.getPhasePlanId());
                // --- 周期长度
                entity.setCycleLen(vo.getCycleLen());
                // --- 协调相位编号
                entity.setCoordPhaseId(vo.getCoordPhaseId());
                // --- 相位差
                entity.setOffset(vo.getOffset());
                // 是否双环： 1 是 0 否
                // TODO 是否双环默认 0 否
                entity.setIsDoubleRing(0);
                // --- 环编号
                // TODO 单环1；双环就顺序写1,2
                entity.setRingNo("1");
                if (ListUtil.isNotEmpty(vo.getRings())) {
                    Ring ring = vo.getRings().get(0);
                    Phase phase = ring.getPhases().get(0);
                    // ---环编号
                    entity.setRingNo(ring.getRingNo());
                    // --- 相位编号
                    entity.setPhaseId(phase.getPhaseId());
                    // --- 相位顺序序号
                    entity.setPhaseOrderId(phase.getPhaseOrderId());
                    // --- 相位时长
                    entity.setPhaseTime(phase.getPhaseTime());
                    // --- 最小绿灯时间
                    entity.setMinGreenTime(phase.getMingreenTime());
                    // --- 最大绿灯时间
                    entity.setMaxGreenTime(phase.getMaxgreenTime());
                    // --- 当前步号
                    Step step = phase.getSteps().get(0);
                    entity.setStepNo(step.getStepNo());
                    // --- 步类型
                    entity.setStepType(step.getStepType());
                    // --- 步长
                    entity.setStepLen(step.getStepLen());
                    // --- 创建时间
                    entity.setGmtCreate(new Date());
                }
                timeplans.add(entity);
            }
        }
        if (ListUtil.isEmpty(timeplans)) {
            return;
        }
        insertList(timeplans);
    }
}
