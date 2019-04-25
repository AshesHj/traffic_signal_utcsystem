package com.mapabc.signal.common.constant;

import com.mapabc.signal.dao.model.TBaseVendorMethod;

import java.util.concurrent.*;

/**
 * @Description: [常量类]
 * @author yinguijin
 * @version 1.0
 * Created on 2019/4/16 15:07
 */
public interface Const {

    String SYSTEM_TYPE = "UTC";
    /**
     * 未删除
     */
    Boolean IS_DELETE_NO = false;
    /**
     * 已删除
     */
    Boolean IS_DELETE_YES = true;

    /**
     * 分隔符：逗号
     **/
    String SEPARATOR_COMMA = ",";

    /**
     * 分隔符：冒号
     **/
    String SEPARATOR_COLON = ":";

    /**
     * 分隔符：减号
     **/
    String SEPARATOR_MINUS = "-";

    /**
     * 分隔符：下划线
     **/
    String SEPARATOR_UNDER_LINE = "_";

    /**
     * 分隔符：空格
     **/
    String SEPARATOR_SPACE = " ";

    /**
     * 分隔符：分号
     **/
    String SEPARATOR_SEMICOLON = ";";

    /**
     * 分隔符：@
     */
    String SEPARATOR_AT = "@";

    /**
     * 分隔符：左括号
     */
    String SEPARATOR_LEFTBRACES = "(";

    /**
     * 分隔符：右括号
     */
    String SEPARATOR_RIGHTBRACES = ")";

    /**
     * 分隔符：大于号 >
     */
    String SEPARATOR_GREATER_THAN = ">";

    /**
     * 线程池
     */
    ExecutorService masterDataPool =  new ThreadPoolExecutor(10, 100,
            5L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>());

    /**
     * 厂商接口地址Map
     */
    ConcurrentHashMap<String, TBaseVendorMethod> urlMap = new ConcurrentHashMap<>();
}
