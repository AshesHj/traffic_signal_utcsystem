package com.mapabc.signal.dao.vo.signalalarms;

import com.mapabc.signal.common.component.BaseSignal;
import lombok.Data;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [信号机警报信息实体]
 * Created on 2019/4/22 17:14
 */
@Data
public class SignalAlarmsVo extends BaseSignal {

    //1检测器故障|2时钟故障|3电源故障|4驱动模块故障|
    //5信号灯故障|6箱门开启|7方案错误|8绿冲突|9红全熄|10行人红熄
    private String alarmId;

    //1检测器故障|2时钟故障|3电源故障|4驱动模块故障|
    //5信号灯故障|6箱门开启|7方案错误|8绿冲突|9红全熄|10行人红熄
    private String alarmDesc;

    //告警时间，yyyy-mm-dd hh24:mi:ss
    private String alarmTime;

    //结束时间，yyyy-mm-dd hh24:mi:ss
    private String endTime;

}
