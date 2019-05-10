package com.mapabc.signal.service;

import com.mapabc.signal.dao.model.TelesemeLights;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @Description: [灯组字典service]</p>
* @author yinguijin
* @version 1.0
* Created on 2019年05月05日
*/
@Repository
public interface TelesemeLightsService extends BaseService<TelesemeLights> {

    /**
     * @description: 根据条件查询
     * @param telesemeLights 查询条件
     * @return 结果list
     * @author yinguijin
     * @date 2019/5/5 15:12
    */
    List<TelesemeLights> selectByExample(TelesemeLights telesemeLights);
}
