package com.mapabc.signal.service;

import com.mapabc.signal.common.component.BaseSignal;
import com.mapabc.signal.common.component.ParamEntity;
import com.mapabc.signal.dao.model.TBaseCrossTimePlan;
import org.springframework.stereotype.Repository;

/**
* @Description: [路口配时方案表service]</p>
* @author yinguijin
* @version 1.0
* Created on 2019年05月06日
*/
@Repository
public interface TBaseCrossTimePlanService extends BaseService<TBaseCrossTimePlan> {

    /**
     * @description: 路口的配时方案信息获取
     * @param param  请求参数配时方案
     * @return  TimePlanVo 路口的配时方案数据集合
     * @author yinguijin
     * @date 2019/4/19 14:49
     */
    void initTimePlan(ParamEntity param);
}
