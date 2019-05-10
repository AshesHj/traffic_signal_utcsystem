package com.mapabc.signal.service;

import com.mapabc.signal.common.component.BaseSignal;
import com.mapabc.signal.common.component.ParamEntity;
import com.mapabc.signal.dao.model.TBaseCrossSectionPlan;
import org.springframework.stereotype.Repository;

/**
* @Description: [路口时段方案表service]</p>
* @author yinguijin
* @version 1.0
* Created on 2019年05月06日
*/
@Repository
public interface TBaseCrossSectionPlanService extends BaseService<TBaseCrossSectionPlan> {

    /**
     * @description: 保存路口的时段方案（日时段方案）
     * @param param  请求参数时段方案
     * @author yinguijin
     * @date 2019/4/19 14:49
     */
    void initSectionPlan(ParamEntity param);
}
