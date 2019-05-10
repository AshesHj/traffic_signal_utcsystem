package com.mapabc.signal.service.impl;

import com.mapabc.signal.ApplicationTests;
import com.mapabc.signal.service.TBaseCrossCoilFlowService;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author yinguijin
 * @version 1.0
 * Created on 2019/5/9 12:03
 * @description: []
 */
public class TBaseCrossCoilFlowServiceImplTest extends ApplicationTests {

    @Resource
    private TBaseCrossCoilFlowService tBaseCrossCoilFlowService;

    @Test
    public void saveCoilFlow() {
        tBaseCrossCoilFlowService.saveCoilFlow();
    }
}