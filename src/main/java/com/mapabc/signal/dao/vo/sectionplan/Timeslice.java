package com.mapabc.signal.dao.vo.sectionplan;

import lombok.Data;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [时段数据实体]
 * Created on 2019/4/22 16:22
 */
@Data
public class Timeslice {
    //时段编号
    private String timesliceid;
    //时段描述
    private String timeslicedesc;
    //时段顺序号
    private String timesliceorderid;
    //开始时间，格式 HH:MI:00
    private String starttime;
    //配时方案编号
    private String timeplanid;

}
