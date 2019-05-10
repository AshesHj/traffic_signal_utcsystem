package com.mapabc.signal.dao.model;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Description: [路口运行计划表实体类]</p>
 * @author yinguijin
 * @version 1.0
 * Created on 2019年05月06日
 */
@Data
@Table(name = "t_base_cross_run_plan")
public class TBaseCrossRunPlan {

	/**
	 * 主键
	 **/
	@Id
	private Integer id;

	/**
	 * 路口ID
	 **/
	@Column(name = "crossid")
	private String crossId;

	/**
	 * 信号机类型：1、青松；2、SCATS；3、海信
	 **/
	@Column(name = "signaltype")
	private String signalType;

	/**
	 * 信号机编号
	 **/
	@Column(name = "signalid")
	private String signalId;

	/**
	 * 时段方案编号
	 **/
	@Column(name = "sectionplanid")
	private String sectionPlanId;

	/**
	 * 星期：1 星期一 2 星期二 3 星期三 4 星期四 5 星期五 6 星期六 7 星期日
	 **/
	@Column(name = "weeknum")
	private Integer weekNum;

	/**
	 * 特殊日期：YYYY-MM-DD
	 **/
	@Column(name = "datestr")
	private String dateStr;

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
