package com.mapabc.signal.dao.vo.detector;

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
    private Integer direction;
    //单位时间粒度内的流量，小车当量
    private Integer volume;
    //平均速度
    private Double speed;
    //时间占有率
    private Double occupancy;
    //车头间距
    private Double headDistance;
    //车头时距
    private Double headTime;
    //平均车长
    private Double length;
    //饱和度
    private Double saturation;
    //微型车流量
    private Integer miniVolume;
    //小车流量
    private Integer smallVolume;
    //中车流量
    private Integer middleVolume;
    //大车流量
    private Integer bigVolume;
    //挂车流量
    private Integer largeVolume;
}
