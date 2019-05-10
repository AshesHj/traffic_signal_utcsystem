package com.mapabc.signal.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mapabc.signal.common.component.BaseSignal;
import com.mapabc.signal.common.component.ParamEntity;
import com.mapabc.signal.common.component.VendorResult;
import com.mapabc.signal.common.constant.Const;
import com.mapabc.signal.common.enums.BaseEnum;
import com.mapabc.signal.common.util.ListUtil;
import com.mapabc.signal.dao.mapper.TBaseCrossRunPlanMapper;
import com.mapabc.signal.dao.model.TBaseCrossRunPlan;
import com.mapabc.signal.dao.model.TelesemeList;
import com.mapabc.signal.dao.vo.runplan.RunplanVo;
import com.mapabc.signal.service.TBaseCrossRunPlanService;
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
* @Description: [路口运行计划表service实现]</p>
* @author yinguijin
* @version 1.0
* Created on 2019年05月06日
*/
@Service
public class TBaseCrossRunPlanServiceImpl extends BaseServiceImpl<TBaseCrossRunPlan, TBaseCrossRunPlanMapper> implements TBaseCrossRunPlanService {

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
     * @param param 请求参数时段方案
     * @description: 保存路口的运行计划表
     * @author yinguijin
     * @date 2019/4/19 14:49
     */
    @Override
    public void initRunPlan(ParamEntity param) {
        List<TBaseCrossRunPlan> runPlans = new ArrayList<>();
        BaseSignal query = (BaseSignal) param.getDataContent();
        if (BaseEnum.VendorTypeEnum.QS.getNick().equalsIgnoreCase(param.getSourceType()) && adSwitch) {
            List<TelesemeList> telesemeLists = telesemeListService.selectByTeleseme(query);
            //青松接口
            runPlans = qsGetSignalMethodService.queryQsRunPlan(telesemeLists);
        } else {
            List<BaseSignal> signalList = telesemeListService.selectByBaseSignal(query);
            param.setDataContent(signalList);
            //标准接口
            VendorResult<RunplanVo> result = crossingService.queryRunPlan(param);
            if (null == result) {
                return;
            }
            List<RunplanVo> dataContent = result.getDataContent();
            //封装对象
            TBaseCrossRunPlan entity;
            for (RunplanVo vo : dataContent) {
                //星期运行方案
                JSONArray weeks = vo.getWeeks();
                if (null != weeks) {
                    for (int i = 0; i < weeks.size(); i++) {
                        JSONObject week = weeks.getJSONObject(i);
                        entity = new TBaseCrossRunPlan();
                        entity.setCrossId(vo.getSignalId());//路口编号
                        entity.setSignalId(vo.getSignalType() + Const.SEPARATOR_UNDER_LINE + vo.getSignalId());//信号机编号
                        entity.setSignalType(vo.getSignalType());//信号机类型
                        entity.setWeekNum(week.getInteger("weeknum"));//星期
                        entity.setSectionPlanId(week.getString("sectionplanid"));//时段方案ID
                        entity.setGmtCreate(new Date());//创建时间
                        runPlans.add(entity);
                    }
                }
                //特殊日期处理
                JSONArray specialdays = vo.getSpecialdays();
                if (null != specialdays) {
                    for (int i = 0; i < specialdays.size(); i++) {
                        JSONObject specialday = specialdays.getJSONObject(i);
                        entity = new TBaseCrossRunPlan();
                        entity.setCrossId(vo.getSignalId());//路口编号
                        entity.setSignalId(vo.getSignalType() + Const.SEPARATOR_UNDER_LINE + vo.getSignalId());//信号机编号
                        entity.setSignalType(vo.getSignalType());//信号机类型
                        entity.setDateStr(specialday.getString("datestr"));//特殊日期
                        entity.setSectionPlanId(specialday.getString("sectionplanid"));//时段方案ID
                        entity.setGmtCreate(new Date());//创建时间
                        runPlans.add(entity);
                    }
                }
            }
        }
        if (ListUtil.isEmpty(runPlans)) {
            return;
        }
        insertList(runPlans);
    }
}
