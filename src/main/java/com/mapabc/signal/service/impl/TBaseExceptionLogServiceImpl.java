package com.mapabc.signal.service.impl;

import com.mapabc.signal.common.component.ExceptionLog;
import com.mapabc.signal.dao.mapper.TBaseExceptionLogMapper;
import com.mapabc.signal.service.TBaseExceptionLogService;
import org.springframework.stereotype.Service;

/**
* @Description: [信号机标准接口服务调用异常日志表service实现]</p>
* @author yinguijin
* @version 1.0
* Created on 2019年05月10日
*/
@Service
public class TBaseExceptionLogServiceImpl extends BaseServiceImpl<ExceptionLog, TBaseExceptionLogMapper> implements TBaseExceptionLogService {
}
