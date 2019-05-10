package com.mapabc.signal.task;

import com.mapabc.signal.service.ExceptionService;
import com.mapabc.signal.service.TBaseCrossCoilFlowService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author yinguijin
 * @version 1.0
 * Created on 2019/5/9 11:51
 * @description: [路口线圈流量数据统计jOB]
 */
@Component
public class CrossCoilFlowTask {

    @Resource
    private ExceptionService exceptionService;

    @Resource
    private TBaseCrossCoilFlowService tBaseCrossCoilFlowService;

    //1分钟开始5分钟刷新一次
    @Scheduled(cron = "0 1/5 * * * ?")
    public void loadVendor() {
        try {
            tBaseCrossCoilFlowService.saveCoilFlow();
        } catch (Exception e) {
            exceptionService.handle("JOB定时任务统计线圈流量数据异常", e);
        }
    }
}
