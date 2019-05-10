package com.mapabc.signal.service.impl;

import com.mapabc.signal.common.component.OperateLog;
import com.mapabc.signal.dao.mapper.TBaseOperateLogMapper;
import com.mapabc.signal.service.TBaseOperateLogService;
import org.springframework.stereotype.Service;

/**
* @Description: [信号机标准接口服务调用操作日志表service实现]</p>
* @author yinguijin
* @version 1.0
* Created on 2019年05月10日
*/
@Service
public class TBaseOperateLogServiceImpl extends BaseServiceImpl<OperateLog, TBaseOperateLogMapper> implements TBaseOperateLogService {
}
