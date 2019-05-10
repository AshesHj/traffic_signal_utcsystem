package com.mapabc.signal.service.impl;

import com.alibaba.fastjson.JSON;
import com.mapabc.signal.ApplicationTests;
import com.mapabc.signal.common.constant.Const;
import com.mapabc.signal.dao.model.TBaseVendorMethod;
import com.mapabc.signal.service.TBaseVendorMethodService;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.List;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [描述该类概要功能介绍]
 * Created on 2019/4/25 14:23
 */
public class TBaseVendorMethodServiceImplTest  extends ApplicationTests {

    @Resource
    private TBaseVendorMethodService tBaseVendorMethodService;

    @Test
    public void queryList() {
        TBaseVendorMethod entity = new TBaseVendorMethod();
        entity.setIsDelete(Const.IS_DELETE_NO);
        List<TBaseVendorMethod> vendorMethods = tBaseVendorMethodService.select(entity);
        System.out.println(JSON.toJSON(vendorMethods));
    }

}