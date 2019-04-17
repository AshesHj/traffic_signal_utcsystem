package com.mapabc.signal.dao;

import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [通用Mapper接口]
 * Created on 2019/4/16 14:37
 */
@Repository
public interface MyBaseMapper<T> extends Mapper<T>, MySqlMapper<T> {

}
