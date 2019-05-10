package com.mapabc.signal.service.qs;

import com.mapabc.signal.common.component.BaseSignal;
import com.mapabc.signal.dao.model.*;
import com.mapabc.signal.dao.vo.signal.SignalInfoVo;
import com.mapabc.signal.dao.vo.signalrunstatus.SignalRunStatusVo;

import java.util.List;

/**
 * @author yinguijin
 * @version 1.0
 * Created on 2019/4/30 9:57
 * @description: [青松信号灯厂商接口service]
 */
public interface QsGetSignalMethodService {

    /**
     * @description: 登录青松接口服务
     * @author yinguijin
     * @date 2019/4/30 10:22
    */
    void login();

    /**
     * @description: 获取青松信号机列表
     * @return com.mapabc.signal.common.component.VendorResult<com.mapabc.signal.dao.vo.cross.CrossingVo>
     * @author yinguijin
     * @date 2019/4/30 11:09
     */
    List<TelesemeList> queryQsCrossing();

    /**
     * @description: 获取青松信号机灯组信息列表
     * @param telesemeLists 信号机集合
     * @return TCrossLights
     * @author yinguijin
     * @date 2019/4/30 11:09
     */
    List<TCrossLights> queryQsCrossLights(List<TelesemeList> telesemeLists);

    /**
     * @description: 获取青松路口的时段方案（日时段方案）
     * @param telesemeLists  信号灯集合
     * @return  TBasePlanSectime 路口的时段方案数据集合
     * @author yinguijin
     * @date 2019/4/19 14:49
     */
    List<TBaseCrossSectionPlan> queryQsSectionPlan(List<TelesemeList> telesemeLists);

    /**
     * @description: 查询青松路口相位方案信息
     * @param telesemeLists 信号机集合
     * @return java.util.List<com.mapabc.signal.dao.model.TBaseCrossPhasePlan>
     * @author yinguijin
     * @date 2019/5/5 15:18
     */
    List<TBaseCrossPhasePlan> queryQsPhasePlan(List<TelesemeList> telesemeLists);

    /**
     * @description: 路口的运行计划表获取
     * @param telesemeLists  信号机集合
     * @return  RunplanVo 路口的运行计划数据集合
     * @author yinguijin
     * @date 2019/4/19 14:49
     */
    List<TBaseCrossRunPlan> queryQsRunPlan(List<TelesemeList> telesemeLists);

    /**
     * @description: 获取相位配时方案信息
     * @param telesemeLists 信号灯列表
     * @return java.util.List<com.mapabc.signal.dao.model.TBaseCrossTimePlan> 相位配时方案集合
     * @author yinguijin
     * @date 2019/5/6 15:08
     */
    List<TBaseCrossTimePlan> queryQsTimePlan(List<TelesemeList> telesemeLists);

/**
 * =======================================================================================
 * ==========================实时动态数据接口================================================
 * =======================================================================================
 */

    /**
     * @description: 获取路口信号机的实时状态数据
     * @param baseSignals 信号机列表
     * @return SignalInfoVo 信号机状态
     * @author yinguijin
     * @date 2019/5/7 15:50
     */
    List<SignalRunStatusVo> queryQsRunStatus(List<BaseSignal> baseSignals);

    /**
     * @description: 获取路口信号机的实时灯态数据
     * @param baseSignals 信号机列表
     * @return SignalInfoVo 信号机状态
     * @author yinguijin
     * @date 2019/5/7 15:50
    */
    List<SignalInfoVo> queryQsSignalInfo(List<BaseSignal> baseSignals);

    /**
     * @description: 获取路口信号机的实时流量数据
     * @param telesemeLists 信号机列表
     * @return SignalVolumeVo 信号机的实时流量
     * @author yinguijin
     * @date 2019/4/22 17:09
     */
    List<TBaseCrossCoilFlow> queryQsSignalVolume(List<TelesemeList> telesemeLists);
}
