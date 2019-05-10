package com.mapabc.signal.service.qs.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mapabc.signal.common.component.BaseSignal;
import com.mapabc.signal.common.component.HttpRestEntity;
import com.mapabc.signal.common.constant.Const;
import com.mapabc.signal.common.constant.RedisKeyConst;
import com.mapabc.signal.common.enums.BaseEnum;
import com.mapabc.signal.common.exception.WarnException;
import com.mapabc.signal.common.util.*;
import com.mapabc.signal.common.util.date.DateUtils;
import com.mapabc.signal.dao.mapper.TBaseCrossCoilFlowMapper;
import com.mapabc.signal.dao.model.*;
import com.mapabc.signal.dao.vo.signal.SignalInfoVo;
import com.mapabc.signal.dao.vo.signal.SignalRunring;
import com.mapabc.signal.dao.vo.signalrunstatus.SignalRunStatusVo;
import com.mapabc.signal.service.TCrossLightsService;
import com.mapabc.signal.service.TelesemeLightsService;
import com.mapabc.signal.service.qs.QsGetSignalMethodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;

/**
 * @description: [青松信号灯厂商接口service实现]
 * @author yinguijin
 * @version 1.0
 * Created on 2019/4/30 10:00
 */
@Service
public class QsGetSignalMethodServiceImpl implements QsGetSignalMethodService {

    private Logger LOGGER = LoggerFactory.getLogger(QsGetSignalMethodServiceImpl.class);

    //相位编号
    private String phasenos[] = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

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

    //平均车长
    @Value("${signalamp.basic.carLength}")
    private BigDecimal carLength;

    //redis
    @Resource
    private RedisUtil redisUtil;
    //路口信号灯组
    @Resource
    private TCrossLightsService tCrossLightsService;
    //信号灯组方向
    @Resource
    private TelesemeLightsService telesemeLightsService;
    //线圈流量Mapper
    @Resource
    private TBaseCrossCoilFlowMapper tBaseCrossCoilFlowMapper;

    /**
     * @description: 青松接口登录
     * @return com.alibaba.fastjson.JSONObject 获取登录后的返回结果
     * @author yinguijin
     * @date 2019/4/25 9:28
     */
    public void login() {
        try {
            String url = rootpath + loginUrl;
            JSONObject param = new JSONObject();
            param.put("username", username);
            param.put("password", password);
            JSONObject object = HttpRestUtil.doPost(url, handlerHeader(), JSON.toJSONString(param), JSONObject.class);
            LOGGER.info("登录青松接口成功、TOKEN过期时间：->" + object.getString("expiresAt"));
            //存储token到Redis中 设置过期时长
            redisUtil.setAndExpire(RedisKeyConst.KEY_PREFIX + RedisKeyConst.TOKEN, object.getString("token"), RedisKeyConst.TOKEN_EXPIRE_TIME);
        } catch(HttpClientErrorException e) {
            HttpStatus statusCode = e.getStatusCode();
            if (HttpStatus.UNAUTHORIZED.equals(statusCode)) {//401
                throw new WarnException("登录认证失败，用户名或密码错误！", e);
            } else if(HttpStatus.INTERNAL_SERVER_ERROR.equals(statusCode)) {//500
                throw new RuntimeException("系统繁忙，服务器端内部错误！", e);
            } else if(HttpStatus.NOT_FOUND.equals(statusCode)) {//404
                throw new WarnException("请求URL地址不存在！", e);
            }
            throw new RuntimeException("系统繁忙，服务器端内部错误！", e);
        }
    }

    /**
     * Discription:[封装请求头]
     * @author: yinguijin
     * @date 2019/4/24 15:23
     */
    private Map<String, String> handlerHeader() {
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type","application/json; charset=UTF-8");
        header.put("Accept", MediaType.APPLICATION_JSON.toString());
        return header;
    }


    /**
     * @description: 获取青松信号机列表
     * @return com.mapabc.signal.common.component.VendorResult<com.mapabc.signal.dao.vo.cross.CrossingVo>
     * @author yinguijin
     * @date 2019/4/30 11:09
     */
    public List<TelesemeList> queryQsCrossing() {
        List<TelesemeList> result = new ArrayList<>();
        TelesemeList vo = null;
        try{
            //发送请求
            HttpRestEntity restEntity = new HttpRestEntity(BaseEnum.VendorTypeEnum.QS.getNick(), "queryCrossing");
            String url = restEntity.getUrl();
            Map<String, String> headers = restEntity.getHeaders();
            headers.put("Authorization","Bearer " + redisUtil.get(RedisKeyConst.KEY_PREFIX + RedisKeyConst.TOKEN).toString());
            JSONObject data = HttpRestUtil.doGet(url, headers, null, JSONObject.class);
            //获取请求返回的数量
            int count = data.getInteger("count");
            // 获取对象中的数组
            JSONArray summaries = data.getJSONArray("summaries");
            // 遍历数组获取每一个对象
            for(int i = 0; i < summaries.size(); i++){
                JSONObject summarie = summaries.getJSONObject(i);
                //封装
                String id = summarie.getString("id");
                String name = summarie.getString("name");
                vo = new TelesemeList();
                vo.setId(BaseEnum.VendorTypeEnum.QS.getNick() + Const.SEPARATOR_UNDER_LINE + id);
                vo.setCrossId(id);
                vo.setCrossName(name);
                vo.setLng(summarie.getString("mapX"));
                vo.setLat(summarie.getString("mapY"));
                vo.setSignalType(BaseEnum.VendorTypeEnum.QS.getNick());
                vo.setSignalName(name);
                vo.setSignalId(id);
                vo.setSignalStatus(BaseEnum.StatusEnum.ON.getCode());
                result.add(vo);
            }
            //返回结果
            return result;
        } catch(HttpClientErrorException e) {
            HttpStatus statusCode = e.getStatusCode();
            if (HttpStatus.UNAUTHORIZED.equals(statusCode)) {//401
                //重新登录-刷新Redis中token
                //this.login(); TODO 测试阶段注释
                throw new WarnException("认证失败，Token失效，已经重新刷新token", e);
            } else if(HttpStatus.NOT_FOUND.equals(statusCode)) {//404
                throw new WarnException("请求URL地址不存在！", e);
            }
            throw new RuntimeException("系统繁忙，服务器端内部错误！", e);
        }  catch(Exception e) {
            throw new RuntimeException("系统繁忙，服务器端内部错误！", e);
        }
    }

