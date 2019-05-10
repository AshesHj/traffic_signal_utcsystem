package com.mapabc.signal.dao.vo.timeplan;

import com.mapabc.signal.common.component.BaseSignal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [配时方案实体类]
 * Created on 2019/4/22 15:40
 */
@Data
@ApiModel(value = "TimePlanVo", description = "配时方案实体类")
public class TimePlanVo extends BaseSignal {

    //配时方案编号
    @ApiModelProperty(value = "配时方案编号", required = true)
    private String timePlanId;
    //配时方案描述
    @ApiModelProperty(value = "配时方案描述")
    private String timePlanDesc;
    //相位方案编号
    @ApiModelProperty(value = "相位方案编号")
    private String phasePlanId;
    //周期长度
    @ApiModelProperty(value = "周期长度")
    private Integer cycleLen;
    //协调相位编号
    @ApiModelProperty(value = "协调相位编号")
    private String coordPhaseId;
    //相位差
    @ApiModelProperty(value = "相位差")
    private Integer offset;
    //环数量 1 一环 2 两环
    @ApiModelProperty(value = "环数量 1 一环 2 两环")
    private String ringCount;
    //环数据对象列表
    @ApiModelProperty(value = "环数据对象列表")
    private List<Ring> rings;
}
