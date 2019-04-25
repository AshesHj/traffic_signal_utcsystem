package com.mapabc.signal.dao.vo.timeplan;

import lombok.Data;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [步数据对象]
 * Created on 2019/4/22 15:56
 */
@Data
public class Step {
    //步号：1,2,3,4,5,6
    private Integer stepNo;
    //步类型：1 green 2 greenflash 3 pedflash 4 yellow 5 red 6 off
    private String stepType;
    //步长
    private Integer stepLen;

}
