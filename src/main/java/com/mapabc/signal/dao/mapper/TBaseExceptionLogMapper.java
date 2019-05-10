package com.mapabc.signal.dao.mapper;

import com.mapabc.signal.common.component.ExceptionLog;
import com.mapabc.signal.dao.MyBaseMapper;
import org.springframework.stereotype.Repository;

/**
* @Description: [信号机标准接口服务调用异常日志表持久层实现]</p>
* @author yinguijin
* @version 1.0
* Created on 2019年05月10日
*/
@Repository
public interface TBaseExceptionLogMapper extends MyBaseMapper<ExceptionLog> {
}