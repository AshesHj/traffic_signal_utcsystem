package com.mapabc.signal.dao.model;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Description: [路口相位方案表实体类]</p>
 * @author yinguijin
 * @version 1.0
 * Created on 2019年05月06日
 */
@Data
@Table(name = "t_base_cross_phase_plan")
public class TBaseCrossPhasePlan {

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
	 * 相位方案编号
	 **/
	@Column(name = "phaseplanid")
	private String phasePlanId;

	/**
	 * 相位方案描述
	 **/
	@Column(name = "pahseplandesc")
	private String pahsePlanDesc;

	/**
	 * 相位编号
	 **/
	@Column(name = "phaseid")
	private String phaseId;

	/**
	 * 相位名称
	 **/
	@Column(name = "phasename")
	private String phaseName;

	/**
	 * 相位描述
	 **/
	@Column(name = "phasedesc")
	private String phaseDesc;

	/**
	 * 方向：0 北 1 东北 2 东 3 东南 4 南 5 西南 6 西 7 西北
	 **/
	@Column(name = "direction")
	private String direction;

	/**
	 * 转向：1 左 2 直 3 右 4 掉头
	 **/
	@Column(name = "turntype")
	private String turnType;

	/**
	 * 是否二次行人过街：1 是 0 否
	 **/
	@Column(name = "issecond")
	private String isSecond;

	/**
	 * 进出口类型：0 all 1 in 2 out
	 **/
	@Column(name = "inouttype")
	private String inoutType;

	/**
	 * 方向对象类型，01机动车方向，02行人方向
	 */
	@Column(name = "dirobjtype")
	private String dirobjType;

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
