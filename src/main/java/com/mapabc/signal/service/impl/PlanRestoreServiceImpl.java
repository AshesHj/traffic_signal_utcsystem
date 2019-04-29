package com.mapabc.signal.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mapabc.signal.common.component.HttpRestEntity;
import com.mapabc.signal.common.component.ParamEntity;
import com.mapabc.signal.common.component.Result;
import com.mapabc.signal.common.constant.Const;
import com.mapabc.signal.common.util.HttpRestUtil;
import com.mapabc.signal.common.util.StringUtils;
import com.mapabc.signal.service.PlanRestoreService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: [控制指令-方案计划恢复service实现]
 * @author yinguijin
 * @version 1.0
 * Created on 2019/4/29 9:50
 */
@Service
public class PlanRestoreServiceImpl implements PlanRestoreService {


    /**
     * @description: 恢复时间--》在信号机设置特殊控制之后，用于恢复时间表方案
     * @param signalId   信号机编号
     * @param sourceType 厂家简称 QS/SCATS/HS/HK
     * @param signalType 信号机类型 QS/SCATS/HS/HK
     * @param command    1 恢复
     * @return Result 执行结果
     * @author yinguijin
     * @date 2019/4/29 9:52
     */
    @Override
    public Result updateNormalPlan(String signalId, String sourceType, String signalType, int command) {
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
        HttpRestEntity httpRestEntity = new HttpRestEntity(param.getSourceType(), "updateNormalPlan");
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