    /**
     * @description: 获取青松信号机灯组信息列表
     * @return TCrossLights 信号灯集合
     * @author yinguijin
     * @date 2019/4/30 11:09
     */
    @Override
    public List<TCrossLights> queryQsCrossLights(List<TelesemeList> telesemeLists) {
        List<TCrossLights> result = new ArrayList<>();
        TCrossLights vo = null;
        try {
            HttpRestEntity restEntity = new HttpRestEntity(BaseEnum.VendorTypeEnum.QS.getNick(), "queryLights");
            String urlTemplate = restEntity.getUrl();
            Map<String, String> headers = restEntity.getHeaders();
            headers.put("Authorization","Bearer " + redisUtil.get(RedisKeyConst.KEY_PREFIX + RedisKeyConst.TOKEN).toString());
            for (TelesemeList entity : telesemeLists) {
                //请求地址
                String url = MessageFormat.format(urlTemplate, entity.getSignalId());
                //发送请求
                JSONObject data = HttpRestUtil.doGet(url, headers, null, JSONObject.class);
                //获取请求返回的数量
                int count = data.getInteger("count");
                LOGGER.info("信号机{}下灯组List数量为{}", entity.getSignalId(), count);
                // 获取对象中的数组
                JSONArray lights = data.getJSONArray("lights");
                // 遍历数组获取每一个对象
                for(int i = 0; i < lights.size(); i++) {
                    JSONObject light = lights.getJSONObject(i);
                    //封装
                    vo = new TCrossLights();
                    // --- 路口编号
                    vo.setId(entity.getCrossId());
                    // --- 灯组ID
                    vo.setLightsId(light.getString("id"));
                    // --- 灯组编号
                    vo.setLightGroupNo(light.getString("lightGroupNo"));
                    // --- 放行方向
                    vo.setDirectionCode(light.getString("directionCode"));
                    result.add(vo);
                }
            }
            //返回结果
            return result;
        } catch(HttpClientErrorException e) {
            HttpStatus statusCode = e.getStatusCode();
            if (HttpStatus.UNAUTHORIZED.equals(statusCode)) {//401
                //重新登录-刷新Redis中token
                //this.login(); TODO 测试阶段注释
                throw new WarnException("认证失败，Token失效，已经重新刷新token", e);
            } else if(HttpStatus.NOT_FOUND.equals(statusCode)) {//404
                throw new WarnException("请求URL地址不存在！", e);
            }
            throw new RuntimeException("系统繁忙，服务器端内部错误！", e);
        }  catch(Exception e) {
            throw new RuntimeException("系统繁忙，服务器端内部错误！", e);
        }
    }


    /**
     * @description: 获取青松路口的时段方案（日时段方案）
     * @param telesemeLists  信号灯集合
     * @return  TBasePlanSectime 路口的时段方案数据集合
     * @author yinguijin
     * @date 2019/4/19 14:49
     */
    @Override
    public List<TBaseCrossSectionPlan> queryQsSectionPlan(List<TelesemeList> telesemeLists) {
        List<TBaseCrossSectionPlan> result = new ArrayList<>();
        try {
            HttpRestEntity restEntity = new HttpRestEntity(BaseEnum.VendorTypeEnum.QS.getNick(), "querySectionPlan");
            String urlTemplate = restEntity.getUrl();
            Map<String, String> headers = restEntity.getHeaders();
            headers.put("Authorization","Bearer " + redisUtil.get(RedisKeyConst.KEY_PREFIX + RedisKeyConst.TOKEN).toString());
            for (TelesemeList entity : telesemeLists) {
                //下线跳过
                if (BaseEnum.StatusEnum.OFF.getCode() == entity.getSignalStatus()) {
                    continue;
                }
                //请求地址
                String url = MessageFormat.format(urlTemplate, entity.getSignalId());
                //发送请求
                JSONObject data = HttpRestUtil.doGet(url, headers, null, JSONObject.class);
                //获取请求返回的数量
                int count = data.getInteger("count");
                LOGGER.info("信号机{}下路口的时段方案List数量为{}", entity.getSignalId(), count);
                //时段方案id集合
                JSONArray periodsIds = data.getJSONArray("periodsIds");
                for (Object obj : periodsIds) {
                    String periodsId = obj.toString();
                    List<TBaseCrossSectionPlan> planSectimeList = this.queryQsSectionPlanById(entity, periodsId);
                    result.addAll(planSectimeList);
                }
            }
            return result;
        } catch(HttpClientErrorException e) {
            HttpStatus statusCode = e.getStatusCode();
            if (HttpStatus.UNAUTHORIZED.equals(statusCode)) {//401
                //重新登录-刷新Redis中token
                //this.login(); TODO 测试阶段注释
                throw new WarnException("认证失败，Token失效，已经重新刷新token", e);
            } else if(HttpStatus.NOT_FOUND.equals(statusCode)) {//404
                throw new WarnException("请求URL地址不存在！", e);
            }
            throw new RuntimeException("系统繁忙，服务器端内部错误！", e);
        }  catch(Exception e) {
            throw new RuntimeException("系统繁忙，服务器端内部错误！", e);
        }
    }

