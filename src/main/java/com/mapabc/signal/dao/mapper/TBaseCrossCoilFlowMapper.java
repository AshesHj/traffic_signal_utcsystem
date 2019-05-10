package com.mapabc.signal.dao.mapper;

import com.mapabc.signal.dao.model.TBaseCrossCoilFlow;
import com.mapabc.signal.dao.MyBaseMapper;
import org.springframework.stereotype.Repository;

/**
* @Description: [路口线圈流量数据持久层实现]</p>
* @author yinguijin
* @version 1.0
* Created on 2019年05月08日
*/
@Repository
public interface TBaseCrossCoilFlowMapper extends MyBaseMapper<TBaseCrossCoilFlow> {

    /**
     * @description: 删除一小时以前的数据
     * @author yinguijin
     * @date 2019/5/8 21:23
    */
    void deleteOneHouFlow();
}