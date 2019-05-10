package com.mapabc.signal.service.impl;

import com.mapabc.signal.ApplicationTests;
import com.mapabc.signal.common.util.RedisUtil;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author yinguijin
 * @version 1.0
 * Created on 2019/5/5 19:30
 * @description: []
 */
public class RedisUtilTest extends ApplicationTests {

    @Resource
    private RedisUtil redisUtil;

    @Test
    public void get() {
        System.out.println(redisUtil.getVersion("test"));
    }
}
