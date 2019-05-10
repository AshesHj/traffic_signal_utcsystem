package com.mapabc.signal.service;

import com.mapabc.signal.common.component.ParamEntity;
import com.mapabc.signal.dao.model.TBaseCrossCoilInfo;
import org.springframework.stereotype.Repository;

/**
* @Description: [路口线圈信息表service]</p>
* @author yinguijin
* @version 1.0
* Created on 2019年05月08日
*/
@Repository
public interface TBaseCrossCoilInfoService extends BaseService<TBaseCrossCoilInfo> {

    /**
     * @description: 加载路口线圈信息
     * @param param 信号机信息
     * @author yinguijin
     * @date 2019/5/8 14:20
    */
    void initDetector(ParamEntity param);
}
