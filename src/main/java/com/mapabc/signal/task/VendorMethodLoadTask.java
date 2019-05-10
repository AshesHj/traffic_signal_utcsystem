package com.mapabc.signal.task;

import com.mapabc.signal.service.ExceptionService;
import com.mapabc.signal.service.TBaseVendorMethodService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description: [JOB定时加载数据库维护的厂商URL数据到内存]
 * @author yinguijin
 * @version 1.0
 * Created on 2019/4/29 10:27
 */
@Component
public class VendorMethodLoadTask {

    @Resource
    private ExceptionService exceptionService;

    @Resource
    private TBaseVendorMethodService tBaseVendorMethodService;

    //2小时刷新一次
    @Scheduled(cron = "0 0 0/2 * * ?")
    public void loadVendor() {
        try {
            tBaseVendorMethodService.initLoadVendor();
        } catch (Exception e) {
            exceptionService.handle("JOB定时任务加载厂商接口URL到内存异常", e);
        }
    }
}
