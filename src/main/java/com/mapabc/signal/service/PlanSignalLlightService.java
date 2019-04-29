package com.mapabc.signal.service;

import com.mapabc.signal.common.component.Result;

/**
 * @description: [控制指令-全红/黄灯/关灯方案下发API接口service]
 * @author yinguijin
 * @version 1.0
 * Created on 2019/4/28 19:44
 */
public interface PlanSignalLlightService {

    /**
     * @description: 全红控制--》设置当前路口信号机是否要执行全红
     * @param signalId 信号机编号
     * @param sourceType 厂家简称
     * @param signalType 信号机类型
     * @param command 1 全红控制 0 取消全红
     * @return Result 返回结果
     * @author yinguijin
     * @date 2019/4/28 19:51
    */
    Result updateAllRed(String signalId, String sourceType, String signalType, Integer command);

    /**
     * @description: 黄闪控制--》设置当前路口信号机是否要执行黄闪
     * @param signalId 信号机编号
     * @param sourceType 厂家简称
     * @param signalType 信号机类型
     * @param command 1 黄闪控制 0 取消黄闪
     * @return Result 返回结果
     * @author yinguijin
     * @date 2019/4/28 19:51
     */
    Result updateYellowFlash(String signalId, String sourceType, String signalType, Integer command);

    /**
     * @description: 关灯控制--》设置当前路口信号机是否要执行开灯或关灯
     * @param signalId 信号机编号
     * @param sourceType 厂家简称
     * @param signalType 信号机类型
     * @param command 1 开灯 0 关灯
     * @return Result 返回结果
     * @author yinguijin
     * @date 2019/4/28 19:51
     */
    Result updateOff(String signalId, String sourceType, String signalType, Integer command);
}
