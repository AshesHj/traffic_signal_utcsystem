package com.mapabc.signal.config;

import com.alibaba.fastjson.JSON;
import com.mapabc.signal.common.constant.Const;
import com.mapabc.signal.service.TBaseVendorMethodService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [系统启动后调用-初始化值信息]
 * Created on 2019/4/25 16:33
 */
@Component
public class InitialLoadConfig implements ApplicationRunner {

    @Resource
    private TBaseVendorMethodService tBaseVendorMethodService;

    /**
     * @description: 项目启动加载厂商url到内存
     * @author yinguijin
     * @date 2019/4/25 16:36
    */
    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        tBaseVendorMethodService.initLoadVendor();
        System.out.println(JSON.toJSON(Const.urlMap));
    }
}