    /**
     * @description: 根据时段ID获取青松路口时段方案信息
     * @param teleseme 信号机对象
     * @param periodsId 时段方案ID   必须
     * @return TBasePlanSectime 路口的时段方案数据集合
     * @author yinguijin
     * @date 2019/4/19 14:49
     */
    private List<TBaseCrossSectionPlan> queryQsSectionPlanById(TelesemeList teleseme, String periodsId) {
        List<TBaseCrossSectionPlan> result = new ArrayList<>();
        TBaseCrossSectionPlan sectionPlan = null;
        try {
            //时段方案请求头
            HttpRestEntity restEntity = new HttpRestEntity(BaseEnum.VendorTypeEnum.QS.getNick(), "querySectionPlanById");
            String urlTemplate = restEntity.getUrl();
            Map<String, String> headers = restEntity.getHeaders();
            headers.put("Authorization","Bearer " + redisUtil.get(RedisKeyConst.KEY_PREFIX + RedisKeyConst.TOKEN).toString());
            //请求地址
            String url = MessageFormat.format(urlTemplate, teleseme.getSignalId(), periodsId);
            //发送请求
            JSONObject data = HttpRestUtil.doGet(url, headers, null, JSONObject.class);
            //获取请求返回的数量
            int count = data.getInteger("count");
            JSONArray periods = data.getJSONArray("periods");
            //排序时段
            ListUtil.JSONlistSort(periods);
            //遍历所有时段，获取每个时段方案号
            int timesLiceOrderId = 0;
            for(int j = 0; j < periods.size(); j++) {
                JSONObject period = periods.getJSONObject(j);
                // --- 时段开始时间
                String startTime = period.getString("from");
                // 不够四位补0，除0之外
                if (startTime.length() != 4 && !("0".equals(startTime))) {
                    startTime = "0" + startTime;
                }
                // 0特殊处理
                if ("0".equals(startTime)) {
                    startTime = "0000";
                }
                // 补全后进行格式化
                String startLast = startTime.substring(startTime.length() - 2);
                String startFirst = startTime.substring(0, startTime.length() - 2);
                startTime = startFirst + ":" + startLast;

                StringBuffer secId = new StringBuffer();//时段编号先使用开始时间 + 结束时间
                secId.append(startFirst);
                secId.append(startLast);
                secId.append(Const.SEPARATOR_MINUS);
                String endTime = "";
                // 判断是否取到了最后
                if (j < periods.size() - 1) {
                    // --- 时段结束时间
                    JSONObject periodEnd = periods.getJSONObject(j + 1);
                    endTime = periodEnd.getString("from");
                    // 不够四位补0，除0之外
                    if (endTime.length() != 4) {
                        endTime = "0" + endTime;
                    }
                    String endLast = endTime.substring(endTime.length() - 2);
                    String endFirst = endTime.substring(0, endTime.length() - 2);
                    endTime = endFirst + ":" + endLast;
                    secId.append(endFirst);
                    secId.append(endLast);
                } else {
                    endTime = "24:00";
                }
                // --- 获取配时方案号
                String planNo = period.getString("phasesId");
                // 封装数据
                sectionPlan = new TBaseCrossSectionPlan();
                // --- 路口编号
                sectionPlan.setCrossId(teleseme.getCrossId());
                // 信号机编号
                sectionPlan.setSignalId(teleseme.getId());
                // 信号机类型
                sectionPlan.setSignalType(teleseme.getSignalType());
                // 方案号-配时方案编号
                sectionPlan.setTimePlanId(planNo);
                // 时段方案编号
                sectionPlan.setSectionPlanId(periodsId);
                // 时段方案描述
                StringBuffer sectionPlanDesc = new StringBuffer(teleseme.getCrossName());
                sectionPlanDesc.append(Const.SEPARATOR_UNDER_LINE);
                sectionPlanDesc.append(periodsId);
                sectionPlanDesc.append(Const.SEPARATOR_UNDER_LINE);
                sectionPlanDesc.append(secId);
                sectionPlanDesc.append(Const.SEPARATOR_UNDER_LINE );
                sectionPlanDesc.append(planNo);
                sectionPlan.setSectionPlanDesc(sectionPlanDesc.toString());
                // 时段顺序号
                ++ timesLiceOrderId;
                sectionPlan.setTimesLiceOrderId(timesLiceOrderId);
                // 时段开始时间
                sectionPlan.setStartTime(startTime);
                // 时段结束时间
                sectionPlan.setEndTime(endTime);
                // 时段编号
                sectionPlan.setTimesLiceId(secId.toString());
                sectionPlan.setTimesLiceDesc(getSectimeName(startTime, endTime));
                //创建时间
                sectionPlan.setGmtCreate(new Date());
                result.add(sectionPlan);
            }
            return result;
        } catch(HttpClientErrorException e) {
            HttpStatus statusCode = e.getStatusCode();
            if (HttpStatus.UNAUTHORIZED.equals(statusCode)) {//401
                //重新登录-刷新Redis中token
                //this.login(); TODO 测试阶段注释
                throw new WarnException("认证失败，Token失效，已经重新刷新token", e);
            } else if(HttpStatus.NOT_FOUND.equals(statusCode)) {//404
                throw new WarnException("请求URL地址不存在！", e);
            }
            throw new RuntimeException("系统繁忙，服务器端内部错误！", e);
        }  catch(Exception e) {
            throw new RuntimeException("系统繁忙，服务器端内部错误！", e);
        }
    }


    /**
     * @description: 获取时段名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return java.lang.String 时段名称
     * @author yinguijin
     * @date 2019/5/5 20:12
    */
    private String getSectimeName(String startTime, String endTime){
        String secname = "平峰";
        String st = startTime.split(":")[0];
        String et = endTime.split(":")[0];
        int s = Integer.parseInt(st);
        int e = Integer.parseInt(et);
        if (s>=0 && e<=6){
            secname = "低峰";
        }else if (s>=6 && e<=7){
            secname = "早平峰";
        }else if (s>=7 && e<=9){
            secname = "早高峰";
        }else if (s>=9 && e<=12){
            secname = "平峰";
        }else if (s>=12 && e<=14){
            secname = "次平峰";
        }else if (s>=14 && e<=17){
            secname = "平峰";
        }else if (s>=17 && e<=19){
            secname = "晚高峰";
        }else if (s>=19 && e<=22){
            secname = "次平峰";
        }else if (s>=22 && e<=24){
            secname = "低峰";
        }
        return secname;
    }

