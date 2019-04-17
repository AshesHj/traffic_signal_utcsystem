package com.mapabc.signal.dao.model;


import lombok.Data;

import java.util.Date;

/**
 * @description: [异常信息实体类]
 * @author yinguijin
 * @date 2019/4/17 12:45
*/
@Data
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
	private String operatorIp;

	/**
	 * 错误描述
	 **/
	private String description;

	/**
	 * 主机
	 **/
	private String hostName;

	/**
	 * 错误详细
	 **/
	private String detail;

	/**
	 * 浏览器信息
	 **/
	private String browerMessage;

	/**
	 * 操作时间
	 **/
	private Date createTime;

}
