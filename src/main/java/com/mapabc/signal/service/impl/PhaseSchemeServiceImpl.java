package com.mapabc.signal.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mapabc.signal.common.component.HttpRestEntity;
import com.mapabc.signal.common.component.ParamEntity;
import com.mapabc.signal.common.component.Result;
import com.mapabc.signal.common.constant.Const;
import com.mapabc.signal.common.util.HttpRestUtil;
import com.mapabc.signal.common.util.StringUtils;
import com.mapabc.signal.dao.vo.phase.PhaseLockVo;
import com.mapabc.signal.dao.vo.timeplan.TimePlanVo;
import com.mapabc.signal.service.PhaseSchemeService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: [控制指令-相位锁定/步进/当前方案优化API接口-service实现]
 * @author yinguijin
 * @version 1.0
 * Created on 2019/4/28 15:27
 */
@Service
public class PhaseSchemeServiceImpl implements PhaseSchemeService {

    /**
     * @description: 当前方案优化调整--》设置当前路口信号机的方案优化
     * @param param 配时方案实体类
     * @return Result 返回结果
     * @author yinguijin
     * @date 2019/4/28 14:34
     */
    public Result updateTimePlan(ParamEntity<TimePlanVo> param) {
        Result result = new Result();
        //请求接口地址
        HttpRestEntity httpRestEntity = new HttpRestEntity(param.getSourceType(), "updateTimePlan");
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
     * @description: 相位锁定控制--》设置当前路口信号机的一个或多个相位放行
     * @param param 相位锁定实体类
     * @return true 成功，false 失败
     * @author yinguijin
     * @date 2019/4/28 14:34
     */
    public Result updateLockPhase(ParamEntity<PhaseLockVo> param) {
        Result result = new Result();
        //请求接口地址
        HttpRestEntity httpRestEntity = new HttpRestEntity(param.getSourceType(), "updateLockPhase");
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
     * @description: 相位步进控制--》设置当前路口信号机由一个相位向下一个相位或后续某个相位进行平滑过渡
     * @param sourceType 厂家简称 QS/SCATS/HS/HK
     * @param signalId 信号机编号
     * @param signalType 信号机类型 QS/SCATS/HS/HK
     * @param command 1 开始步进 0 取消步进
     * @param stepNum 0 顺序步进 n 跳过n个相位
     * @return true 成功，false 失败
     * @author yinguijin
     * @date 2019/4/28 14:34
     */
    public Result updateStep(String signalId, String sourceType, String signalType, int command, int stepNum) {
        Result result = new Result();
        //封装请求参数
        Map<String, Object> dataContent = new HashMap<>();
        dataContent.put("signalId", signalId);
        dataContent.put("signalType", signalType);
        dataContent.put("command", command);
        dataContent.put("stepNum", stepNum);
        ParamEntity<Map<String, Object>> param = new ParamEntity<>();
        param.setSourceType(sourceType);
        param.setUpdateTime(new Date());
        param.setSystemType(Const.SYSTEM_TYPE);
        param.setDataContent(dataContent);
        //请求接口地址
        HttpRestEntity httpRestEntity = new HttpRestEntity(param.getSourceType(), "updateStep");
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
     * @description: 强制方案控制--》设置当前路口信号机执行某方案
     * @param sourceType 厂家简称 QS/SCATS/HS/HK
     * @param signalId 信号机编号
     * @param signalType 信号机类型 QS/SCATS/HS/HK
     * @param timePlanId 配时方案编号
     * @return true 成功，false 失败
     * @author yinguijin
     * @date 2019/4/28 14:34
     */
    public Result updateForcePlan(String signalId, String sourceType, String signalType, String timePlanId) {
        Result result = new Result();
        //封装请求参数
        Map<String, Object> dataContent = new HashMap<>();
        dataContent.put("signalId", signalId);
        dataContent.put("signalType", signalType);
        dataContent.put("timePlanId", timePlanId);
        ParamEntity<Map<String, Object>> param = new ParamEntity<>();
        param.setSourceType(sourceType);
        param.setUpdateTime(new Date());
        param.setSystemType(Const.SYSTEM_TYPE);
        param.setDataContent(dataContent);
        //请求接口地址
        HttpRestEntity httpRestEntity = new HttpRestEntity(param.getSourceType(), "updateForcePlan");
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
