package com.mapabc.signal.service.qs.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mapabc.signal.common.component.HttpRestEntity;
import com.mapabc.signal.common.component.Result;
import com.mapabc.signal.common.constant.Const;
import com.mapabc.signal.common.constant.RedisKeyConst;
import com.mapabc.signal.common.enums.BaseEnum;
import com.mapabc.signal.common.util.HttpRestUtil;
import com.mapabc.signal.common.util.ListUtil;
import com.mapabc.signal.common.util.RedisUtil;
import com.mapabc.signal.dao.vo.phase.Phase;
import com.mapabc.signal.dao.vo.phase.PhasePlanVo;
import com.mapabc.signal.dao.vo.runplan.RunplanVo;
import com.mapabc.signal.dao.vo.sectionplan.SectionPlan;
import com.mapabc.signal.dao.vo.sectionplan.SectionPlanVo;
import com.mapabc.signal.dao.vo.sectionplan.Timeslice;
import com.mapabc.signal.dao.vo.timeplan.Ring;
import com.mapabc.signal.dao.vo.timeplan.TimePlanVo;
import com.mapabc.signal.service.qs.QsPutPlanSignalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: [青松信号机方案下发service实现]
 * @author yinguijin
 * @version 1.0
 * Created on 2019/5/9 17:15
 */
