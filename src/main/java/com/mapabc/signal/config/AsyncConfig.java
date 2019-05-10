package com.mapabc.signal.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

/**
 * @Description: [异步线程连接池配置]
 * @author yinguijin
 * @version 1.0
 * Created on 2019/4/16 14:58
 */
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncConfig.class);

    @Value("${threadPool.corePoolSize}")
    private int corePoolSize;

    @Value("${threadPool.maxPoolSize}")
    private int maxPoolSize;

    @Value("${threadPool.queueCapacity}")
    private int queueCapacity;

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new MyAsyncExceptionHandler();
    }
    /**
     * 自定义异常处理类
     * @author yinguijin
     */
    class MyAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
        //手动处理捕获的异常
        @Override
        public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
            LOGGER.error("-------------》》》捕获线程异常信息");
            LOGGER.error("Exception message - " + throwable.getMessage());
            LOGGER.error("Method name - " + method.getName());
            for (Object param : obj) {
                LOGGER.error("Parameter value - " + param);
            }
        }
    }
}