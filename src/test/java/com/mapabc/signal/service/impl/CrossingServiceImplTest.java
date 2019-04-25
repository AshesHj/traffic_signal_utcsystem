package com.mapabc.signal.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mapabc.signal.ApplicationTests;
import com.mapabc.signal.common.component.BaseSignal;
import com.mapabc.signal.common.component.VendorResult;
import com.mapabc.signal.dao.vo.cross.CrossingVo;
import com.mapabc.signal.service.CrossingService;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [描述该类概要功能介绍]
 * Created on 2019/4/24 17:08
 */
public class CrossingServiceImplTest extends ApplicationTests {

    @Resource
    private CrossingService crossingService;

    @Test
    public void login() {
        JSONObject login = crossingService.login();
        System.out.println(JSON.toJSON(login));
    }

    @Test
    public void queryCrossing() {
        VendorResult<BaseSignal> param = new VendorResult<>();
//        param.setSystemType("UTC");
//        param.setSourceType(BaseEnum.VendorTypeEnum.QS.getNick());
//        param.setUpdateTime(new Date());
//        param.setDataContent();
        VendorResult<CrossingVo> result = crossingService.queryCrossing(null);
        System.out.println(JSON.toJSON(result));
    }
}