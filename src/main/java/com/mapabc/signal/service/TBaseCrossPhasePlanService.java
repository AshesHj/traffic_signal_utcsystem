package com.mapabc.signal.service;

import com.mapabc.signal.common.component.BaseSignal;
import com.mapabc.signal.common.component.ParamEntity;
import com.mapabc.signal.dao.model.TBaseCrossPhasePlan;
import org.springframework.stereotype.Repository;

/**
* @Description: [路口相位方案表service]</p>
* @author yinguijin
* @version 1.0
* Created on 2019年05月06日
*/
@Repository
public interface TBaseCrossPhasePlanService extends BaseService<TBaseCrossPhasePlan> {

    /**
     * @description: 保存路口相位方案数据
     * @param param  请求参数相位方案
     * @return  PhasePlanVo 相位方案数据集合
     * @author yinguijin
     * @date 2019/4/19 14:49
     */
    void initPhasePlan(ParamEntity param);
}
