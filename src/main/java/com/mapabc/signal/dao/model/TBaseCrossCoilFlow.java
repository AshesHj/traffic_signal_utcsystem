package com.mapabc.signal.dao.model;


import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

import javax.persistence.*;

/**
 * @Description: [路口线圈流量数据实体类]</p>
 * @author yinguijin
 * @version 1.0
 * Created on 2019年05月08日
 */
@Data
@Table(name = "t_base_cross_coil_flow")
public class TBaseCrossCoilFlow {

	/**
	 * 主键
	 **/
	@Id
	private Long id;

	/**
	 * 信号机厂商
	 **/
	@Column(name = "signal_type")
	private String signalType;

	/**
	 * 信号机编号
	 **/
	@Column(name = "signal_id")
	private String signalId;

	/**
	 * 日期时间：yyyy-mm-dd hh24:mi:00
	 */
	@Column(name = "date_time")
	private String dateTime;

	/**
	 * 时间粒度，单位：秒
	 **/
	private Integer granula;

	/**
	 * 检测器编号
	 **/
	@Column(name = "det_id")
	private String detId;

	/**
	 * 单位时间粒度内的流量
	 **/
	private Integer volume;

	/**
	 * 平均速度
	 **/
	private BigDecimal speed;

	/**
	 * 时间占有率
	 **/
	private BigDecimal occupancy;

	/**
	 * 车头间距
	 **/
	@Column(name = "head_distance")
	private BigDecimal headDistance;

	/**
	 * 车头时距
	 **/
	@Column(name = "head_time")
	private BigDecimal headTime;

	/**
	 * 平均车长
	 **/
	@Column(name = "car_length")
	private BigDecimal carLength;

	/**
	 * 排队长度
	 **/
	@Column(name = "queue_length")
	private BigDecimal queueLength;

	/**
	 * 置信度
	 **/
	private BigDecimal credible;

	/**
	 * 饱和度
	 **/
	private BigDecimal saturation;

	/**
	 * 微型车流量
	 **/
	@Column(name = "mini_volume")
	private Integer miniVolume;

	/**
	 * 小车流量
	 **/
	@Column(name = "small_volume")
	private Integer smallVolume;

	/**
	 * 中车流量
	 **/
	@Column(name = "middle_volume")
	private Integer middleVolume;

	/**
	 * 大车流量
	 **/
	@Column(name = "big_volume")
	private Integer bigVolume;

	/**
	 * 挂车流量
	 **/
	@Column(name = "large_volume")
	private Integer largeVolume;

	/**
	 * 创建时间
	 **/
	@Column(name = "create_time")
	private Date createTime;


}
