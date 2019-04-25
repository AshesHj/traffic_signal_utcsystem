package com.mapabc.signal.dao.vo.timeplan;

import com.mapabc.signal.common.component.BaseSignal;
import lombok.Data;

import java.util.List;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [配时方案实体类]
 * Created on 2019/4/22 15:40
 */
@Data
public class TimePlanVo extends BaseSignal {

    //配时方案编号
    private String timePlanId;
    //配时方案描述
    private String timePlanDesc;
    //相位方案编号
    private String phasePlanId;
    //周期长度
    private Integer cycleLen;
    //协调相位编号
    private String coordPhaseId;
    //相位差
    private String offset;
    //环数量 1 一环 2 两环
    private String ringCount;
    //环数据对象列表
    private List<Ring> rings;
}
