package com.mapabc.signal.common.component;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * @description: [操作日志信息实体类]
 * @author yinguijin
 * @date 2019/4/17 12:45
 */
@Data
@Table(name = "t_base_operate_log")
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
    @Column(name = "operation_type")
    private String operationType;

    /**
     * 响应状态码:如200、500等
     */
    private String status;

    /**
     * 响应结果
     */
    private String result;

    /**
     * 请求参数
     */
    private String args;

    /**
     * 操作时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 操作信息
     */
    private String description;

}