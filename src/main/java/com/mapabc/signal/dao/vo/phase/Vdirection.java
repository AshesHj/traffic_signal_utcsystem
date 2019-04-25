package com.mapabc.signal.dao.vo.phase;

import lombok.Data;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [机动车方向]
 * Created on 2019/4/19 17:20
 */
@Data
public class Vdirection {

    //方向, 方向：0 北 1 东北 2 东 3 东南 4 南 5 西南 6 西 7 西北
    private Integer direction;

    //车辆类型
    private Integer turnType;

}
