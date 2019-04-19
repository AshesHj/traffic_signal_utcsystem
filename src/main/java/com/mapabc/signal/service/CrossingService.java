package com.mapabc.signal.service;

import com.mapabc.signal.common.component.BaseSignal;
import com.mapabc.signal.common.component.VendorResult;
import com.mapabc.signal.dao.model.cross.Crossing;
import com.mapabc.signal.dao.model.detector.DetectorVo;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [控制路口Service]
 * Created on 2019/4/18 18:43
 */
public interface CrossingService {

    /**
     * @description: 获取信号控制系统所有控制路口基本信息
     * @return VendorResult 路口信息结果集合
     * @author yinguijin
     * @date 2019/4/19 14:48
    */
    VendorResult<Crossing> queryCrossing();

    /**
     * @description: 获取路口的线圈信息
     * @param param  请求参数信号灯信息
     * @return  VendorResult 线圈信息结果集合
     * @author yinguijin 
     * @date 2019/4/19 14:49 
    */
    VendorResult<DetectorVo> queryDetector(VendorResult<BaseSignal> param);


}