    /**
     * @description: 查询青松路口相位方案信息
     * @param telesemeLists 信号机集合
     * @return java.util.List<com.mapabc.signal.dao.model.TBaseCrossPhasePlan>
     * @author yinguijin
     * @date 2019/5/5 15:18
     */
    public List<TBaseCrossPhasePlan> queryQsPhasePlan(List<TelesemeList> telesemeLists) {
        List<TBaseCrossPhasePlan> result = new ArrayList<>();
        try {
            HttpRestEntity restEntity = new HttpRestEntity(BaseEnum.VendorTypeEnum.QS.getNick(), "queryPhasePlan");
            String urlTemplate = restEntity.getUrl();
            Map<String, String> headers = restEntity.getHeaders();
            headers.put("Authorization","Bearer " + redisUtil.get(RedisKeyConst.KEY_PREFIX + RedisKeyConst.TOKEN).toString());
            for (TelesemeList entity : telesemeLists) {
                //下线跳过
                if (BaseEnum.StatusEnum.OFF.getCode() == entity.getSignalStatus()) {
                    continue;
                }
                //请求地址
                String url = MessageFormat.format(urlTemplate, entity.getSignalId());
                //发送请求
                JSONObject data = HttpRestUtil.doGet(url, headers, null, JSONObject.class);
                //获取请求返回的数量
                int count = data.getInteger("count");
                LOGGER.info("信号机{}下路口的相位方案List数量为{}", entity.getSignalId(), count);
                //相位方案id集合
                JSONArray phasesIds = data.getJSONArray("phasesIds");
                for (Object obj : phasesIds) {
                    String phasesId = obj.toString();
                    List<TBaseCrossPhasePlan> phasePlans = this.queryQsPhasePlanById(entity, phasesId);
                    result.addAll(phasePlans);
                }
            }
            return result;
        } catch(HttpClientErrorException e) {
            HttpStatus statusCode = e.getStatusCode();
            if (HttpStatus.UNAUTHORIZED.equals(statusCode)) {//401
                //重新登录-刷新Redis中token
                //this.login(); TODO 测试阶段注释
                throw new WarnException("认证失败，Token失效，已经重新刷新token", e);
            } else if(HttpStatus.NOT_FOUND.equals(statusCode)) {//404
                throw new WarnException("请求URL地址不存在！", e);
            }
            throw new RuntimeException("系统繁忙，服务器端内部错误！", e);
        }  catch(Exception e) {
            throw new RuntimeException("系统繁忙，服务器端内部错误！", e);
        }
    }

    /**
     * @description: 根据相位ID查询路口相位方案信息
     * @param teleseme 信号机信息
     * @param phaseId 相位方案ID
     * @return java.util.List<com.mapabc.signal.dao.model.TBaseCrossPhasePlan>
     * @author yinguijin
     * @date 2019/5/5 15:18
    */
    private List<TBaseCrossPhasePlan> queryQsPhasePlanById(TelesemeList teleseme, String phaseId) {
        List<TBaseCrossPhasePlan> result = new ArrayList<>();
        TBaseCrossPhasePlan crossPhase = null;
        try {
            //时段方案请求头
            HttpRestEntity restEntity = new HttpRestEntity(BaseEnum.VendorTypeEnum.QS.getNick(), "queryPhasePlanById");
            String urlTemplate = restEntity.getUrl();
            Map<String, String> headers = restEntity.getHeaders();
            headers.put("Authorization","Bearer " + redisUtil.get(RedisKeyConst.KEY_PREFIX + RedisKeyConst.TOKEN).toString());
            //请求地址
            String url = MessageFormat.format(urlTemplate, teleseme.getSignalId(), phaseId);
            //发送请求
            JSONObject data = HttpRestUtil.doGet(url, headers, null, JSONObject.class);
            //获取请求返回的数量
            int count = data.getInteger("count");
            // --- 相位差
            Integer offset = data.getInteger("offset");
            // 所有方案
            JSONArray phases = data.getJSONArray("phases");

            // 遍历所有相位
            for(int i = 0; i < phases.size(); i++) {
                // --- 相位编号
                String phaseNo = phasenos[i];
                // 各相位
                JSONObject phase = phases.getJSONObject(i);
                // 最小绿灯时间
                Integer minGreenLength = phase.getInteger("minGreenLength");
                // 最大绿灯时间
                Integer maxGreenLength = phase.getInteger("maxGreenLength");
                // 各相位的灯组
                JSONArray mainLightGroups = phase.getJSONArray("mainLightGroups");
                // 数组去重
                Set set = new HashSet();
                //遍历数组并存入集合,如果元素已存在则不会重复存入
                for (int j = 0; j < mainLightGroups.size(); j++) {
                    set.add(mainLightGroups.getString(j));
                }
                Object obj[] = set.toArray();
                // 遍历当前相位对应的灯组号
                for (int j = 0; j < obj.length; j++) {
                    // --- 灯组号
                    String phaseLigntGroupNo = (String) obj[j];
                    // 根据灯组号和路口编号查询灯组
                    TCrossLights query = new TCrossLights();
                    query.setId(teleseme.getCrossId());
                    query.setLightGroupNo(phaseLigntGroupNo);
                    List<TCrossLights> crossLightList = tCrossLightsService.select(query);
                    for (TCrossLights crossLight : crossLightList) {
                        // 灯组的放行方向编号
                        String directioncode = crossLight.getDirectionCode();
                        // 根据放行方向编号到字典中查询其它数据
                        TelesemeLights lightsQuery = new TelesemeLights();
                        lightsQuery.setCode(directioncode);
                        List<TelesemeLights> telesemeLights = telesemeLightsService.select(lightsQuery);
                        if (ListUtil.isEmpty(telesemeLights)) {
                            continue;
                        }
                        TelesemeLights telesemeLight = telesemeLights.get(0);
                        crossPhase = new TBaseCrossPhasePlan();
                        // 路口编号
                        crossPhase.setCrossId(teleseme.getCrossId());
                        // 信号机编号
                        crossPhase.setSignalId(teleseme.getId());
                        // 信号机类型
                        crossPhase.setSignalType(teleseme.getSignalType());
                        // 方位方向
                        crossPhase.setDirection(telesemeLight.getDrection());
                        // 机动车/行人
                        crossPhase.setDirobjType(telesemeLight.getDirobjtype());
                        // 进出方向
                        crossPhase.setInoutType(telesemeLight.getInouttype());
                        // 转向方向
                        crossPhase.setTurnType(telesemeLight.getTurn());
                        // 最小绿灯时间
                        crossPhase.setMinGreenTime(minGreenLength);
                        // 最大绿灯时间
                        crossPhase.setMaxGreenTime(maxGreenLength);
                        // 相位方案编号
                        crossPhase.setPhasePlanId(phaseId);
                        // 相位方案描述
                        StringBuffer pahsePlanDesc = new StringBuffer(teleseme.getCrossName());
                        pahsePlanDesc.append(Const.SEPARATOR_UNDER_LINE);
                        pahsePlanDesc.append(phaseId);
                        pahsePlanDesc.append(Const.SEPARATOR_UNDER_LINE);
                        pahsePlanDesc.append(phaseNo);
                        crossPhase.setPahsePlanDesc(pahsePlanDesc.toString());
                        // 相位编号
                        crossPhase.setPhaseId(phaseNo);
                        // 创建时间
                        crossPhase.setGmtCreate(new Date());
                        result.add(crossPhase);
                    }
                }
            }
            return result;
        } catch(HttpClientErrorException e) {
            HttpStatus statusCode = e.getStatusCode();
            if (HttpStatus.UNAUTHORIZED.equals(statusCode)) {//401
                //重新登录-刷新Redis中token
                //this.login(); TODO 测试阶段注释
                throw new WarnException("认证失败，Token失效，已经重新刷新token", e);
            } else if(HttpStatus.NOT_FOUND.equals(statusCode)) {//404
                throw new WarnException("请求URL地址不存在！", e);
            }
            throw new RuntimeException("系统繁忙，服务器端内部错误！", e);
        }  catch(Exception e) {
            throw new RuntimeException("系统繁忙，服务器端内部错误！", e);
        }
    }


