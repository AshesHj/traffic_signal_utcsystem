package com.mapabc.signal.config.init;

import com.mapabc.signal.common.exception.WarnException;
import com.mapabc.signal.service.ExceptionService;
import com.mapabc.signal.service.qs.QsGetSignalMethodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author yinguijin
 * @version 1.0
 * Created on 2019/4/29 17:42
 * @description: [初始化加载青松token]
 */
@Component
@Order(2)
public class InitialVendorTokenRunner implements ApplicationRunner {

    private Logger LOGGER = LoggerFactory.getLogger(InitialVendorTokenRunner.class) ;

    @Resource
    private QsGetSignalMethodService qsSignalMethodService;

    @Resource
    private ExceptionService exceptionService;

    @Override
    public void run(ApplicationArguments applicationArguments) {
        try {
//            qsSignalMethodService.login();// TODO 测试阶段注释
        } catch (WarnException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (Exception e) {
            exceptionService.handle("登录青松接口失败！", e);
        }
    }
}
