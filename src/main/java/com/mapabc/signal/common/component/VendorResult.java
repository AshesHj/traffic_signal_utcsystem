package com.mapabc.signal.common.component;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [交通信号调用厂商接口返回信息实体]
 * Created on 2019/4/18 18:29
 */
@Data
public class VendorResult<T> {

    /**
     * “UTC”:城市交通信号控制系统简称
     */
    private String systemType;

    /**
     * 厂家简称
     */
    private String sourceType;

    /**
     * 记录数量
     */
    private Integer dataCount;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 返回值集合
     */
    private List<T> dataContent;

}
