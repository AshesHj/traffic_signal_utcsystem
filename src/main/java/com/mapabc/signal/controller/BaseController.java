package com.mapabc.signal.controller;

import com.mapabc.signal.service.ExceptionService;

import javax.annotation.Resource;

/**
 * @Description: [基础控制类]
 * @author yinguijin
 * @version 1.0
 * Created on 2019/4/17 12:01
 */
public class BaseController {

    @Resource
    ExceptionService exceptionService;
}
