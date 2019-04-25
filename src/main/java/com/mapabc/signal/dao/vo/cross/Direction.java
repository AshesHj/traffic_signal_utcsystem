package com.mapabc.signal.dao.vo.cross;

import lombok.Data;

import java.util.List;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [路口进口方向对象实体]
 * Created on 2019/4/19 10:08
 */
@Data
public class Direction {

    //方向：0 北 1 东北 2 东 3 东南 4 南 5 西南 6 西 7 西北
    private Integer direction;

    //道路信息列表
    private List<Road> roads;
}
