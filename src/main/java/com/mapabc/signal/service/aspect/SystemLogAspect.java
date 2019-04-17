package com.mapabc.signal.service.aspect;

import com.alibaba.fastjson.JSON;
import com.mapabc.signal.common.annotation.AspectLog;
import com.mapabc.signal.common.util.IpAddressUtil;
import com.mapabc.signal.common.util.date.DateStyle;
import com.mapabc.signal.common.util.date.DateUtils;
import com.mapabc.signal.dao.model.OperateLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: [AOP切入类，记录接口调用记录，输出到日志文件]
 * @author yinguijin
 * @version 1.0
 * Created on 2019/4/17 14:38
 */
@Aspect
@Component
public class SystemLogAspect {

    //本地异常日志记录对象
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemLogAspect.class);

    @Value("${service.name: }")
    private String serverName;

    //Controller层切点
    @Pointcut("@annotation(com.mapabc.signal.common.annotation.AspectLog)")
    public void controllerAspect() {
    }


    /**
     * 前置通知 用于拦截Controller层记录用户的操作
     *
     * @param joinPoint 切点
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {

        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();
        //请求的IP
        String ip = IpAddressUtil.getIpAddress(request);
        try {
            //获取信息
            Map<String, Object> map = getControllerMethodDescription(joinPoint);
            //*========操作日志=========*//
            OperateLog log = new OperateLog();
            log.setDescription(map.get("description").toString());
            log.setIp(ip);
            log.setOperationType(map.get("operationType").toString());
            log.setUrl(request.getRequestURL().toString());
            log.setArgs(getArgs(request, joinPoint));
            log.setCreateTime(new Date());
            //打印操作日志
            this.consoleLog(log, request);
        } catch (Exception e) {
            //记录本地异常日志
            LOGGER.error("记录本地异常日志，异常信息:{}", e);
        }
    }


    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public static Map<String, Object> getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        Map<String, Object> map = new HashMap<>();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    String description = method.getAnnotation(AspectLog.class).description();
                    String operationType = method.getAnnotation(AspectLog.class).operationType().toString();
                    map.put("operationType", operationType);
                    map.put("description", description);
                    break;
                }
            }
        }
        return map;
    }

    /**
     * @discription: [获取请求参数]
     * @param request 请求对象
     * @return String 请求参数
     *
     * Created on 2019/4/17 14:16
     * @author: yinguijin
     */
    public static String getArgs(HttpServletRequest request, JoinPoint joinPoint){
        Enumeration<String> parameterNames = request.getParameterNames();
        Map<String,Object> requestParams = new HashMap<>();
        while (parameterNames.hasMoreElements()){
            String argName = parameterNames.nextElement();
            String value = request.getParameter(argName);
            requestParams.put(argName,value);
        }
        if (requestParams.size()<=0) {
            Object[] args = joinPoint.getArgs();
            for (Object arg : args) {
                if (null == arg) {
                    continue;
                }
                if (arg instanceof HttpServletRequest || arg instanceof HttpServletResponse) {
                    continue;
                }
                String argName = arg.getClass().getName();
                Object value = arg;
                requestParams.put(argName,value);
            }
        }
        return JSON.toJSONString(requestParams);
    }

    private String consoleLogTemplate = "系统：%s\r\n" +
            "URL：%s\r\n" +
            "操作类型：%s\r\n" +
            "操作描述：%s\r\n" +
            "操作IP：%s\r\n" +
            "操作时间：%s\r\n" +
            "请求参数：%s\r\n" +
            "响应状态：%s";
    /**
     * <p>Description:[记录操作日志，输出到日志文件]</p>
     * Created on 2019/4/17 14:16
     *
     * @param log 输出日志对象
     * @param request 请求对象
     * @auther yinguijin
     */
    private void consoleLog(OperateLog log, HttpServletRequest request) {
        try {
            String logText = String.format(consoleLogTemplate,
                    serverName,
                    log.getUrl(),
                    log.getOperationType(),
                    log.getDescription(),
                    log.getIp(),
                    DateUtils.dateToString(log.getCreateTime(), DateStyle.YYYY_MM_DD_HH_MM_SS),
                    log.getArgs(),
                    log.getStatus());
            LOGGER.info(logText);
        } catch (Exception e) {
            LOGGER.error("打印操作日志异常，异常信息", e);
        }
    }
}