    /**
     * @description: 路口的运行计划获取
     * @param telesemeLists 信号机集合
     * @return RunplanVo 路口的运行计划数据集合
     * @author yinguijin
     * @date 2019/4/19 14:49
     */
    @Override
    public List<TBaseCrossRunPlan> queryQsRunPlan(List<TelesemeList> telesemeLists) {
        List<TBaseCrossRunPlan> result = new ArrayList<>();
        TBaseCrossRunPlan vo = null;
        try {
            HttpRestEntity restEntity = new HttpRestEntity(BaseEnum.VendorTypeEnum.QS.getNick(), "queryRunPlan");
            //运行方案请求头
            String urlTemplate = restEntity.getUrl();
            Map<String, String> headers = restEntity.getHeaders();
            headers.put("Authorization","Bearer " + redisUtil.get(RedisKeyConst.KEY_PREFIX + RedisKeyConst.TOKEN).toString());
            for (TelesemeList entity : telesemeLists) {
                //下线跳过
                if (BaseEnum.StatusEnum.OFF.getCode() == entity.getSignalStatus()) {
                    continue;
                }
                //请求地址
                String url = MessageFormat.format(urlTemplate, entity.getSignalId());
                //发送请求
                JSONObject data = HttpRestUtil.doGet(url, headers, null, JSONObject.class);
                //获取请求返回的数量
                int count = data.getInteger("count");
                LOGGER.info("信号机{}运行计划List数量为{}", entity.getSignalId(), count);
                // 获取对象中的数组
                JSONArray days = data.getJSONArray("days");
                // 遍历数组获取每一个对象
                for(int i = 0; i < days.size(); i++) {
                    JSONObject day = days.getJSONObject(i);
                    // ---weekDay 星期
                    // 1(周天),2(周一),3(周二),4(周三),5(周四),6(周五),7(周六)
                    // 1(周一),2(周二),3(周三),4(周四),5(周五),6(周六),7(周天)
                    String weekDay = day.getString("weekDay");
                    if(StringUtils.isEmpty(weekDay)) {
                        continue;
                    }
                    if ("1".equals(weekDay)) {
                        weekDay = "7";
                    } else if ("2".equals(weekDay)) {
                        weekDay = "1";
                    } else if ("3".equals(weekDay)) {
                        weekDay = "2";
                    } else if ("4".equals(weekDay)) {
                        weekDay = "3";
                    } else if ("5".equals(weekDay)) {
                        weekDay = "4";
                    } else if ("6".equals(weekDay)) {
                        weekDay = "5";
                    } else if ("7".equals(weekDay)) {
                        weekDay = "6";
                    }
                    //QS时段方案ID
                    String periodId = day.getString("periodsId");
                    //封装结果
                    vo = new TBaseCrossRunPlan();
                    vo.setCrossId(entity.getCrossId());//路口编号
                    vo.setWeekNum(Integer.valueOf(weekDay));//星期
                    vo.setSignalId(entity.getId());//信号机编号
                    vo.setSignalType(entity.getSignalType());//信号机类型
                    vo.setSectionPlanId(periodId);//时段方案ID
                    vo.setGmtCreate(new Date());//创建时间
                    //判断是否特殊日期
                    String specialYear = day.getString("year");
                    if (StringUtils.isNotEmpty(specialYear)) {
                        String specialMonth = day.getString("month");
                        String specialDay = day.getString("day");
                        String specialDate = specialYear + Const.SEPARATOR_MINUS + specialMonth + Const.SEPARATOR_MINUS + specialDay;
                        vo.setDateStr(specialDate);
                    }
                    result.add(vo);
                }
            }
            //返回结果
            return result;
        } catch(HttpClientErrorException e) {
            HttpStatus statusCode = e.getStatusCode();
            if (HttpStatus.UNAUTHORIZED.equals(statusCode)) {//401
                //重新登录-刷新Redis中token
                //this.login(); TODO 测试阶段注释
                throw new WarnException("认证失败，Token失效，已经重新刷新token", e);
            } else if(HttpStatus.NOT_FOUND.equals(statusCode)) {//404
                throw new WarnException("请求URL地址不存在！", e);
            }
            throw new RuntimeException("系统繁忙，服务器端内部错误！", e);
        }  catch(Exception e) {
            throw new RuntimeException("系统繁忙，服务器端内部错误！", e);
        }
    }



