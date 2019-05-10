package com.mapabc.signal.dao.model;


import lombok.Data;

import javax.persistence.*;

/**
 * @Description: [路口线圈信息表实体类]</p>
 * @author yinguijin
 * @version 1.0
 * Created on 2019年05月08日
 */
@Data
@Table(name = "t_base_cross_coil_info")
public class TBaseCrossCoilInfo {

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
	 * 检测器编号
	 **/
	@Column(name = "det_id")
	private String detId;

	/**
	 * 车道编号
	 **/
	@Column(name = "lane_id")
	private String laneId;

	/**
	 * 车道所在进口方向, 方向：附件 A1.2
            0 北 1 东北 2 东 3 东南 4 南 5 西南 6 西 7 西北
	 **/
	private Integer direction;


}
