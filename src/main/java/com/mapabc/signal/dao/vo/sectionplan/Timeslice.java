package com.mapabc.signal.dao.vo.sectionplan;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [时段数据实体]
 * Created on 2019/4/22 16:22
 */
@Data
@ApiModel(value = "Timeslice", description = "时段数据实体")
public class Timeslice {

    //时段编号
    @ApiModelProperty(value = "时段编号")
    private String timesliceid;

    //时段描述
    @ApiModelProperty(value = "时段描述")
    private String timeslicedesc;

    //时段顺序号
    @ApiModelProperty(value = "时段顺序号")
    private Integer timesliceorderid;

    //开始时间，格式 HH:MI:00
    @ApiModelProperty(value = "开始时间，格式 HH:MI:00")
    private String starttime;

    //配时方案编号
    @ApiModelProperty(value = "配时方案编号")
    private String timeplanid;

}
