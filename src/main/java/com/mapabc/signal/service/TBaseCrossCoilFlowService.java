package com.mapabc.signal.service;

import com.mapabc.signal.dao.model.TBaseCrossCoilFlow;
import org.springframework.stereotype.Repository;

/**
* @Description: [路口线圈流量数据service]</p>
* @author yinguijin
* @version 1.0
* Created on 2019年05月08日
*/
@Repository
public interface TBaseCrossCoilFlowService extends BaseService<TBaseCrossCoilFlow> {

    /**
     * @description: 保存信号机流量信息
     * @author yinguijin
     * @date 2019/5/8 20:27
    */
    void saveCoilFlow();
}
