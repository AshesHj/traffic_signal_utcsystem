package com.mapabc.signal.common.constant;

/**
 * @author yinguijin
 * @version 1.0
 * Created on 2019/5/8 16:35
 * @description: [redis key常量类]
 */
public interface RedisKeyConst {

    /**
     * Redis存储Key前缀
     */
    String KEY_PREFIX = "utcsystem_";

    //token常量名称
    String TOKEN = "qs_token";
    //redis时段方案版本号
    String SECTIM_VERSION = "sectim_version_";
    //redis相位方案版本号
    String PHASE_VERSION = "phase_version_";
    //token存储Redis有效时长
    int TOKEN_EXPIRE_TIME = 7200;

    //信号机状态
    String TELESEME_STATUS = "teleseme_status";
}
