package com.mapabc.signal.service.impl;

import com.mapabc.signal.dao.mapper.TelesemeLightsMapper;
import com.mapabc.signal.dao.model.TelesemeLights;
import com.mapabc.signal.service.TelesemeLightsService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
* @Description: [service实现]</p>
* @author yinguijin
* @version 1.0
* Created on 2019年05月05日
*/
@Service
public class TelesemeLightsServiceImpl extends BaseServiceImpl<TelesemeLights, TelesemeLightsMapper> implements TelesemeLightsService {

    /**
     * @param telesemeLights 查询条件
     * @return 结果list
     * @description: 根据条件查询
     * @author yinguijin
     * @date 2019/5/5 15:12
     */
    @Override
    public List<TelesemeLights> selectByExample(TelesemeLights telesemeLights) {
        Example example = new Example(telesemeLights.getClass());
        buildCriteriaByEntity(telesemeLights, example);
        return myBaseMapper.selectByExample(example);
    }
}