    /**
     * @description: 获取相位配时方案信息
     * @param telesemeLists 信号灯列表
     * @return java.util.List<com.mapabc.signal.dao.model.TBaseCrossTimePlan> 相位配时方案集合
     * @author yinguijin
     * @date 2019/5/6 15:08
    */
    @Override
    public List<TBaseCrossTimePlan> queryQsTimePlan(List<TelesemeList> telesemeLists) {
        List<TBaseCrossTimePlan> result = new ArrayList<>();
        TBaseCrossTimePlan timePlan;
        try {
            HttpRestEntity restEntity = new HttpRestEntity(BaseEnum.VendorTypeEnum.QS.getNick(), "queryPhasePlan");
            String urlTemplate = restEntity.getUrl();
            Map<String, String> headers = restEntity.getHeaders();
            headers.put("Authorization","Bearer " + redisUtil.get(RedisKeyConst.KEY_PREFIX + RedisKeyConst.TOKEN).toString());

            //相位方案请求头
            HttpRestEntity phaseRestEntity = new HttpRestEntity(BaseEnum.VendorTypeEnum.QS.getNick(), "queryPhasePlanById");
            String phaseUrlTemplate = phaseRestEntity.getUrl();
            Map<String, String> phaseHeaders = phaseRestEntity.getHeaders();
            phaseHeaders.put("Authorization","Bearer " + redisUtil.get(RedisKeyConst.KEY_PREFIX + RedisKeyConst.TOKEN).toString());
            //循环获取相位配时方案信息
            for (TelesemeList entity : telesemeLists) {
                //下线跳过
                if (BaseEnum.StatusEnum.OFF.getCode() == entity.getSignalStatus()) {
                    continue;
                }
                //请求地址
                String url = MessageFormat.format(urlTemplate, entity.getSignalId());
                //发送请求
                JSONObject data = HttpRestUtil.doGet(url, headers, null, JSONObject.class);
                //获取请求返回的数量
                int count = data.getInteger("count");
                LOGGER.info("信号机{}下路口的相位方案List数量为{}", entity.getSignalId(), count);
                //相位方案id集合
                JSONArray phasesIds = data.getJSONArray("phasesIds");
                for (Object obj : phasesIds) {
                    String phasesId = obj.toString();
                    //请求地址
                    String periodUrl = MessageFormat.format(phaseUrlTemplate, entity.getSignalId(), phasesId);
                    //发送请求
                    JSONObject phaseData = HttpRestUtil.doGet(periodUrl, phaseHeaders, null, JSONObject.class);
                    // --- 相位差
                    Integer offset = phaseData.getInteger("offset");
                    // --- 信号周期
                    Integer cycleLen = phaseData.getInteger("length");
                    // 所有方案
                    JSONArray phases = phaseData.getJSONArray("phases");
                    // 相位顺序序号
                    int phaseOrderId = 0;
                    // 遍历所有相位
                    for(int i = 0; i < phases.size(); i++) {
                        // --- 相位顺序序号
                        ++ phaseOrderId;
                        // 各相位
                        JSONObject phase = phases.getJSONObject(i);
                        // 相位时间
                        Integer phaseTime = phase.getInteger("length");
                        // 最小绿灯时间
                        Integer minGreenLength = phase.getInteger("minGreenLength");
                        // 最大绿灯时间
                        Integer maxGreenLength = phase.getInteger("maxGreenLength");
                        // 当前相位所有步
                        JSONArray steps = phase.getJSONArray("steps");
                        // 步编号
                        Integer stepNo = 0;
                        for(int j = 0; j < steps.size(); j++) {
                            JSONObject step = steps.getJSONObject(j);
                            // --- 当前步长
                            Integer stepLength = step.getInteger("length");
                            // --- 当前步号
                            ++ stepNo;

                            // --- 相位编号
                            String phaseNo = phasenos[i];
                            //组装返回值
                            timePlan = new TBaseCrossTimePlan();
                            // --- 路口编号
                            timePlan.setCrossId(entity.getCrossId());
                            // --- 信号机编号
                            timePlan.setSignalId(entity.getId());
                            // --- 信号机类型
                            timePlan.setSignalType(entity.getSignalType());
                            // --- 配时方案编号
                            timePlan.setTimePlanId(phasesId);
                            // --- 配时方案描述
                            StringBuffer timePlanDesc = new StringBuffer(entity.getCrossName());
                            timePlanDesc.append(Const.SEPARATOR_UNDER_LINE);
                            timePlanDesc.append(phasesId);
                            timePlanDesc.append(Const.SEPARATOR_UNDER_LINE );
                            timePlanDesc.append(phaseNo);
                            timePlan.setTimePlanDesc(timePlanDesc.toString());
                            // --- 相位方案编号
                            timePlan.setPhasePlanId(phasesId);
                            // --- 周期长度
                            timePlan.setCycleLen(cycleLen);
                            // --- 协调相位编号
                            /**
                             * TODO 协调相位当前设置为0
                             * 用于绿波控制的，没有协调相位设成0.
                             * 如果是绿波路口，但没有制定协调相位，那就是第一个相位，把第一个相位编号写进去就行
                             */
                            timePlan.setCoordPhaseId("0");
                            // --- 相位差
                            if (null == offset) {
                                offset = 0;
                            }
                            timePlan.setOffset(offset);
                            // 是否双环： 1 是 0 否
                            // TODO 是否双环默认 0 否
                            timePlan.setIsDoubleRing(0);
                            // --- 环编号
                            // TODO 单环1；双环就顺序写1,2
                            timePlan.setRingNo("1");
                            // --- 相位编号
                            timePlan.setPhaseId(phaseNo);
                            // --- 相位顺序序号
                            timePlan.setPhaseOrderId(phaseOrderId);
                            // --- 相位时长
                            timePlan.setPhaseTime(phaseTime);
                            // --- 最小绿灯时间
                            timePlan.setMinGreenTime(minGreenLength);
                            // --- 最大绿灯时间
                            timePlan.setMaxGreenTime(maxGreenLength);
                            // --- 当前步号
                            timePlan.setStepNo(stepNo);
                            // --- 步类型
                            timePlan.setStepType(stepNo);
                            // --- 步长
                            timePlan.setStepLen(stepLength);
                            // --- 创建时间
                            timePlan.setGmtCreate(new Date());
                            result.add(timePlan);
                        }
                    }
                }
            }
            return result;
        } catch(HttpClientErrorException e) {
            HttpStatus statusCode = e.getStatusCode();
            if (HttpStatus.UNAUTHORIZED.equals(statusCode)) {//401
                //重新登录-刷新Redis中token
                //this.login(); TODO 测试阶段注释
                throw new WarnException("认证失败，Token失效，已经重新刷新token", e);
            } else if(HttpStatus.NOT_FOUND.equals(statusCode)) {//404
                throw new WarnException("请求URL地址不存在！", e);
            }
            throw new RuntimeException("系统繁忙，服务器端内部错误！", e);
        }  catch(Exception e) {
            throw new RuntimeException("系统繁忙，服务器端内部错误！", e);
        }
    }


/**
 * =======================================================================================
 * ==========================实时动态数据接口================================================
 * =======================================================================================
 */

