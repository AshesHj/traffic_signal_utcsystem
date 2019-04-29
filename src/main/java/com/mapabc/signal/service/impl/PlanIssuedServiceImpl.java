package com.mapabc.signal.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mapabc.signal.common.component.HttpRestEntity;
import com.mapabc.signal.common.component.ParamEntity;
import com.mapabc.signal.common.component.Result;
import com.mapabc.signal.common.constant.Const;
import com.mapabc.signal.common.util.HttpRestUtil;
import com.mapabc.signal.common.util.StringUtils;
import com.mapabc.signal.dao.vo.phase.PhasePlanVo;
import com.mapabc.signal.dao.vo.runplan.RunplanVo;
import com.mapabc.signal.dao.vo.sectionplan.SectionPlanVo;
import com.mapabc.signal.dao.vo.timeplan.TimePlanVo;
import com.mapabc.signal.service.PlanIssuedService;
import org.springframework.stereotype.Service;

/**
 * @description: [相位/配时/时段/运行方案下发API接口service实现]
 * @author yinguijin
 * @version 1.0
 * Created on 2019/4/28 20:26
 */
@Service
public class PlanIssuedServiceImpl implements PlanIssuedService {

    /**
     * @description: 相位方案下发-->下发相位方案信息到路口信号机或信号系统中
     * @param param 下发的相位数据
     * @return Result 发送结果
     * @author yinguijin
     * @date 2019/4/28 20:24
     */
    @Override
    public Result updatePhasePlans(ParamEntity<PhasePlanVo> param) {
        Result result = new Result();
        //请求接口地址
        HttpRestEntity httpRestEntity = new HttpRestEntity(param.getSourceType(), "updatePhasePlans");
        String responseResult = HttpRestUtil.doPost(httpRestEntity.getUrl(), httpRestEntity.getHeaders(), JSON.toJSONString(param), String.class);
        if (StringUtils.isEmpty(responseResult)) {//没有返回值
            return result.renderSuccess();
        }
        //获取接口返回值
        JSONObject obj = JSON.parseObject(responseResult);
        Object isSuccess = obj.get("result");
        Object description = obj.get("description");
        //判断结果
        boolean bool = false;
        String msg = "";
        if (null != isSuccess) {
            if (Integer.valueOf((String)isSuccess).equals(Const.TRUE)) {
                bool = true;
            }
        }
        if (null != description) {
            msg = (String)description;
        }
        //成功
        if (bool) {
            return result.renderSuccess(msg);
        }
        //失败
        return result.renderError(msg);
    }


    /**
     * @description: 配时方案下发-->下发配时方案信息到路口信号机或信号系统中
     * @param param 下发的配时数据
     * @return Result 发送结果
     * @author yinguijin
     * @date 2019/4/28 20:24
     */
    @Override
    public Result updateTimePlans(ParamEntity<TimePlanVo> param) {
        Result result = new Result();
        //请求接口地址
        HttpRestEntity httpRestEntity = new HttpRestEntity(param.getSourceType(), "updateTimePlans");
        String responseResult = HttpRestUtil.doPost(httpRestEntity.getUrl(), httpRestEntity.getHeaders(), JSON.toJSONString(param), String.class);
        if (StringUtils.isEmpty(responseResult)) {//没有返回值
            return result.renderSuccess();
        }
        //获取接口返回值
        JSONObject obj = JSON.parseObject(responseResult);
        Object isSuccess = obj.get("result");
        Object description = obj.get("description");
        //判断结果
        boolean bool = false;
        String msg = "";
        if (null != isSuccess) {
            if (Integer.valueOf((String)isSuccess).equals(Const.TRUE)) {
                bool = true;
            }
        }
        if (null != description) {
            msg = (String)description;
        }
        //成功
        if (bool) {
            return result.renderSuccess(msg);
        }
        //失败
        return result.renderError(msg);
    }

    /**
     * @param param 下发的时段方案数据
     * @return Result 发送结果
     * @description: 时段方案下发-->下发时段方案信息到路口信号机或信号系统中
     * @author yinguijin
     * @date 2019/4/28 20:24
     */
    @Override
    public Result updateSectionPlans(ParamEntity<SectionPlanVo> param) {
        Result result = new Result();
        //请求接口地址
        HttpRestEntity httpRestEntity = new HttpRestEntity(param.getSourceType(), "updateSectionPlans");
        String responseResult = HttpRestUtil.doPost(httpRestEntity.getUrl(), httpRestEntity.getHeaders(), JSON.toJSONString(param), String.class);
        if (StringUtils.isEmpty(responseResult)) {//没有返回值
            return result.renderSuccess();
        }
        //获取接口返回值
        JSONObject obj = JSON.parseObject(responseResult);
        Object isSuccess = obj.get("result");
        Object description = obj.get("description");
        //判断结果
        boolean bool = false;
        String msg = "";
        if (null != isSuccess) {
            if (Integer.valueOf((String)isSuccess).equals(Const.TRUE)) {
                bool = true;
            }
        }
        if (null != description) {
            msg = (String)description;
        }
        //成功
        if (bool) {
            return result.renderSuccess(msg);
        }
        //失败
        return result.renderError(msg);
    }

    /**
     * @description: 运行计划下发-->下发运行计划信息到路口信号机或信号系统中
     * @param param 下发的运行方案数据
     * @return Result 发送结果
     * @author yinguijin
     * @date 2019/4/28 20:24
     */
    @Override
    public Result updateRunPlan(ParamEntity<RunplanVo> param) {
        Result result = new Result();
        //请求接口地址
        HttpRestEntity httpRestEntity = new HttpRestEntity(param.getSourceType(), "updateRunPlan");
        String responseResult = HttpRestUtil.doPost(httpRestEntity.getUrl(), httpRestEntity.getHeaders(), JSON.toJSONString(param), String.class);
        if (StringUtils.isEmpty(responseResult)) {//没有返回值
            return result.renderSuccess();
        }
        //获取接口返回值
        JSONObject obj = JSON.parseObject(responseResult);
        Object isSuccess = obj.get("result");
        Object description = obj.get("description");
        //判断结果
        boolean bool = false;
        String msg = "";
        if (null != isSuccess) {
            if (Integer.valueOf((String)isSuccess).equals(Const.TRUE)) {
                bool = true;
            }
        }
        if (null != description) {
            msg = (String)description;
        }
        //成功
        if (bool) {
            return result.renderSuccess(msg);
        }
        //失败
        return result.renderError(msg);
    }
}
