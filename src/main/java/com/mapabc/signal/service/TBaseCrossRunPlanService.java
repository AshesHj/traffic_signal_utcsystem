package com.mapabc.signal.service;

import com.mapabc.signal.common.component.BaseSignal;
import com.mapabc.signal.common.component.ParamEntity;
import com.mapabc.signal.common.component.VendorResult;
import com.mapabc.signal.dao.model.TBaseCrossRunPlan;
import com.mapabc.signal.dao.vo.runplan.RunplanVo;
import org.springframework.stereotype.Repository;

/**
* @Description: [路口运行计划表service]</p>
* @author yinguijin
* @version 1.0
* Created on 2019年05月06日
*/
@Repository
public interface TBaseCrossRunPlanService extends BaseService<TBaseCrossRunPlan> {

    /**
     * @description: 保存路口的运行计划表
     * @param param  请求参数时段方案
     * @author yinguijin
     * @date 2019/4/19 14:49
     */
    void initRunPlan(ParamEntity param);
}
