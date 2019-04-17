package com.mapabc.signal.common.annotation;

import com.mapabc.signal.common.enums.BaseEnum;

import java.lang.annotation.*;

/**
 * @Description: [自定义注解-系统操作日志，拦截Controller请求]
 * @author yinguijin
 * @version 1.0
 * Created on 2019/4/17 14:06
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AspectLog {

    /**
     * 操作描述 业务名称description
     * @return
     */
    String description() default "";

    /**
     * 操作类型 query create modify delete
     * @return
     */
    BaseEnum.OperationTypeEnum operationType();
}
