package com.mapabc.signal.dao.model.detector;

import lombok.Data;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [检测器实体]
 * Created on 2019/4/19 10:15
 */
@Data
public class Detector {
    //检测器编号
    private String detId;
    //车道编号
    private String laneId;
    //车道所在进口方向, 方向：0 北 1 东北 2 东 3 东南 4 南 5 西南 6 西 7 西北
    private String direction;

}
