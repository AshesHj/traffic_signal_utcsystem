package com.mapabc.signal.task;

import com.mapabc.signal.service.ExceptionService;
import com.mapabc.signal.service.TelesemeListService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author yinguijin
 * @version 1.0
 * Created on 2019/5/8 16:59
 * @description: [刷新信号机状态task]
 */
@Component
public class RefreshSignalStatusTask {

    @Resource
    private ExceptionService exceptionService;

    @Resource
    private TelesemeListService telesemeListService;

    //10分钟刷新一次
    @Scheduled(cron = "0 0/10 * * * ?")
    public void loadVendor() {
        try {
            telesemeListService.refreshSignalStatusToRedis();
        } catch (Exception e) {
            exceptionService.handle("JOB定时任务刷新Redis中信号机状态异常", e);
        }
    }
}
