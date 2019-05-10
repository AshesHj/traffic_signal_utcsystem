package com.mapabc.signal.dao.model;


import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Description: [实体类]</p>
 * @author yinguijin
 * @version 1.0
 * Created on 2019年05月05日
 */
@Data
@Table(name = "t_teleseme_lights")
public class TelesemeLights {

	/**
	 * 唯一主键
	 **/
	@Id
	private String id;

	/**
	 * 代码
	 **/
	private String code;

	/**
	 * 机动车灯组或人行灯组，01代表机动车，02代表人行
	 **/
	private String dirobjtype;

	/**
	 * 方位方向
	 **/
	private String drection;

	/**
	 * 转向，01左转，02直行，04右转，08掉头
	 **/
	private String turn;

	/**
	 * 进出口，1代表出口，2代表进口，3代表进和出
	 **/
	private String inouttype;

	/**
	 * 描述
	 **/
	private String descreption;


}