    /**
     * @description: 获取路口信号机的实时状态数据
     * @param baseSignals 信号机列表
     * @return SignalInfoVo 信号机状态
     * @author yinguijin
     * @date 2019/5/7 15:50
     */
    @Override
    public List<SignalRunStatusVo> queryQsRunStatus(List<BaseSignal> baseSignals) {
        List<SignalRunStatusVo> result = new ArrayList<>();
        SignalRunStatusVo vo;
        try {
            HttpRestEntity restEntity = new HttpRestEntity(BaseEnum.VendorTypeEnum.QS.getNick(), "queryCrossingBySignal");
            //运行方案请求头
            String urlTemplate = restEntity.getUrl();
            Map<String, String> headers = restEntity.getHeaders();
            headers.put("Authorization", "Bearer " + redisUtil.get(RedisKeyConst.KEY_PREFIX + RedisKeyConst.TOKEN).toString());
            for (BaseSignal entity : baseSignals) {
                //请求地址
                String url = MessageFormat.format(urlTemplate, entity.getSignalId());
                //发送请求
                JSONObject data = HttpRestUtil.doGet(url, headers, null, JSONObject.class);
                int commStatus = 2;
                if (null != data && null != data.getInteger("onLine") && 1 == data.getInteger("onLine")) {
                    commStatus = 1;
                }
                //封装对象
                vo = new SignalRunStatusVo();
                vo.setCommStatus(commStatus);
                vo.setSignalId(entity.getSignalId());
                vo.setSignalType(entity.getSignalType());
                result.add(vo);
            }
            return result;
        } catch(HttpClientErrorException e){
            HttpStatus statusCode = e.getStatusCode();
            if (HttpStatus.UNAUTHORIZED.equals(statusCode)) {//401
                //重新登录-刷新Redis中token
                //this.login(); TODO 测试阶段注释
                throw new WarnException("认证失败，Token失效，已经重新刷新token", e);
            } else if (HttpStatus.NOT_FOUND.equals(statusCode)) {//404
                throw new WarnException("请求URL地址不存在！", e);
            }
            throw new RuntimeException("系统繁忙，服务器端内部错误！", e);
        }  catch(Exception e){
            throw new RuntimeException("系统繁忙，服务器端内部错误！", e);
        }
    }

    /**
     * @description: 获取路口信号机的实时灯态数据
     * @param baseSignals 信号机列表
     * @return SignalInfoVo 信号机状态
     * @author yinguijin
     * @date 2019/5/7 15:50
     */
    @Override
    public List<SignalInfoVo> queryQsSignalInfo(List<BaseSignal> baseSignals) {
        List<SignalInfoVo> result = new ArrayList<>();
        SignalInfoVo infoVo;
        try {
            HttpRestEntity restEntity = new HttpRestEntity(BaseEnum.VendorTypeEnum.QS.getNick(), "querySignalInfo");
            //运行方案请求头
            String urlTemplate = restEntity.getUrl();
            Map<String, String> headers = restEntity.getHeaders();
            headers.put("Authorization", "Bearer " + redisUtil.get(RedisKeyConst.KEY_PREFIX + RedisKeyConst.TOKEN).toString());
            for (BaseSignal entity : baseSignals) {
                //请求地址
                String url = MessageFormat.format(urlTemplate, entity.getSignalId());
                //发送请求
                JSONObject data = HttpRestUtil.doGet(url, headers, null, JSONObject.class);
                // 方案编号
                String phaseId = data.getString("phasesId");
                // 运行模式
                String runningMode = data.getString("runningMode");
                // 当前步信息
                JSONObject currentStep = data.getJSONObject("currentStep");
                // 当前步号
                Integer stepIndex = currentStep.getInteger("index");
                // 当前相位信息
                JSONObject currentPhase = data.getJSONObject("currentPhase");
                // 相位序号
                Integer phaseIndex = currentPhase.getInteger("index");
                // 倒计时
                Integer countdown = currentStep.getInteger("countdown");
                // 当前步长
                Integer length = currentStep.getInteger("length");
                //封装对象
                infoVo = new SignalInfoVo();
                infoVo.setSignalId(entity.getSignalId());//信号机ID
                infoVo.setSignalType(entity.getSignalType());//信号机类型
                infoVo.setPhasePlanId(phaseId);//相位方案编号
                infoVo.setRunMode(runningMode);//运行模式
                SignalRunring runring = new SignalRunring();
                runring.setStepNo(stepIndex);
                runring.setStepLen(length);
                runring.setPhaseId(phaseId);
                runring.setPhaseLeft(countdown);
                List<SignalRunring> runrings = new ArrayList<>();
                runrings.add(runring);
                infoVo.setRunrings(runrings);
                result.add(infoVo);
            }
            return result;
        } catch(HttpClientErrorException e){
            HttpStatus statusCode = e.getStatusCode();
            if (HttpStatus.UNAUTHORIZED.equals(statusCode)) {//401
                //重新登录-刷新Redis中token
                //this.login(); TODO 测试阶段注释
                throw new WarnException("认证失败，Token失效，已经重新刷新token", e);
            } else if (HttpStatus.NOT_FOUND.equals(statusCode)) {//404
                throw new WarnException("请求URL地址不存在！", e);
            }
            throw new RuntimeException("系统繁忙，服务器端内部错误！", e);
        }  catch(Exception e){
            throw new RuntimeException("系统繁忙，服务器端内部错误！", e);
        }
    }

