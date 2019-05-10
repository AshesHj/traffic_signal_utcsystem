package com.mapabc.signal.service.impl;

import com.mapabc.signal.common.component.BaseSignal;
import com.mapabc.signal.common.component.ParamEntity;
import com.mapabc.signal.common.component.VendorResult;
import com.mapabc.signal.common.constant.Const;
import com.mapabc.signal.common.enums.BaseEnum;
import com.mapabc.signal.common.util.ListUtil;
import com.mapabc.signal.dao.mapper.TBaseCrossPhasePlanMapper;
import com.mapabc.signal.dao.model.TBaseCrossPhasePlan;
import com.mapabc.signal.dao.model.TelesemeList;
import com.mapabc.signal.dao.vo.phase.*;
import com.mapabc.signal.service.TBaseCrossPhasePlanService;
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
* @Description: [路口相位方案表service实现]</p>
* @author yinguijin
* @version 1.0
* Created on 2019年05月06日
*/
@Service
public class TBaseCrossPhasePlanServiceImpl extends BaseServiceImpl<TBaseCrossPhasePlan, TBaseCrossPhasePlanMapper> implements TBaseCrossPhasePlanService {

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
     * @description: 保存路口相位方案数据
     * @param param 请求参数相位方案
     * @return PhasePlanVo 相位方案数据集合
     * @author yinguijin
     * @date 2019/4/19 14:49
     */
    @Override
    public void initPhasePlan(ParamEntity param) {
        List<TBaseCrossPhasePlan> phasePlans = new ArrayList<>();
        BaseSignal query = (BaseSignal) param.getDataContent();
        if (BaseEnum.VendorTypeEnum.QS.getNick().equalsIgnoreCase(param.getSourceType()) && adSwitch) {
            //青松接口
            List<TelesemeList> telesemeLists = telesemeListService.selectByTeleseme(query);
            phasePlans = qsGetSignalMethodService.queryQsPhasePlan(telesemeLists);
        } else {
            List<BaseSignal> signalList = telesemeListService.selectByBaseSignal(query);
            param.setDataContent(signalList);
            //标准接口
            VendorResult<PhasePlanVo> result = crossingService.queryPhasePlan(param);
            if (null == result) {
                return;
            }
            List<PhasePlanVo> dataContent = result.getDataContent();
            //封装对象
            TBaseCrossPhasePlan crossPhase;
            for (PhasePlanVo vo : dataContent) {
                for (PhasePlan teleseme : vo.getPhasePlans()) {
                    for (Phase phase : teleseme.getPhases()) {
                        crossPhase = new TBaseCrossPhasePlan();
                        // 路口编号
                        crossPhase.setCrossId(vo.getSignalId());
                        // 信号机编号
                        crossPhase.setSignalId(vo.getSignalType() + Const.SEPARATOR_UNDER_LINE + vo.getSignalId());
                        // 信号机类型
                        crossPhase.setSignalType(vo.getSignalType());

                        if (ListUtil.isNotEmpty(phase.getVdirections())) {
                            Vdirection vdirection = phase.getVdirections().get(0);
                            // 方位方向
                            crossPhase.setDirection(vdirection.getDirection().toString());
                            // 机动车/行人
                            crossPhase.setDirobjType("01");
                            // 转向方向
                            crossPhase.setTurnType(vdirection.getTurnType().toString());
                        }
                        if (ListUtil.isNotEmpty(phase.getPdirections())) {
                            Pdirection pdirection = phase.getPdirections().get(0);
                            // 方位方向
                            crossPhase.setDirection(pdirection.getDirection().toString());
                            // 机动车/行人
                            crossPhase.setDirobjType("02");
                            // 进出方向
                            crossPhase.setInoutType(pdirection.getInoutType().toString());
                            // 是否二次行人过街：1 是 0 否
                            crossPhase.setIsSecond(pdirection.getSecond().toString());
                        }
                        // 最小绿灯时间
                        crossPhase.setMinGreenTime(phase.getMingreenTime());
                        // 最大绿灯时间
                        crossPhase.setMaxGreenTime(phase.getMaxgreenTime());
                        // 相位方案编号
                        crossPhase.setPhasePlanId(teleseme.getPhasePlanId());
                        // 相位方案描述
                        crossPhase.setPahsePlanDesc(teleseme.getPahsePlanDesc());
                        // 相位编号
                        crossPhase.setPhaseId(phase.getPhaseId());
                        crossPhase.setPhaseName(phase.getPhaseName());
                        crossPhase.setPhaseDesc(phase.getPhaseDesc());
                        // 创建时间
                        crossPhase.setGmtCreate(new Date());
                        phasePlans.add(crossPhase);
                    }
                }
            }
        }
        if (ListUtil.isEmpty(phasePlans)) {
            return;
        }
        insertList(phasePlans);
    }
}
