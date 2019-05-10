package com.mapabc.signal.dao.model;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Description: [路口时段方案表实体类]
 * @author yinguijin
 * @version 1.0
 * Created on 2019年05月06日
 */
@Data
@Table(name = "t_base_cross_section_plan")
public class TBaseCrossSectionPlan {

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
	 * 时段方案描述
	 **/
	@Column(name = "sectionplandesc")
	private String sectionPlanDesc;

	/**
	 * 时段编号
	 **/
	@Column(name = "timesliceid")
	private String timesLiceId;

	/**
	 * 时段描述
	 **/
	@Column(name = "timeslicedesc")
	private String timesLiceDesc;

	/**
	 * 时段顺序号
	 **/
	@Column(name = "timesliceorderid")
	private Integer timesLiceOrderId;

	/**
	 * 开始时间，格式 HH:MI
	 **/
	@Column(name = "starttime")
	private String startTime;

	/**
	 * 结束时间，格式 HH:MI
	 **/
	@Column(name = "endtime")
	private String endTime;

	/**
	 * 配时方案编号
	 **/
	@Column(name = "timeplanid")
	private String timePlanId;

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
