package com.mapabc.signal.service;

import com.mapabc.signal.common.component.ParamEntity;
import com.mapabc.signal.common.component.Result;
import com.mapabc.signal.dao.vo.phase.PhaseLockVo;
import com.mapabc.signal.dao.vo.timeplan.TimePlanVo;

/**
 * @Description: [控制指令-相位锁定/步进/当前方案优化API接口-service]
 * @author yinguijin
 * @version 1.0
 * Created on 2019/4/28 14:17
 */
public interface PhaseSchemeService {

    /**
     * @description: 当前方案优化调整--》设置当前路口信号机的方案优化
     * @param param 配时方案实体类
     * @return Result 返回结果
     * @author yinguijin
     * @date 2019/4/28 14:34
    */
    Result updateTimePlan(ParamEntity<TimePlanVo> param);

    /**
     * @description: 相位锁定控制--》设置当前路口信号机的一个或多个相位放行
     * @param param 相位锁定实体类
     * @return Result 返回结果
     * @author yinguijin
     * @date 2019/4/28 14:34
     */
    Result updateLockPhase(ParamEntity<PhaseLockVo> param);

    /**
     * @description: 相位步进控制--》设置当前路口信号机由一个相位向下一个相位或后续某个相位进行平滑过渡
     * @param sourceType 厂家简称 QS/SCATS/HS/HK
     * @param signalId 信号机编号
     * @param signalType 信号机类型 QS/SCATS/HS/HK
     * @param command 1 开始步进 0 取消步进
     * @param stepNum 0 顺序步进 n 跳过n个相位
     * @return Result 返回结果
     * @author yinguijin
     * @date 2019/4/28 14:34
     */
    Result updateStep(String signalId, String sourceType, String signalType, int command, int stepNum);

    /**
     * @description: 强制方案控制--》设置当前路口信号机执行某方案
     * @param sourceType 厂家简称 QS/SCATS/HS/HK
     * @param signalId 信号机编号
     * @param signalType 信号机类型 QS/SCATS/HS/HK
     * @param timePlanId 配时方案编号
     * @return Result 返回结果
     * @author yinguijin
     * @date 2019/4/28 14:34
     */
    Result updateForcePlan(String signalId, String sourceType, String signalType, String timePlanId);

}