@Service
public class QsPutPlanSignalServiceImpl implements QsPutPlanSignalService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QsPutPlanSignalServiceImpl.class);

    //相位锁定
    private static final Integer LOCK_RUNNING_MODE = 2;

    //手动全红
    private static final Integer RED_RUNNING_MODE = 3;

    //手动黄闪
    private static final Integer YELLOW_RUNNING_MODE = 4;

    //恢复正常方案
    private static final Integer NORMAL_RUNNING_MODE = 8;

    //恢复正常方案
    private static final Integer INTERIM_PLAN_RUNNING_MODE = 255;

    //redis
    @Resource
    private RedisUtil redisUtil;

    /**
     * @description: 设置当前路口信号机的优化方案
     * @param timePlanVo 配时方案
     * @return 请求响应结果
     * @author yinguijin
     * @date 2019/5/9 17:13
     */
    @Override
    public Result updateQsTimePlan(TimePlanVo timePlanVo) {
        Result result = new Result();
        try {
            //获取URL 封装请求头
            HttpRestEntity restEntity = new HttpRestEntity(BaseEnum.VendorTypeEnum.QS.getNick(), "querySectionPlan");
            String urlTemplate = restEntity.getUrl();
            Map<String, String> headers = restEntity.getHeaders();
            headers.put("Authorization","Bearer " + redisUtil.get(RedisKeyConst.KEY_PREFIX + RedisKeyConst.TOKEN).toString());
            String url = MessageFormat.format(urlTemplate, timePlanVo.getSignalId(), timePlanVo.getPhasePlanId());
            //封装请求参数
            List<Ring> rings = timePlanVo.getRings();
            if (ListUtil.isEmpty(rings)) {
                return result.renderError(HttpStatus.BAD_REQUEST.value(), "参数错误");
            }
            List<Phase> phases = rings.get(0).getPhases();
            if (ListUtil.isEmpty(phases)) {
                return result.renderError(HttpStatus.BAD_REQUEST.value(), "参数错误");
            }
            // 绿信比
            Map<String,List<String>> greenLetterMap = new HashMap<>();
            // 原始绿
            List<String> oldLengths = new ArrayList<>();
            // 优化绿
            List<String> newLengths = new ArrayList<>();
            // 排序
            ListUtil.sortList(phases, "phaseOrderId", ListUtil.SORT_ASC);
            for (Phase phase : phases) {
                oldLengths.add(phase.getOriginalTime().toString());
                newLengths.add(phase.getPhaseTime().toString());
            }
            greenLetterMap.put("oldLengths", oldLengths) ;
            greenLetterMap.put("newLengths", newLengths) ;
            //发送请求
            HttpRestUtil.doExecute(url, headers, HttpMethod.PUT, JSON.toJSONString(greenLetterMap));
            return result.renderSuccess();
        } catch(HttpClientErrorException e) {
            HttpStatus statusCode = e.getStatusCode();
            if (HttpStatus.UNAUTHORIZED.equals(statusCode)) {//401
                //重新登录-刷新Redis中token
                //this.login(); TODO 测试阶段注释
                LOGGER.error("认证失败，Token失效，已经重新刷新token", e);
                return result.renderError(HttpStatus.UNAUTHORIZED.value(), "认证失败，Token失效，已经重新刷新token");
            } else if(HttpStatus.NOT_FOUND.equals(statusCode)) {//404
                LOGGER.error("请求URL地址不存在！", e);
                return result.renderError(HttpStatus.NOT_FOUND.value(), "请求URL地址不存在！");
            }
            throw new RuntimeException("当前路口信号机的优化方案下发失败，接口响应500。", e);
        }  catch(Exception e) {
            throw new RuntimeException("系统繁忙，服务器端内部错误！", e);
        }
    }

    /**
     * @description: 相位锁定控制--》设置当前路口信号机的一个或多个相位放行
     * @param signalId 信号机编号
     * @param command 1 锁定当前相位 0 取消
     * @return Result 返回结果
     * @author yinguijin
     * @date 2019/4/28 14:34
     */
    @Override
    public Result updateQsLockPhase(String signalId, Integer command) {
        if (command == Const.TRUE) {//锁定方案
            return this.updateManual(signalId, LOCK_RUNNING_MODE);
        } else {//恢复正常
            return this.updateManual(signalId, NORMAL_RUNNING_MODE);
        }
    }

    /**
     * @param signalId 信号机编号
     * @param command  1 全红控制 0 取消全红
     * @return Result 返回结果
     * @description: 全红控制--》设置当前路口信号机是否要执行全红
     * @author yinguijin
     * @date 2019/4/28 19:51
     */
    @Override
    public Result updateQsAllRed(String signalId, Integer command) {
        if (command == Const.TRUE) {//全红方案
            return this.updateManual(signalId, RED_RUNNING_MODE);
        } else {//恢复正常
            return this.updateManual(signalId, NORMAL_RUNNING_MODE);
        }
    }

    /**
     * @param signalId 信号机编号
     * @param command  1 黄闪控制 0 取消黄闪
     * @return Result 返回结果
     * @description: 黄闪控制--》设置当前路口信号机是否要执行黄闪
     * @author yinguijin
     * @date 2019/4/28 19:51
     */
    @Override
    public Result updateQsYellowFlash(String signalId, Integer command) {
        if (command == Const.TRUE) {//黄闪方案
            return this.updateManual(signalId, YELLOW_RUNNING_MODE);
        } else {//恢复正常
            return this.updateManual(signalId, NORMAL_RUNNING_MODE);
        }
    }

    /**
     * @param signalId 信号机编号
     * @return Result 执行结果
     * @description: 恢复时间--》在信号机设置特殊控制之后，用于恢复时间表方案
     * @author yinguijin
     * @date 2019/4/29 9:45
     */
    @Override
    public Result updateQsNormalPlan(String signalId) {
        //恢复正常
        return this.updateManual(signalId, NORMAL_RUNNING_MODE);
    }

    /**
     * @description: 调用青松接口执行手动控制
     * @param signalId 信号机编号
     * @param runningMode `2=手动锁定当前相位`，`3=手动全红`，`4=手动黄闪`，`8=正常按计划运行`，`255=临时控制方案`
     * @return com.mapabc.signal.common.component.Result 请求响应结果
     * @author yinguijin
     * @date 2019/5/10 10:36
     */
    private Result updateManual(String signalId, Integer runningMode) {
        Result result = new Result();
        try {
            //获取URL 封装请求头
            HttpRestEntity restEntity = new HttpRestEntity(BaseEnum.VendorTypeEnum.QS.getNick(), "updateManual");
            String urlTemplate = restEntity.getUrl();
            Map<String, String> headers = restEntity.getHeaders();
            headers.put("Authorization","Bearer " + redisUtil.get(RedisKeyConst.KEY_PREFIX + RedisKeyConst.TOKEN).toString());
            String url = MessageFormat.format(urlTemplate, signalId);
            //封装请求参数
            JSONObject param = new JSONObject();
            param.put("runningMode", runningMode) ;
            //发送请求
            HttpRestUtil.doPost(url, headers, JSON.toJSONString(param));
            return result.renderSuccess();
        } catch(HttpClientErrorException e) {
            HttpStatus statusCode = e.getStatusCode();
            if (HttpStatus.UNAUTHORIZED.equals(statusCode)) {//401
                //重新登录-刷新Redis中token
                //this.login(); TODO 测试阶段注释
                LOGGER.error("认证失败，Token失效，已经重新刷新token", e);
                return result.renderError(HttpStatus.UNAUTHORIZED.value(), "认证失败，Token失效，已经重新刷新token");
            } else if(HttpStatus.NOT_FOUND.equals(statusCode)) {//404
                LOGGER.error("请求URL地址不存在！", e);
                return result.renderError(HttpStatus.NOT_FOUND.value(), "请求URL地址不存在！");
            }
            throw new RuntimeException("当前路口信号机的优化方案下发失败，接口响应500。", e);
        }  catch(Exception e) {
            throw new RuntimeException("系统繁忙，服务器端内部错误！", e);
        }
    }

    /**
     * @param signalId   信号机编号
     * @param timePlanId 配时方案编号
     * @param minutes    控制方案运行时长（分钟）
     * @return Result 返回结果
     * @description: 强制方案控制--》设置当前路口信号机执行某方案
     * @author yinguijin
     * @date 2019/4/28 14:34
     */
    @Override
    public Result updateQsForcePlan(String signalId, String timePlanId, Integer minutes) {
        Result result = new Result();
        try {
            //获取URL 封装请求头
            HttpRestEntity restEntity = new HttpRestEntity(BaseEnum.VendorTypeEnum.QS.getNick(), "updateManual");
            String urlTemplate = restEntity.getUrl();
            Map<String, String> headers = restEntity.getHeaders();
            headers.put("Authorization","Bearer " + redisUtil.get(RedisKeyConst.KEY_PREFIX + RedisKeyConst.TOKEN).toString());
            String url = MessageFormat.format(urlTemplate, signalId);
            //封装请求参数
            JSONObject param = new JSONObject();
            param.put("runningMode", INTERIM_PLAN_RUNNING_MODE);
            param.put("phasesId", timePlanId);
            param.put("minutes", minutes);
            //发送请求
            HttpRestUtil.doPost(url, headers, JSON.toJSONString(param));
            return result.renderSuccess();
        } catch(HttpClientErrorException e) {
            HttpStatus statusCode = e.getStatusCode();
            if (HttpStatus.UNAUTHORIZED.equals(statusCode)) {//401
                //重新登录-刷新Redis中token
                //this.login(); TODO 测试阶段注释
                LOGGER.error("认证失败，Token失效，已经重新刷新token", e);
                return result.renderError(HttpStatus.UNAUTHORIZED.value(), "认证失败，Token失效，已经重新刷新token");
            } else if(HttpStatus.NOT_FOUND.equals(statusCode)) {//404
                LOGGER.error("请求URL地址不存在！", e);
                return result.renderError(HttpStatus.NOT_FOUND.value(), "请求URL地址不存在！");
            }
            throw new RuntimeException("当前路口信号机的优化方案下发失败，接口响应500。", e);
        }  catch(Exception e) {
            throw new RuntimeException("系统繁忙，服务器端内部错误！", e);
        }
    }


    /**
     * @param param 下发的相位数据
     * @return Result 发送结果
     * @description: 相位方案下发-->下发相位方案信息到路口信号机或信号系统中
     * @author yinguijin
     * @date 2019/4/28 20:24
     */
    @Override
    public Result updateQsPhasePlans(PhasePlanVo param) {
        return null;
    }

    /**
     * @param param 下发的配时数据
     * @return Result 发送结果
     * @description: 配时方案下发-->下发配时方案信息到路口信号机或信号系统中
     * @author yinguijin
     * @date 2019/4/28 20:24
     */
    @Override
    public Result updateQsTimePlans(TimePlanVo param) {
        Result result = new Result();
        try {
            //获取URL 封装请求头
            HttpRestEntity restEntity = new HttpRestEntity(BaseEnum.VendorTypeEnum.QS.getNick(), "updateTimePlans");
            String urlTemplate = restEntity.getUrl();
            Map<String, String> headers = restEntity.getHeaders();
            headers.put("Authorization","Bearer " + redisUtil.get(RedisKeyConst.KEY_PREFIX + RedisKeyConst.TOKEN).toString());
            //定义参数
            JSONObject object = new JSONObject();
            JSONArray array = new JSONArray();
            //封装请求参数
            object.put("phasesId", param.getPhasePlanId());//相位方案编号
            object.put("overwrite", Const.IS_DELETE_YES);//是否覆盖现有控制方案 true
            object.put("offset", param.getOffset());//相位差（秒，可选）

            List<Ring> rings = param.getRings();
            List<Phase> phases = rings.get(0).getPhases();
            for (Phase phase : phases) {
                JSONObject en = new JSONObject();
                en.put("minGreenLength", phase.getMingreenTime());//最小绿灯时间
                en.put("maxGreenLength", phase.getMaxgreenTime());//最大绿灯时间
                array.add(en);
            }
            object.put("phases", array);//相位方案
            //发送请求
            String url = MessageFormat.format(urlTemplate, param.getSignalId());
            HttpRestUtil.doPost(url, headers, JSON.toJSONString(object));
            return result.renderSuccess();
        } catch(HttpClientErrorException e) {
            HttpStatus statusCode = e.getStatusCode();
            if (HttpStatus.UNAUTHORIZED.equals(statusCode)) {//401
                //重新登录-刷新Redis中token
                //this.login(); TODO 测试阶段注释
                LOGGER.error("认证失败，Token失效，已经重新刷新token", e);
                return result.renderError(HttpStatus.UNAUTHORIZED.value(), "认证失败，Token失效，已经重新刷新token");
            } else if(HttpStatus.NOT_FOUND.equals(statusCode)) {//404
                LOGGER.error("请求URL地址不存在！", e);
                return result.renderError(HttpStatus.NOT_FOUND.value(), "请求URL地址不存在！");
            }
            throw new RuntimeException("当前路口信号机的优化方案下发失败，接口响应500。", e);
        }  catch(Exception e) {
            throw new RuntimeException("系统繁忙，服务器端内部错误！", e);
        }
    }

    /**
     * @param param 下发的时段方案数据
     * @return Result 发送结果
     * @description: 时段方案下发-->下发时段方案信息到路口信号机或信号系统中
     * @author yinguijin
     * @date 2019/4/28 20:24
     */
    @Override
    public Result updateQsSectionPlans(SectionPlanVo param) {
        Result result = new Result();
        try {
            //获取URL 封装请求头
            HttpRestEntity restEntity = new HttpRestEntity(BaseEnum.VendorTypeEnum.QS.getNick(), "updateTimePlans");
            String urlTemplate = restEntity.getUrl();
            Map<String, String> headers = restEntity.getHeaders();
            headers.put("Authorization","Bearer " + redisUtil.get(RedisKeyConst.KEY_PREFIX + RedisKeyConst.TOKEN).toString());
            //封装请求参数
            /**
             * TODO 如果是循环修改多个时段方案，返回值那块需要处理
             * 目前同时成功 同时失败
             */
            List<SectionPlan> sectionPlans = param.getSectionPlans();
            for (SectionPlan plan : sectionPlans) {
                String url = MessageFormat.format(urlTemplate, param.getSignalId());
                //请求参数
                JSONObject obj = new JSONObject();
                obj.put("periodsId", plan.getSectionPlanId());//要写入的时段方案编号
                obj.put("overwrite", Const.IS_DELETE_YES);//如果已存在是否覆盖
                JSONArray periods = new JSONArray();
                for (Timeslice timeslice : plan.getTimeslices()) {
                    JSONObject period = new JSONObject();
                    period.put("from", timeslice.getStarttime());// 时段开始时间，00表示分，9表示时，即9:00
                    period.put("phasesId", timeslice.getTimeplanid());//配时方案编号
                    periods.add(period);
                }
                obj.put("periods", periods);//时段设置
                //发送请求
                HttpRestUtil.doPost(url, headers, JSON.toJSONString(obj));
            }
            return result.renderSuccess();
        } catch(HttpClientErrorException e) {
            HttpStatus statusCode = e.getStatusCode();
            if (HttpStatus.UNAUTHORIZED.equals(statusCode)) {//401
                //重新登录-刷新Redis中token
                //this.login(); TODO 测试阶段注释
                LOGGER.error("认证失败，Token失效，已经重新刷新token", e);
                return result.renderError(HttpStatus.UNAUTHORIZED.value(), "认证失败，Token失效，已经重新刷新token");
            } else if(HttpStatus.NOT_FOUND.equals(statusCode)) {//404
                LOGGER.error("请求URL地址不存在！", e);
                return result.renderError(HttpStatus.NOT_FOUND.value(), "请求URL地址不存在！");
            }
            throw new RuntimeException("当前路口信号机的优化方案下发失败，接口响应500。", e);
        }  catch(Exception e) {
            throw new RuntimeException("系统繁忙，服务器端内部错误！", e);
        }
    }

    /**
     * @param param 下发的运行方案数据
     * @return Result 发送结果
     * @description: 运行计划下发-->下发运行计划信息到路口信号机或信号系统中
     * @author yinguijin
     * @date 2019/4/28 20:24
     */
    @Override
    public Result updateQsRunPlan(RunplanVo param) {
        Result result = new Result();
        try {
            //获取URL 封装请求头
            HttpRestEntity restEntity = new HttpRestEntity(BaseEnum.VendorTypeEnum.QS.getNick(), "updateTimePlans");
            String urlTemplate = restEntity.getUrl();
            Map<String, String> headers = restEntity.getHeaders();
            headers.put("Authorization","Bearer " + redisUtil.get(RedisKeyConst.KEY_PREFIX + RedisKeyConst.TOKEN).toString());
            //请求参数
            JSONObject object = new JSONObject();
            JSONArray days = new JSONArray();
            //封装请求参数
            JSONArray weeks = param.getWeeks();
            for (int i = 0; i < weeks.size(); i++) {
                JSONObject day = new JSONObject();
                JSONObject week = weeks.getJSONObject(i);
                day.put("weekDay", week.getInteger("weeknum"));//星期
                day.put("periodsId", week.getString("sectionplanid"));//时段方案ID
            }
            object.put("days", days);
            //发送请求
            String url = MessageFormat.format(urlTemplate, param.getSignalId());
            HttpRestUtil.doPost(url, headers, JSON.toJSONString(object));
            return result.renderSuccess();
        } catch(HttpClientErrorException e) {
            HttpStatus statusCode = e.getStatusCode();
            if (HttpStatus.UNAUTHORIZED.equals(statusCode)) {//401
                //重新登录-刷新Redis中token
                //this.login(); TODO 测试阶段注释
                LOGGER.error("认证失败，Token失效，已经重新刷新token", e);
                return result.renderError(HttpStatus.UNAUTHORIZED.value(), "认证失败，Token失效，已经重新刷新token");
            } else if(HttpStatus.NOT_FOUND.equals(statusCode)) {//404
                LOGGER.error("请求URL地址不存在！", e);
                return result.renderError(HttpStatus.NOT_FOUND.value(), "请求URL地址不存在！");
            }
            throw new RuntimeException("当前路口信号机的优化方案下发失败，接口响应500。", e);
        }  catch(Exception e) {
            throw new RuntimeException("系统繁忙，服务器端内部错误！", e);
        }
    }

}
