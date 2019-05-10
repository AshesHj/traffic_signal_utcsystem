package com.mapabc.signal.dao.model;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @Description: [信号机列表实体类]</p>
 * @author yinguijin
 * @version 1.0
 * Created on 2019年04月30日
 */
@Data
@Table(name = "t_teleseme_list")
public class TelesemeList {

	/**
	 * 主键ID
	 **/
	@Column(name = "ID")
	private String id;

	/**
	 * 路口ID路口ID
	 **/
	@Column(name = "CROSSID")
	private String crossId;

	/**
	 * 路口名称路口名称
	 **/
	@Column(name = "CROSSNAME")
	private String crossName;

	/**
	 * 经度经度
	 **/
	@Column(name = "LNG")
	private String lng;

	/**
	 * 纬度纬度
	 **/
	@Column(name = "LAT")
	private String lat;

	/**
	 * 信号机名称
	 **/
	@Column(name = "SIGNAL_NAME")
	private String signalName;

	/**
	 * 路口类型
	 **/
	@Column(name = "CROSS_TYPE")
	private String crossType;

	/**
	 * 信号机状态
	 **/
	@Column(name = "SIGNAL_STATUS")
	private Integer signalStatus;

	/**
	 * IP
	 **/
	@Column(name = "IP")
	private String ip;

	/**
	 * 区域名称
	 **/
	@Column(name = "AREA_NAME")
	private String areaName;

	/**
	 * 地址
	 **/
	@Column(name = "ADDR")
	private String addr;

	/**
	 * 备注
	 **/
	@Column(name = "REMARK")
	private String remark;

	/**
	 * 信号机厂商
	 **/
	@Column(name = "SIGNAL_TYPE")
	private String signalType;

	/**
	 * 信号机编号
	 **/
	@Column(name = "SIGNAL_ID")
	private String signalId;


}
