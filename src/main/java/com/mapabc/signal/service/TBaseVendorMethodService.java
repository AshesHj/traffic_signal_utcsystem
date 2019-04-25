package com.mapabc.signal.service;

import com.mapabc.signal.dao.model.TBaseVendorMethod;
import org.springframework.stereotype.Repository;

/**
* @Description: [厂商接口信息表service]</p>
* @author yinguijin
* @version 1.0
* Created on 2019年04月25日
*/
@Repository
public interface TBaseVendorMethodService extends BaseService<TBaseVendorMethod> {

    /**
     * @description: 项目启动加载厂商url到内存
     * @author yinguijin
     * @date 2019/4/25 16:36
     */
    void initLoadVendor();
}
