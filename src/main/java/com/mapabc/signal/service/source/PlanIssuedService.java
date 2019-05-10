package com.mapabc.signal.service.source;

import com.mapabc.signal.common.component.ParamEntity;
import com.mapabc.signal.common.component.Result;
import com.mapabc.signal.dao.vo.phase.PhasePlanVo;
import com.mapabc.signal.dao.vo.runplan.RunplanVo;
import com.mapabc.signal.dao.vo.sectionplan.SectionPlanVo;
import com.mapabc.signal.dao.vo.timeplan.TimePlanVo;

/**
 * @author yinguijin
 * @version 1.0
 * Created on 2019/4/28 20:21
 * @description: [相位/配时/时段/运行方案下发API接口service]
 */
public interface PlanIssuedService {

    /**
     * @description: 相位方案下发-->下发相位方案信息到路口信号机或信号系统中
     * @param param 下发的相位数据
     * @return Result 发送结果
     * @author yinguijin
     * @date 2019/4/28 20:24
    */
    Result updatePhasePlans(ParamEntity<PhasePlanVo> param);

    /**
     * @description: 配时方案下发-->下发配时方案信息到路口信号机或信号系统中
     * @param param 下发的配时数据
     * @return Result 发送结果
     * @author yinguijin
     * @date 2019/4/28 20:24
     */
    Result updateTimePlans(ParamEntity<TimePlanVo> param);

    /**
     * @description: 时段方案下发-->下发时段方案信息到路口信号机或信号系统中
     * @param param 下发的时段方案数据
     * @return Result 发送结果
     * @author yinguijin
     * @date 2019/4/28 20:24
     */
    Result updateSectionPlans(ParamEntity<SectionPlanVo> param);

    /**
     * @description: 运行计划下发-->下发运行计划信息到路口信号机或信号系统中
     * @param param 下发的运行方案数据
     * @return Result 发送结果
     * @author yinguijin
     * @date 2019/4/28 20:24
     */
    Result updateRunPlan(ParamEntity<RunplanVo> param);

}
