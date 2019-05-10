package com.mapabc.signal.service.source;

import com.mapabc.signal.common.component.Result;

/**
 * @description: [控制指令-方案计划恢复service]
 * @author yinguijin
 * @version 1.0
 * Created on 2019/4/29 9:45
 */
public interface PlanRestoreService {

    /**
     * @description: 恢复时间--》在信号机设置特殊控制之后，用于恢复时间表方案
     * @param sourceType 厂家简称 QS/SCATS/HS/HK
     * @param signalId 信号机编号
     * @param signalType 信号机类型 QS/SCATS/HS/HK
     * @param command 1 恢复
     * @return Result 执行结果
     * @author yinguijin
     * @date 2019/4/29 9:45
     */
    Result updateNormalPlan(String signalId, String sourceType, String signalType, int command);
}
