package com.mapabc.signal.service.impl;

import com.alibaba.fastjson.JSON;
import com.mapabc.signal.common.component.BaseSignal;
import com.mapabc.signal.common.component.HttpRestEntity;
import com.mapabc.signal.common.component.ParamEntity;
import com.mapabc.signal.common.component.VendorResult;
import com.mapabc.signal.common.util.HttpRestUtil;
import com.mapabc.signal.dao.vo.signal.SignalInfoVo;
import com.mapabc.signal.dao.vo.signalalarms.SignalAlarmsVo;
import com.mapabc.signal.dao.vo.signalrunstatus.SignalRunStatusVo;
import com.mapabc.signal.dao.vo.signalvolume.SignalVolumeVo;
import com.mapabc.signal.service.SignalRunService;
import org.springframework.stereotype.Service;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [信号灯运行数据接口service实现]
 * Created on 2019/4/24 21:52
 */
@Service
public class SignalRunServiceImpl implements SignalRunService {

    /**
     * @description: 获取路口信号机的实时运行状态数据
     * @param param 请求参数
     * @return SignalRunStatusVo 信号机运行状态
     * @author yinguijin
     * @date 2019/4/22 17:09
     */
    @Override
    public VendorResult<SignalRunStatusVo> queryRunstate(ParamEntity<BaseSignal> param) {
        //请求接口地址
        HttpRestEntity httpRestEntity = new HttpRestEntity(param.getSourceType(), "queryRunstate");
        return HttpRestUtil.doGet(httpRestEntity.getUrl(), httpRestEntity.getHeaders(), JSON.toJSONString(param));
    }

    /**
     * @description: 获取路口信号机的实时告警数据
     * @param param 请求参数
     * @return SignalRunStatusVo 信号机告警数据
     * @author yinguijin
     * @date 2019/4/22 17:09
     */
    @Override
    public VendorResult<SignalAlarmsVo> queryAlarms(ParamEntity<BaseSignal> param) {
        //请求接口地址
        HttpRestEntity httpRestEntity = new HttpRestEntity(param.getSourceType(), "queryAlarms");
        return HttpRestUtil.doGet(httpRestEntity.getUrl(), httpRestEntity.getHeaders(), JSON.toJSONString(param));
    }

    /**
     * @description: 获取路口信号机的实时灯态数据
     * @param param 请求参数
     * @return SignalInfoVo 信号机灯态信息
     * @author yinguijin
     * @date 2019/4/22 17:09
     */
    @Override
    public VendorResult<SignalInfoVo> querySignalInfo(ParamEntity<BaseSignal> param) {
        //请求接口地址
        HttpRestEntity httpRestEntity = new HttpRestEntity(param.getSourceType(), "querySignalInfo");
        return HttpRestUtil.doGet(httpRestEntity.getUrl(), httpRestEntity.getHeaders(), JSON.toJSONString(param));
    }

    /**
     * @description: 获取路口信号机的实时流量数据
     * @param param 请求参数
     * @return SignalVolumeVo 信号机的实时流量
     * @author yinguijin
     * @date 2019/4/22 17:09
     */
    @Override
    public VendorResult<SignalVolumeVo> querySignalVolume(ParamEntity<BaseSignal> param) {
        //请求接口地址
        HttpRestEntity httpRestEntity = new HttpRestEntity(param.getSourceType(), "querySignalVolume");
        return HttpRestUtil.doGet(httpRestEntity.getUrl(), httpRestEntity.getHeaders(), JSON.toJSONString(param));
    }
}
