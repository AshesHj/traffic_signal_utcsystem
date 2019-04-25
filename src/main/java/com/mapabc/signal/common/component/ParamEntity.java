package com.mapabc.signal.common.component;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [描述该类概要功能介绍]
 * Created on 2019/4/24 21:35
 */
@Data
public class ParamEntity<T> {

    /**
     * “UTC”:城市交通信号控制系统简称
     */
    private String systemType;

    /**
     * 厂家简称
     */
    private String sourceType;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 返回值
     */
    private T dataContent;
}
