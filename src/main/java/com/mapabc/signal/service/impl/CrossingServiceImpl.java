package com.mapabc.signal.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mapabc.signal.common.component.BaseSignal;
import com.mapabc.signal.common.component.HttpRestEntity;
import com.mapabc.signal.common.component.ParamEntity;
import com.mapabc.signal.common.component.VendorResult;
import com.mapabc.signal.common.util.HttpRestUtil;
import com.mapabc.signal.dao.vo.cross.CrossingVo;
import com.mapabc.signal.dao.vo.detector.DetectorVo;
import com.mapabc.signal.dao.vo.phase.PhasePlanVo;
import com.mapabc.signal.dao.vo.runplan.RunplanVo;
import com.mapabc.signal.dao.vo.sectionplan.SectionPlanVo;
import com.mapabc.signal.dao.vo.timeplan.TimePlanVo;
import com.mapabc.signal.service.CrossingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [信号灯静态数据获取接口实现-控制路口接口实现]
 * Created on 2019/4/24 14:13
 */
@Service
public class CrossingServiceImpl implements CrossingService {

    //URL前缀
    @Value("${signalamp.vendor.qs.rootpath}")
    private String rootpath;

    //账号密码
    @Value("${signalamp.vendor.qs.username}")
    private String username;

    @Value("${signalamp.vendor.qs.password}")
    private String password;

    //登录青松
    @Value("${signalamp.vendor.qs.login}")
    private String loginUrl;

    //获取信号机列表
    @Value("${signalamp.vendor.qs.getSignalList}")
    private String getSignalListUrl;

    /**
     * @description: 青松接口登录
     * @return com.alibaba.fastjson.JSONObject 获取登录后的返回结果
     * @author yinguijin
     * @date 2019/4/25 9:28
     */
    public JSONObject login() {
        String url = rootpath + loginUrl;
        JSONObject param = new JSONObject();
        param.put("username", username);
        param.put("password", password);
        return JSON.parseObject(HttpRestUtil.doExecute(url, handlerHeader(), HttpMethod.POST, JSON.toJSONString(param)));
    }

    /**
     * Discription:[封装请求头]
     * @author: yinguijin
     * @date 2019/4/24 15:23
     */
    private Map<String, String> handlerHeader() {
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type","application/json; charset=UTF-8");
        header.put("Accept", MediaType.APPLICATION_JSON.toString()) ;
        return header;
    }


    /**
     * @description: 获取信号控制系统所有控制路口基本信息
     * @return CrossingVo 路口信息结果集合
     * @author yinguijin
     * @date 2019/4/19 14:48
     */
    @Override
    public VendorResult<CrossingVo> queryCrossing(ParamEntity<BaseSignal> param) {
        //请求接口地址
        HttpRestEntity httpRestEntity = new HttpRestEntity(param.getSourceType(), "queryCrossing");
        return HttpRestUtil.doGet(httpRestEntity.getUrl(), httpRestEntity.getHeaders(), null);
    }

    /**
     * @description: 获取路口的线圈信息
     * @param param  请求参数信号灯信息
     * @return  DetectorVo 线圈信息结果集合
     * @author yinguijin
     * @date 2019/4/19 14:49
     */
    @Override
    public VendorResult<DetectorVo> queryDetector(ParamEntity<BaseSignal> param) {
        //请求接口地址
        HttpRestEntity httpRestEntity = new HttpRestEntity(param.getSourceType(), "queryDetector");
        return HttpRestUtil.doGet(httpRestEntity.getUrl(), httpRestEntity.getHeaders(), JSON.toJSONString(param));
    }

    /**
     * @description: 路口相位方案数据获取
     * @param param  请求参数相位方案
     * @return  PhasePlanVo 相位方案数据集合
     * @author yinguijin
     * @date 2019/4/19 14:49
     */
    @Override
    public VendorResult<PhasePlanVo> queryPhasePlan(ParamEntity<BaseSignal> param) {
        //请求接口地址
        HttpRestEntity httpRestEntity = new HttpRestEntity(param.getSourceType(), "queryPhasePlan");
        return HttpRestUtil.doGet(httpRestEntity.getUrl(), httpRestEntity.getHeaders(), JSON.toJSONString(param));
    }

    /**
     * @description: 路口的配时方案信息获取
     * @param param  请求参数配时方案
     * @return  TimePlanVo 路口的配时方案数据集合
     * @author yinguijin
     * @date 2019/4/19 14:49
     */
    @Override
    public VendorResult<TimePlanVo> queryTimePlan(ParamEntity<BaseSignal> param) {
        //请求接口地址
        HttpRestEntity httpRestEntity = new HttpRestEntity(param.getSourceType(), "queryTimePlan");
        return HttpRestUtil.doGet(httpRestEntity.getUrl(), httpRestEntity.getHeaders(), JSON.toJSONString(param));
    }

    /**
     * @description: 路口的时段方案（日时段方案）获取
     * @param param  请求参数时段方案
     * @return  SectionPlanVo 路口的时段方案数据集合
     * @author yinguijin
     * @date 2019/4/19 14:49
     */
    @Override
    public VendorResult<SectionPlanVo> querySectionPlan(ParamEntity<BaseSignal> param) {
        //请求接口地址
        HttpRestEntity httpRestEntity = new HttpRestEntity(param.getSourceType(), "querySectionPlan");
        return HttpRestUtil.doGet(httpRestEntity.getUrl(), httpRestEntity.getHeaders(), JSON.toJSONString(param));
    }

    /**
     * @description: 路口的运行计划表获取
     * @param param  请求参数时段方案
     * @return  RunplanVo 路口的运行计划数据集合
     * @author yinguijin
     * @date 2019/4/19 14:49
     */
    @Override
    public VendorResult<RunplanVo> queryRunplan(ParamEntity<BaseSignal> param) {
        //请求接口地址
        HttpRestEntity httpRestEntity = new HttpRestEntity(param.getSourceType(), "queryRunplan");
        return HttpRestUtil.doGet(httpRestEntity.getUrl(), httpRestEntity.getHeaders(), JSON.toJSONString(param));
    }
}
