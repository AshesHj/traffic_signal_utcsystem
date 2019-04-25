package com.mapabc.signal.dao.model;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description: [厂商接口信息表实体类]</p>
 * @author yinguijin
 * @version 1.0
 * Created on 2019年04月25日
 */
@Data
@Table(name = "t_base_vendor_method")
public class TBaseVendorMethod implements Serializable {

	/**
	 * 主键ID自增长
	 **/
	@Id
	private Long id;

	/**
	 * 厂商代码
	 **/
	@Column(name = "VENDOR_CODE")
	private String vendorCode;

	/**
	 * 厂商名称：青松、SCATS、海信、海康

	 **/
	@Column(name = "VENDOR_NAME")
	private String vendorName;

	/**
	 * 厂商简称：QS、SCATS、HS、HK

	 **/
	@Column(name = "VENDOR_NICK")
	private String vendorNick;

	/**
	 * 接口类型 1：静态数据接口；2：动态数据接口；3：控制下发接口
	 **/
	@Column(name = "METHOD_TYPE")
	private Integer methodType;

	/**
	 * 接口请求方式 POST GET PUT DELETE
	 **/
	@Column(name = "HTTP_METHOD")
	private String httpMethod;

	/**
	 * 接口代码
	 **/
	@Column(name = "METHOD_CODE")
	private String methodCode;

	/**
	 * 接口名称
	 **/
	@Column(name = "METHOD_NAME")
	private String methodName;

	/**
	 * 接口描述
	 **/
	@Column(name = "METHOD_DESC")
	private String methodDesc;

	/**
	 * 接口地址
	 **/
	@Column(name = "METHOD_URL")
	private String methodUrl;

	/**
	 * 是否删除 0 fasle;1 true
	 **/
	@Column(name = "IS_DELETE")
	private Boolean isDelete;

	/**
	 * 创建时间
	 **/
	@Column(name = "CREATE_TIME")
	private Date createTime;

	/**
	 * 修改时间
	 **/
	@Column(name = "UPDATE_TIME")
	private Date updateTime;


}
