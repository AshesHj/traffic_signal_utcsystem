package com.mapabc.signal.service.source.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mapabc.signal.common.component.HttpRestEntity;
import com.mapabc.signal.common.component.ParamEntity;
import com.mapabc.signal.common.component.Result;
import com.mapabc.signal.common.constant.Const;
import com.mapabc.signal.common.util.HttpRestUtil;
import com.mapabc.signal.common.util.StringUtils;
import com.mapabc.signal.service.source.PlanSignalLlightService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: [控制指令-全红/黄灯/关灯方案下发API接口service实现]
 * @author yinguijin
 * @version 1.0
 * Created on 2019/4/28 20:04
 */
@Service
public class PlanSignalLlightServiceImpl implements PlanSignalLlightService {

    /**
     * @description: 全红控制--》设置当前路口信号机是否要执行全红
     * @param signalId 信号机编号
     * @param sourceType 厂家简称
     * @param signalType 信号机类型
     * @param command 1 全红控制 0 取消全红
     * @return Result 返回结果
     * @author yinguijin
     * @date 2019/4/28 19:51
     */
    @Override
    public Result updateAllRed(String signalId, String sourceType, String signalType, Integer command) {
        Result result = new Result();
        //封装请求参数
        Map<String, Object> dataContent = new HashMap<>();
        dataContent.put("signalId", signalId);
        dataContent.put("signalType", signalType);
        dataContent.put("command", command);
        ParamEntity<Map<String, Object>> param = new ParamEntity<>();
        param.setSourceType(sourceType);
        param.setUpdateTime(new Date());
        param.setSystemType(Const.SYSTEM_TYPE);
        param.setDataContent(dataContent);
        //请求接口地址
        HttpRestEntity httpRestEntity = new HttpRestEntity(param.getSourceType(), "updateAllRed");
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
     * @description: 黄闪控制--》设置当前路口信号机是否要执行黄闪
     * @param signalId 信号机编号
     * @param sourceType 厂家简称
     * @param signalType 信号机类型
     * @param command 1 黄闪控制 0 取消黄闪
     * @return Result 返回结果
     * @author yinguijin
     * @date 2019/4/28 19:51
     */
    @Override
    public Result updateYellowFlash(String signalId, String sourceType, String signalType, Integer command) {
        Result result = new Result();
        //封装请求参数
        Map<String, Object> dataContent = new HashMap<>();
        dataContent.put("signalId", signalId);
        dataContent.put("signalType", signalType);
        dataContent.put("command", command);
        ParamEntity<Map<String, Object>> param = new ParamEntity<>();
        param.setSourceType(sourceType);
        param.setUpdateTime(new Date());
        param.setSystemType(Const.SYSTEM_TYPE);
        param.setDataContent(dataContent);
        //请求接口地址
        HttpRestEntity httpRestEntity = new HttpRestEntity(param.getSourceType(), "updateYellowFlash");
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
     * @description: 关灯控制--》设置当前路口信号机是否要执行开灯或关灯
     * @param signalId 信号机编号
     * @param sourceType 厂家简称
     * @param signalType 信号机类型
     * @param command 1 开灯 0 关灯
     * @return Result 返回结果
     * @author yinguijin
     * @date 2019/4/28 19:51
     */
    @Override
    public Result updateOff(String signalId, String sourceType, String signalType, Integer command) {
        Result result = new Result();
        //封装请求参数
        Map<String, Object> dataContent = new HashMap<>();
        dataContent.put("signalId", signalId);
        dataContent.put("signalType", signalType);
        dataContent.put("command", command);
        ParamEntity<Map<String, Object>> param = new ParamEntity<>();
        param.setSourceType(sourceType);
        param.setUpdateTime(new Date());
        param.setSystemType(Const.SYSTEM_TYPE);
        param.setDataContent(dataContent);
        //请求接口地址
        HttpRestEntity httpRestEntity = new HttpRestEntity(param.getSourceType(), "updateOff");
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
