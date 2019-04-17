package com.mapabc.signal.common.exception;

/**
 * @Description: [自定义异常，用于提示警告的Exception]
 * @author yinguijin
 * @version 1.0
 * Created on 2019/4/17 11:07
 */
public class WarnException extends RuntimeException {

    public WarnException(String message) {
        super(message);
    }

    public WarnException(String message, Exception e) {
        super(message,e);
    }

}
