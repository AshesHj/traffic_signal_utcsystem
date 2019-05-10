package com.mapabc.signal.dao.model;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @Description: [路口灯组表实体类]</p>
 * @author yinguijin
 * @version 1.0
 * Created on 2019年04月30日
 */
@Data
@Table(name = "t_cross_lights")
public class TCrossLights {

	/**
	 * 路口编号
	 **/
	private String id;

	/**
	 * 灯组ID
	 **/
	@Column(name = "lightsid")
	private String lightsId;

	/**
	 * 灯组编号
	 **/
	@Column(name = "lightgroupno")
	private String lightGroupNo;

	/**
	 * 放行方向编号
	 **/
	@Column(name = "directioncode")
	private String directionCode;

	/**
	 * 灯组坐标
	 **/
	private String wkt;

	/**
	 * 灯组类型1 圆灯 2 直行箭头 3 左转箭头 4 右转箭头 5 掉头灯 6 行人灯
	 **/
	@Column(name = "lighttype")
	private String lightType;


}
