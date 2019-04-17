package com.mapabc.signal.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: [异常处理service类]
 * @author yinguijin
 * @version 1.0
 * Created on 2019/4/17 12:02
 */
public interface ExceptionService {

    /**
     * @description: 异常处理
     * @param exceptionMsg 异常描述
     * @param e 异常对象
     * @param request 请求对象
     * @author yinguijin
     * @date 2019/4/17 12:06
    */
   void handle(String exceptionMsg, Exception e, HttpServletRequest request);

    /**
     * @description: 异常处理
     * @param exceptionMsg 异常描述
     * @param e 异常对象
     * @author yinguijin
     * @date 2019/4/17 12:06
     */
   void handle(String exceptionMsg, Exception e);
}
