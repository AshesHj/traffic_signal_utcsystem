package com.mapabc.signal.service;

import com.alibaba.fastjson.JSONObject;
import com.mapabc.signal.common.component.BaseSignal;
import com.mapabc.signal.common.component.ParamEntity;
import com.mapabc.signal.common.component.VendorResult;
import com.mapabc.signal.dao.vo.cross.CrossingVo;
import com.mapabc.signal.dao.vo.detector.DetectorVo;
import com.mapabc.signal.dao.vo.phase.PhasePlanVo;
import com.mapabc.signal.dao.vo.runplan.RunplanVo;
import com.mapabc.signal.dao.vo.sectionplan.SectionPlanVo;
import com.mapabc.signal.dao.vo.timeplan.TimePlanVo;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [信号灯静态数据获取接口-控制路口Service]
 * Created on 2019/4/18 18:43
 */
public interface CrossingService {

    JSONObject login();

    /**
     * @description: 获取信号控制系统所有控制路口基本信息
     * @return CrossingVo 路口信息结果集合
     * @author yinguijin
     * @date 2019/4/19 14:48
    */
    VendorResult<CrossingVo> queryCrossing(ParamEntity<BaseSignal> param);

    /**
     * @description: 获取路口的线圈信息
     * @param param  请求参数信号灯信息
     * @return  DetectorVo 线圈信息结果集合
     * @author yinguijin 
     * @date 2019/4/19 14:49 
    */
    VendorResult<DetectorVo> queryDetector(ParamEntity<BaseSignal> param);

    /**
     * @description: 路口相位方案数据获取
     * @param param  请求参数相位方案
     * @return  PhasePlanVo 相位方案数据集合
     * @author yinguijin
     * @date 2019/4/19 14:49
     */
    VendorResult<PhasePlanVo> queryPhasePlan(ParamEntity<BaseSignal> param);

    /**
     * @description: 路口的配时方案信息获取
     * @param param  请求参数配时方案
     * @return  TimePlanVo 路口的配时方案数据集合
     * @author yinguijin
     * @date 2019/4/19 14:49
     */
    VendorResult<TimePlanVo> queryTimePlan(ParamEntity<BaseSignal> param);

    /**
     * @description: 路口的时段方案（日时段方案）获取
     * @param param  请求参数时段方案
     * @return  SectionPlanVo 路口的时段方案数据集合
     * @author yinguijin
     * @date 2019/4/19 14:49
     */
    VendorResult<SectionPlanVo> querySectionPlan(ParamEntity<BaseSignal> param);

    /**
     * @description: 路口的运行计划表获取
     * @param param  请求参数时段方案
     * @return  RunplanVo 路口的运行计划数据集合
     * @author yinguijin
     * @date 2019/4/19 14:49
     */
    VendorResult<RunplanVo> queryRunplan(ParamEntity<BaseSignal> param);
}
