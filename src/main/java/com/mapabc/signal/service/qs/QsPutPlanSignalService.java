package com.mapabc.signal.service.qs;

import com.mapabc.signal.common.component.ParamEntity;
import com.mapabc.signal.common.component.Result;
import com.mapabc.signal.dao.vo.phase.PhasePlanVo;
import com.mapabc.signal.dao.vo.runplan.RunplanVo;
import com.mapabc.signal.dao.vo.sectionplan.SectionPlanVo;
import com.mapabc.signal.dao.vo.timeplan.TimePlanVo;

/**
 * @description: [青松信号机方案下发service]
 * @author yinguijin
 * @version 1.0
 * Created on 2019/5/9 17:08
 */
public interface QsPutPlanSignalService {

    /**
     * @description: 设置当前路口信号机的优化方案
     * @param param 配时方案
     * @return 请求响应结果
     * @author yinguijin 
     * @date 2019/5/9 17:13 
    */
    Result updateQsTimePlan(TimePlanVo param);

    /**
     * @description: 相位锁定控制--》设置当前路口信号机的一个或多个相位放行
     * @param signalId 信号机编号
     * @param command 1 锁定当前相位 0 取消
     * @return Result 返回结果
     * @author yinguijin
     * @date 2019/4/28 14:34
     */
    Result updateQsLockPhase(String signalId, Integer command);

    /**
     * @description: 全红控制--》设置当前路口信号机是否要执行全红
     * @param signalId 信号机编号
     * @param command 1 全红控制 0 取消全红
     * @return Result 返回结果
     * @author yinguijin
     * @date 2019/4/28 19:51
     */
    Result updateQsAllRed(String signalId, Integer command);

    /**
     * @description: 黄闪控制--》设置当前路口信号机是否要执行黄闪
     * @param signalId 信号机编号
     * @param command 1 黄闪控制 0 取消黄闪
     * @return Result 返回结果
     * @author yinguijin
     * @date 2019/4/28 19:51
     */
    Result updateQsYellowFlash(String signalId, Integer command);

    /**
     * @description: 恢复时间--》在信号机设置特殊控制之后，用于恢复时间表方案
     * @param signalId 信号机编号
     * @return Result 执行结果
     * @author yinguijin
     * @date 2019/4/29 9:45
     */
    Result updateQsNormalPlan(String signalId);

    /**
     * @description: 强制方案控制--》设置当前路口信号机执行某方案
     * @param signalId 信号机编号
     * @param timePlanId 配时方案编号
     * @param minutes 控制方案运行时长（分钟）
     * @return Result 返回结果
     * @author yinguijin
     * @date 2019/4/28 14:34
     */
    Result updateQsForcePlan(String signalId, String timePlanId, Integer minutes);


    /**
     * @description: 相位方案下发-->下发相位方案信息到路口信号机或信号系统中
     * @param param 下发的相位数据
     * @return Result 发送结果
     * @author yinguijin
     * @date 2019/4/28 20:24
     */
    Result updateQsPhasePlans(PhasePlanVo param);

    /**
     * @description: 配时方案下发-->下发配时方案信息到路口信号机或信号系统中
     * @param param 下发的配时数据
     * @return Result 发送结果
     * @author yinguijin
     * @date 2019/4/28 20:24
     */
    Result updateQsTimePlans(TimePlanVo param);

    /**
     * @description: 时段方案下发-->下发时段方案信息到路口信号机或信号系统中
     * @param param 下发的时段方案数据
     * @return Result 发送结果
     * @author yinguijin
     * @date 2019/4/28 20:24
     */
    Result updateQsSectionPlans(SectionPlanVo param);

    /**
     * @description: 运行计划下发-->下发运行计划信息到路口信号机或信号系统中
     * @param param 下发的运行方案数据
     * @return Result 发送结果
     * @author yinguijin
     * @date 2019/4/28 20:24
     */
    Result updateQsRunPlan(RunplanVo param);
}
