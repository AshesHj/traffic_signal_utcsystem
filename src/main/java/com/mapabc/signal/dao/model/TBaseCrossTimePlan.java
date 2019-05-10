package com.mapabc.signal.dao.model;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Description: [路口配时方案表实体类]</p>
 * @author yinguijin
 * @version 1.0
 * Created on 2019年05月06日
 */
@Data
@Table(name = "t_base_cross_time_plan")
public class TBaseCrossTimePlan {

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
	 * 配时方案编号
	 **/
	@Column(name = "timeplanid")
	private String timePlanId;

	/**
	 * 配时方案描述
	 **/
	@Column(name = "timeplandesc")
	private String timePlanDesc;

	/**
	 * 相位方案编号
	 **/
	@Column(name = "phaseplanid")
	private String phasePlanId;

	/**
	 * 周期长度
	 **/
	@Column(name = "cyclelen")
	private Integer cycleLen;

	/**
	 * 协调相位编号
	 **/
	@Column(name = "coordphaseid")
	private String coordPhaseId;

	/**
	 * 相位差
	 **/
	@Column(name = "offset")
	private Integer offset;

	/**
	 * 是否双环： 1 是 0 否
	 **/
	@Column(name = "isdoublering")
	private Integer isDoubleRing;

	/**
	 * 环编号
	 **/
	@Column(name = "ringno")
	private String ringNo;

	/**
	 * 相位编号
	 **/
	@Column(name = "phaseid")
	private String phaseId;

	/**
	 * 相位排序号
	 **/
	@Column(name = "phaseorderid")
	private Integer phaseOrderId;

	/**
	 * 相位时间
	 **/
	@Column(name = "phasetime")
	private Integer phaseTime;

	/**
	 * 最大绿灯时间
	 **/
	@Column(name = "maxgreentime")
	private Integer maxGreenTime;

	/**
	 * 最小绿灯时间
	 **/
	@Column(name = "mingreentime")
	private Integer minGreenTime;

	/**
	 * 步号：1,2,3,4,5,6
	 **/
	@Column(name = "stepno")
	private Integer stepNo;

	/**
	 * 步类型：1 green 2 greenflash 3 pedflash 4 yellow 5 red 6 off
	 **/
	@Column(name = "steptype")
	private Integer stepType;

	/**
	 * 步长
	 **/
	@Column(name = "steplen")
	private Integer stepLen;

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
