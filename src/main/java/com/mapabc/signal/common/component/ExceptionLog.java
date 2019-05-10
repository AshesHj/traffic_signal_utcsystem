package com.mapabc.signal.common.component;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * @description: [异常信息实体类]
 * @author yinguijin
 * @date 2019/4/17 12:45
*/
@Data
@Table(name = "t_base_exception_log")
public class ExceptionLog {

	private Long id;

	/**
	 * 请求地址
	 **/
	private String uri;

	/**
	 * 操作系统
	 **/
	private String system;

	/**
	 * 操作人ip
	 **/
	@Column(name = "operator_ip")
	private String operatorIp;

	/**
	 * 错误描述
	 **/
	private String description;

	/**
	 * 主机
	 **/
	@Column(name = "host_name")
	private String hostName;

	/**
	 * 错误详细
	 **/
	private String detail;

	/**
	 * 浏览器信息
	 **/
	@Column(name = "brower_message")
	private String browerMessage;

	/**
	 * 操作时间
	 **/
	@Column(name = "create_time")
	private Date createTime;

}
