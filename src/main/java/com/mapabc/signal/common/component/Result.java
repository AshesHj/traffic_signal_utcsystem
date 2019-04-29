package com.mapabc.signal.common.component;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;


/**
 * @Description: [API响应结果封装]
 * @author yinguijin
 * @version 1.0
 * Created on 2019/4/28 19:45
 */
public class Result<T> {

    private Integer status;

    private String msg;

    private T data;

    /**
     * @description: 判断是否返回成功
     * @return true 成功，false 失败
     * @author yinguijin 
     * @date 2019/4/28 16:00 
    */
    public Boolean isSuccess() {
        if (this.getStatus().equals(HttpStatus.OK.value())) {
            return true;
        }
        return false;
    }

    /**
     * @description: 设置结果200为成功
     * @return true 成功
     * @author yinguijin
     * @date 2019/4/28 16:00
     */
    public Result renderSuccess() {
        this.status = HttpStatus.OK.value();
        return this;
    }

    /**
     * @description: 设置结果200为成功，设置成功描述信息
     * @param msg 成功描述信息
     * @author yinguijin
     * @date 2019/4/28 16:01
    */
    public Result renderSuccess(String msg) {
        this.status = HttpStatus.OK.value();
        this.msg = msg;
        return this;
    }

    /**
     * @description: 设置结果为成功，设置成功描述信息
     * @param status 状态码
     * @param msg 成功描述信息
     * @author yinguijin
     * @date 2019/4/28 16:01
     */
    public Result renderSuccess(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
        return this;
    }

    /**
     * @description: 设置结果为成功，设置成功描述信息
     * @param status 状态码
     * @param data 成功相应对象
     * @author yinguijin
     * @date 2019/4/28 16:01
     */
    public Result renderSuccess(Integer status, T data) {
        this.status = status;
        this.data = data;
        return this;
    }

    /**
     * @description: 设置结果500为失败
     * @author yinguijin
     * @date 2019/4/28 16:01
     */
    public Result renderError() {
        this.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        return this;
    }

    /**
     * @description: 设置结果500为失败，设置失败描述信息
     * @param msg 成功描述信息
     * @author yinguijin
     * @date 2019/4/28 16:01
     */
    public Result renderError(String msg) {
        this.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.msg = msg;
        return this;
    }

    /**
     * @description: 设置结果为失败，设置失败描述信息
     * @param status 状态码
     * @param msg 失败描述信息
     * @author yinguijin
     * @date 2019/4/28 16:01
     */
    public Result renderError(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
        return this;
    }

    /**
     * @description: 设置结果为失败，设置失败描述信息
     * @param status 状态码
     * @param data 失败相应对象
     * @author yinguijin
     * @date 2019/4/28 16:01
     */
    public Result renderError(Integer status, T data) {
        this.status = status;
        this.data = data;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public Result setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Result setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
