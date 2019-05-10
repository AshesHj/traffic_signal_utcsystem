package com.mapabc.signal.service.impl;

import com.mapabc.signal.common.component.BaseSignal;
import com.mapabc.signal.common.component.ParamEntity;
import com.mapabc.signal.common.component.VendorResult;
import com.mapabc.signal.common.constant.Const;
import com.mapabc.signal.common.enums.BaseEnum;
import com.mapabc.signal.common.util.ListUtil;
import com.mapabc.signal.dao.mapper.TBaseCrossSectionPlanMapper;
import com.mapabc.signal.dao.model.TBaseCrossSectionPlan;
import com.mapabc.signal.dao.model.TelesemeList;
import com.mapabc.signal.dao.vo.sectionplan.SectionPlan;
import com.mapabc.signal.dao.vo.sectionplan.SectionPlanVo;
import com.mapabc.signal.dao.vo.sectionplan.Timeslice;
import com.mapabc.signal.service.TBaseCrossSectionPlanService;
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
* @Description: [路口时段方案表service实现]</p>
* @author yinguijin
* @version 1.0
* Created on 2019年05月06日
*/
@Service
public class TBaseCrossSectionPlanServiceImpl extends BaseServiceImpl<TBaseCrossSectionPlan, TBaseCrossSectionPlanMapper> implements TBaseCrossSectionPlanService {

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
     * @description: 保存路口的时段方案（日时段方案）
     * @param param 请求参数时段方案
     * @author yinguijin
     * @date 2019/4/19 14:49
     */
    @Override
    public void initSectionPlan(ParamEntity param) {
        List<TBaseCrossSectionPlan> sectionPlans = new ArrayList<>();
        BaseSignal query = (BaseSignal) param.getDataContent();
        if (BaseEnum.VendorTypeEnum.QS.getNick().equalsIgnoreCase(param.getSourceType()) && adSwitch) {
            //获取信号机列表
            List<TelesemeList> telesemeLists = telesemeListService.selectByTeleseme(query);
            //青松接口
            sectionPlans = qsGetSignalMethodService.queryQsSectionPlan(telesemeLists);
        } else {
            List<BaseSignal> signalList = telesemeListService.selectByBaseSignal(query);
            param.setDataContent(signalList);
            //标准接口
            VendorResult<SectionPlanVo> result = crossingService.querySectionPlan(param);
            if (null == result) {
                return;
            }
            List<SectionPlanVo> dataContent = result.getDataContent();
            //封装对象
            TBaseCrossSectionPlan entity;
            for (SectionPlanVo vo : dataContent) {
                List<SectionPlan> plans = vo.getSectionPlans();
                for (SectionPlan plan : plans) {
                    List<Timeslice> timeslices = plan.getTimeslices();
                    //排序
                    ListUtil.sortList(timeslices, "timesliceorderid", ListUtil.SORT_ASC);
                    for (int i = 0; i < timeslices.size(); i++) {
                        Timeslice timeslice = timeslices.get(i);
                        // 结束时间
                        String endTime = null;
                        if (i < timeslices.size() - 1) {
                            endTime = timeslices.get(i + 1).getStarttime();
                        }
                        entity = new TBaseCrossSectionPlan();
                        // --- 路口编号
                        entity.setCrossId(vo.getSignalId());
                        // 信号机编号
                        entity.setSignalId(vo.getSignalType() + Const.SEPARATOR_UNDER_LINE + vo.getSignalId());
                        // 信号机类型
                        entity.setSignalType(vo.getSignalType());
                        // 方案号-配时方案编号
                        entity.setTimePlanId(timeslice.getTimeplanid());
                        // 时段方案编号
                        entity.setSectionPlanId(plan.getSectionPlanId());
                        // 时段方案描述
                        entity.setSectionPlanDesc(plan.getSectionPlanDesc());
                        // 时段顺序号
                        entity.setTimesLiceOrderId(timeslice.getTimesliceorderid());
                        // 时段开始时间
                        entity.setStartTime(timeslice.getStarttime());
                        // 时段结束时间
                        entity.setEndTime(endTime);
                        // 时段编号
                        entity.setTimesLiceId(timeslice.getTimesliceid());
                        entity.setTimesLiceDesc(timeslice.getTimeslicedesc());
                        //创建时间
                        entity.setGmtCreate(new Date());
                        sectionPlans.add(entity);
                    }
                }
            }
        }
        if (ListUtil.isEmpty(sectionPlans)) {
            return;
        }
        insertList(sectionPlans);
    }
}
