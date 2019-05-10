package com.mapabc.signal.dao.model;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Description: [路口信息实体类]</p>
 * @author yinguijin
 * @version 1.0
 * Created on 2019年05月06日
 */
@Data
@Table(name = "t_base_cross_info")
public class TBaseCrossInfo {

	/**
	 * 路口编号格式：MESHID_NODEID
	 **/
	@Id
	@Column(name = "ID")
	private String id;

	/**
	 * 路口编码格式：6位行政区划_4位路口顺序号
	 **/
	@Column(name = "CODE")
	private String code;

	/**
	 * 路口名称
	 **/
	@Column(name = "NAME")
	private String name;

	/**
	 * 路口类型1丁字交叉口
            2十字交叉口
            3环岛
            4畸形交叉口
            5立体交叉口
            6铁路道口
            7其他
            
	 **/
	@Column(name = "TYPE")
	private String type;

	/**
	 * 行政区划代码
	 **/
	@Column(name = "ADCODE")
	private String adCode;

	/**
	 * 行政区划名称
	 **/
	@Column(name = "ADNAME")
	private String adName;

	/**
	 * 位置信息POINT(经度,维度)
	 **/
	@Column(name = "LOCATION")
	private String location;

	/**
	 * 信号机编号信号厂家路口编号
	 **/
	@Column(name = "signal_id")
	private String signalId;

	/**
	 * 信号机类型：1、青松；2、SCATS；3、海信；4、海康；5、大华；6、京安；7、同安；
	 **/
	@Column(name = "signal_type")
	private String signalType;

	/**
	 * 控制类型0：单点控制 1：协调控制
	 **/
	@Column(name = "control_type")
	private Integer controlType;

	/**
	 * 是否有特殊控制1 有特殊控制 0 无特殊控制
	 **/
	@Column(name = "is_control")
	private String isControl;

	/**
	 * 是否已启动
	 **/
	@Column(name = "is_start")
	private Integer isStart;

	/**
	 * 数据底盘路口ID对应数据底盘的路口或岔口ID
	 **/
	@Column(name = "ridcross_id")
	private String ridcrossId;

	/**
	 * 数据底盘路口类型1 路口 2 岔口
	 **/
	@Column(name = "ridcross_type")
	private String ridcrossType;

	/**
	 * 创建时间
	 **/
	@Column(name = "gmt_create")
	private Date gmtCreate;

	/**
	 * 更新时间
	 **/
	@Column(name = "gmt_modified")
	private Date gmtModified;


}
