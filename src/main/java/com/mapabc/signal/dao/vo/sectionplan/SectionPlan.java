package com.mapabc.signal.dao.vo.sectionplan;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [路口时段方案实体]
 * Created on 2019/4/22 16:20
 */
@Data
@ApiModel(value = "SectionPlan", description = "路口时段方案实体")
public class SectionPlan {

    //时段方案编号
    @ApiModelProperty(value = "时段方案编号")
    private String sectionPlanId;

    //时段方案描述
    @ApiModelProperty(value = "时段方案描述")
    private String sectionPlanDesc;


    //时段数据列表
    @ApiModelProperty(value = "时段数据列表")
    private List<Timeslice> timeslices;

}