    /**
     * @description: 获取路口信号机的实时流量数据
     * @param telesemeLists 信号机列表
     * @return SignalVolumeVo 信号机的实时流量
     * @author yinguijin
     * @date 2019/4/22 17:09
     */
    @Override
    public List<TBaseCrossCoilFlow> queryQsSignalVolume(List<TelesemeList> telesemeLists) {
        List<TBaseCrossCoilFlow> result = new ArrayList<>();
        TBaseCrossCoilFlow coilFlow;
        try {
            HttpRestEntity restEntity = new HttpRestEntity(BaseEnum.VendorTypeEnum.QS.getNick(), "querySignalVolume");
            //运行方案请求头
            String urlTemplate = restEntity.getUrl();
            Map<String, String> headers = restEntity.getHeaders();
            headers.put("Authorization", "Bearer " + redisUtil.get(RedisKeyConst.KEY_PREFIX + RedisKeyConst.TOKEN).toString());
            for (TelesemeList entity : telesemeLists) {
                //下线跳过
                if (BaseEnum.StatusEnum.OFF.getCode() == entity.getSignalStatus()) {
                    continue;
                }
                String url = MessageFormat.format(urlTemplate, entity.getSignalId());
                //发送请求
                JSONObject data = null;
                try{
                    data = HttpRestUtil.doGet(url, headers, null, JSONObject.class);
                } catch (Exception e) {
                    LOGGER.error("信号机编号: " + entity.getSignalId() + "；调用青松接口存储车流量信息异常！", e);
                }
                if (null == data) {
                    continue;
                }
                JSONArray flowArray = data.getJSONArray("loopFlowStrings");
                if(ListUtil.isNotEmpty(flowArray)) {
                    for(int i = 0; i < flowArray.size(); i++){

                        //格式化日期
                        String date = data.getString("from").replace("T", " ");
                        if (checkValiDate(date,20)) {//判断是否时间在20分钟左右
                            continue;
                        }
                        // 8=23|1|-1|0.14|-1|-1|-1|-1|-1|0
                        String flow = flowArray.get(i).toString();
                        // 拆出线圈编号及线圈对应的流量数据
                        String flows[] = flow.split("=");
                        // 其它流量相关数据
                        String otherData[] = flows[1].split("\\|");
                        //封装对象
                        coilFlow = new TBaseCrossCoilFlow();
                        coilFlow.setSignalId(entity.getId());
                        coilFlow.setSignalType(entity.getSignalType());
                        // 日期时间
                        coilFlow.setDateTime(date);
                        // 时间粒度，单位：秒
                        coilFlow.setGranula(data.getInteger("length"));
                        // 线圈编号-检测器编号
                        coilFlow.setDetId(flows[0]);
                        // 总流量
                        coilFlow.setVolume(MathUtils.getInteger(otherData[0]));
                        // 车速
                        coilFlow.setSpeed(MathUtils.getBigDecimal(otherData[3],2));
                        // 平均车长
                        coilFlow.setCarLength(carLength);
                        // 时间占有率
                        coilFlow.setOccupancy(MathUtils.getBigDecimal(otherData[1],2));
                        // 车头时距
                        coilFlow.setHeadTime(MathUtils.getBigDecimal(otherData[2], 2));
                        // 大型车流量
                        coilFlow.setBigVolume(MathUtils.getInteger(otherData[7]));
                        // 中型车流量
                        coilFlow.setMiddleVolume(MathUtils.getInteger(otherData[6]));
                        // 小型车流量
                        coilFlow.setSmallVolume(MathUtils.getInteger(otherData[5]));
                        // 未知车型流量
                        coilFlow.setMiniVolume(MathUtils.getInteger(otherData[4]));
                        // 排队长度
                        coilFlow.setQueueLength(MathUtils.getBigDecimal(otherData[8], 2));
                        // 置信度
                        coilFlow.setCredible(MathUtils.getBigDecimal(otherData[9], 2));
                        //判断重复数据
                        List<TBaseCrossCoilFlow> flowsBean = tBaseCrossCoilFlowMapper.select(coilFlow);
                        if (ListUtil.isNotEmpty(flowsBean)) {
                            continue;
                        }
                        // 创建时间
                        coilFlow.setCreateTime(new Date());
                        result.add(coilFlow);

                    }
                }
            }
            return result;
        } catch(HttpClientErrorException e){
            HttpStatus statusCode = e.getStatusCode();
            if (HttpStatus.UNAUTHORIZED.equals(statusCode)) {//401
                //重新登录-刷新Redis中token
                //this.login(); TODO 测试阶段注释
                throw new WarnException("认证失败，Token失效，已经重新刷新token", e);
            } else if (HttpStatus.NOT_FOUND.equals(statusCode)) {//404
                throw new WarnException("请求URL地址不存在！", e);
            }
            throw new RuntimeException("系统繁忙，服务器端内部错误！", e);
        }  catch(Exception e){
            throw new RuntimeException("系统繁忙，服务器端内部错误！", e);
        }
    }

    /**
     * @description: 判断时间是否在当前时间点前后范围中
     * @param date 时间
     * @param minues 前后多少分钟
     * @return 在范围中true
     * @author yinguijin
     * @date 2019/5/8 10:26
    */
    private boolean checkValiDate(String date, int minues) {
        boolean isillvalue= false;
        try {
            //获取当前时间毫秒数
            long nowtimes = System.currentTimeMillis();
            //当前时间 + minues 毫秒数
            long maxtimes = nowtimes + (minues * 60*1000);
            //当前时间 - minues 毫秒数
            long mintimes = nowtimes - (minues * 60*1000);

            long intimes = DateUtils.stringToDate(date).getTime();
            //判断是否在时间范围内
            if (intimes < mintimes || intimes > maxtimes) {
                isillvalue = true;
            }
        } catch (Exception e) {
            LOGGER.error("判断流量数据是否时间在" + minues + "分钟左右异常", e);
        }
        return isillvalue;
    }


}
