package com.mapabc.signal.service;

import com.mapabc.signal.common.component.BaseSignal;
import com.mapabc.signal.common.component.ParamEntity;
import com.mapabc.signal.dao.model.TCrossLights;
import org.springframework.stereotype.Repository;

/**
* @Description: [路口灯组表service]</p>
* @author yinguijin
* @version 1.0
* Created on 2019年04月30日
*/
@Repository
public interface TCrossLightsService extends BaseService<TCrossLights> {

    /**
     * @description: 保存信号机灯组信息列表
     * @author yinguijin
     * @date 2019/4/30 11:09
     */
    void initCrossLights(ParamEntity<BaseSignal> param);
}
