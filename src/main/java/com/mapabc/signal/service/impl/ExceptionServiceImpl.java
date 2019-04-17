package com.mapabc.signal.service.impl;

import com.mapabc.signal.common.exception.WarnException;
import com.mapabc.signal.common.util.IpAddressUtil;
import com.mapabc.signal.common.util.StringUtils;
import com.mapabc.signal.common.util.date.DateStyle;
import com.mapabc.signal.common.util.date.DateUtils;
import com.mapabc.signal.dao.model.ExceptionLog;
import com.mapabc.signal.service.ExceptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * @Description: [异常处理service实现]
 * @author yinguijin
 * @version 1.0
 * Created on 2019/4/17 12:03
 */
@Service
public class ExceptionServiceImpl implements ExceptionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionServiceImpl.class);

    @Value("${service.name: }")
    private String serverName;

    @Value("${spring.profiles.active: }")
    private String profile;

    private String HTTP_CONTENT_DEFAULT_CHARSET = "UTF-8";

    private String errorTemplate = "系统：%s\r\n" +
                "环境：%s\r\n" +
                "URL：%s\r\n" +
                "IP：%s\r\n" +
                "操作时间：%s\r\n" +
                "错误描述：%s\r\n" +
                "浏览器信息：%s\r\n" +
                "错误详情：%s";

    /**
     * @description: 异常处理
     * @param exceptionMsg 异常描述
     * @param e 异常对象
     * @param request 请求对象
     * @author yinguijin
     * @date 2019/4/17 12:06
     */
    @Override
    public void handle(String exceptionMsg, Exception e, HttpServletRequest request) {
        try {
            //自定义提示异常  不做处理
            if(e instanceof WarnException){
                return;
            }
            LOGGER.error(e.getMessage());
            //封装异常对象
            ExceptionLog exceptionLog = new ExceptionLog();
            exceptionLog.setSystem(serverName);
            exceptionLog.setDescription(exceptionMsg);
            if (request != null) {
                exceptionLog.setOperatorIp(IpAddressUtil.getIpAddress(request));
                exceptionLog.setUri(request.getRequestURI());
                exceptionLog.setBrowerMessage(request.getHeader("User-Agent"));
            }
            //错误堆栈
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(outputStream, false ,HTTP_CONTENT_DEFAULT_CHARSET);
            e.printStackTrace(ps);
            byte[] lens = outputStream.toByteArray();
            String result = new String(lens, HTTP_CONTENT_DEFAULT_CHARSET);
            exceptionLog.setDetail(result);
            exceptionLog.setCreateTime(new Date());
            String hostName = null;
            try {
                hostName = InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            }
            if (StringUtils.isNotEmpty(hostName)) {
                exceptionLog.setHostName(hostName);
            }
            ps.close();
            outputStream.close();
            //输出到日志文件
            this.consoleLog(exceptionLog);
        } catch (Exception e1) {
            LOGGER.error("记录输出异常日志失败", e1.getMessage());
        }
    }

    /**
     * @description: 异常处理
     * @param exceptionMsg 异常描述
     * @param e 异常对象
     * @author yinguijin
     * @date 2019/4/17 12:06
     */
    @Override
    public void handle(String exceptionMsg, Exception e) {
        handle(exceptionMsg, e, null);
    }

    /**
     * @description: 控制台打印异常信息
     * @param exceptionLog 异常信息对象
     * @return void
     * @author yinguijin
     * @date 2019/4/17 12:35
     */
    private void consoleLog(ExceptionLog exceptionLog) {
        String errorText = String.format(errorTemplate,
                exceptionLog.getSystem(),
                profile,
                exceptionLog.getUri(),
                exceptionLog.getOperatorIp(),
                DateUtils.dateToString(exceptionLog.getCreateTime(), DateStyle.YYYY_MM_DD_HH_MM_SS),
                exceptionLog.getDescription()==null?"":exceptionLog.getDescription(),
                exceptionLog.getBrowerMessage(),
                exceptionLog.getDetail());
        LOGGER.error(errorText);
    }
}
