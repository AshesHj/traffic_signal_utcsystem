package com.mapabc.signal.dao.model;

import lombok.Data;

import java.util.Date;

/**
 * @description: [操作日志信息实体类]
 * @author yinguijin
 * @date 2019/4/17 12:45
 */
@Data
public class OperateLog {

    /**
     * 访问的url
     */
    private String url;

    /**
     * 登录ip
     */
    private String ip;

    /**
     * 操作类型(增删改查)
     */
    private String operationType;

    /**
     * 响应状态码:如200、500等
     */
    private String status;

    /**
     * 请求参数
     */
    private String args;

    /**
     * 操作时间
     */
    private Date createTime;

    /**
     * 操作信息
     */
    private String description;

}