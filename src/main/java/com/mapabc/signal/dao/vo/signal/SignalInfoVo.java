package com.mapabc.signal.dao.vo.signal;

import com.mapabc.signal.common.component.BaseSignal;
import lombok.Data;

import java.util.List;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [信号灯态基础信息实体]
 * Created on 2019/4/22 17:34
 */
@Data
public class SignalInfoVo extends BaseSignal {
    //运行模式
    private String runMode;
    //相位方案号
    private String phasePlanId;
    //相位配时方案
    private String timePlanId;
    //方案开始时间
    private String planStartTime;
    //方案运行时间
    private Integer runTime;
    //运行环数组
    private List<SignalRunring> runrings;
}
