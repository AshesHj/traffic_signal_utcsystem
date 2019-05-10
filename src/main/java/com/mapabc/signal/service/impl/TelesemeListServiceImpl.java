package com.mapabc.signal.service.impl;

import com.mapabc.signal.common.component.BaseSignal;
import com.mapabc.signal.common.component.ParamEntity;
import com.mapabc.signal.common.component.VendorResult;
import com.mapabc.signal.common.constant.Const;
import com.mapabc.signal.common.constant.RedisKeyConst;
import com.mapabc.signal.common.enums.BaseEnum;
import com.mapabc.signal.common.util.ListUtil;
import com.mapabc.signal.common.util.RedisUtil;
import com.mapabc.signal.common.util.StringUtils;
import com.mapabc.signal.dao.mapper.TelesemeListMapper;
import com.mapabc.signal.dao.model.TelesemeList;
import com.mapabc.signal.dao.vo.cross.CrossingVo;
import com.mapabc.signal.dao.vo.signalrunstatus.SignalRunStatusVo;
import com.mapabc.signal.service.TelesemeListService;
import com.mapabc.signal.service.qs.QsGetSignalMethodService;
import com.mapabc.signal.service.source.CrossingService;
import com.mapabc.signal.service.source.SignalRunService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
* @Description: [信号机列表service实现]</p>
* @author yinguijin
* @version 1.0
* Created on 2019年04月30日
*/
@Service
public class TelesemeListServiceImpl extends BaseServiceImpl<TelesemeList, TelesemeListMapper> implements TelesemeListService {

    @Value("${signalamp.vendor.qs.switch}")
    private Boolean adSwitch;

    //青松接口
    @Resource
    private QsGetSignalMethodService qsGetSignalMethodService;

    //标准接口
    @Resource
    private CrossingService crossingService;

    //标准接口
    @Resource
    private SignalRunService signalRunService;

    @Resource
    private RedisUtil redisUtil;


    /**
     * @param param
     * @return CrossingVo 路口信息结果集合
     * @description: 获取信号控制系统所有控制路口基本信息
     * @author yinguijin
     * @date 2019/4/19 14:48
     */
    @Override
    public void initCrossing(ParamEntity<BaseSignal> param) {
        List<TelesemeList> telesemeLists = new ArrayList<>();
        if (BaseEnum.VendorTypeEnum.QS.getNick().equalsIgnoreCase(param.getSourceType()) && adSwitch) {
            //青松接口
            telesemeLists = qsGetSignalMethodService.queryQsCrossing();
        } else {
            //标准接口
            VendorResult<CrossingVo> result = crossingService.queryCrossing(param);
            if (null == result) {
                return;
            }
            List<CrossingVo> dataContent = result.getDataContent();
            //封装对象
            TelesemeList teleseme;
            for (CrossingVo vo : dataContent) {
                teleseme = new TelesemeList();
                // 主键ID
                teleseme.setId(param.getSourceType() + Const.SEPARATOR_UNDER_LINE + vo.getSignalId());
                // 路口
                teleseme.setCrossId(vo.getSignalId());
                teleseme.setCrossName(vo.getCrossName());
                teleseme.setCrossType(vo.getCrossType());
                // 信号机状态 默认1
                teleseme.setSignalStatus(BaseEnum.StatusEnum.ON.getCode());
                // 信号机基础信息
                teleseme.setSignalId(vo.getSignalId());
                teleseme.setSignalType(vo.getSignalType());
                teleseme.setSignalName(vo.getCrossName());
                teleseme.setLng(vo.getLongitude().toString());
                teleseme.setLat(vo.getLatitude().toString());
                telesemeLists.add(teleseme);
            }
        }
        //存储信号机信息
        if (ListUtil.isEmpty(telesemeLists)) {
            return;
        }
        insertList(telesemeLists);
    }

    /**
     * @param param 信号机ID和信号机类型（实时信号机状态）
     * @return 信号机信息
     * @description: 根据信号机ID和信号机类型查询信号机信息
     * @author yinguijin
     * @date 2019/5/7 11:51
     */
    @Override
    public List<TelesemeList> selectByTeleseme(BaseSignal param) {
        List<TelesemeList> list = myBaseMapper.selectByTeleseme(param);
        for (TelesemeList entity : list) {
            String status = redisUtil.getHash(RedisKeyConst.KEY_PREFIX + RedisKeyConst.TELESEME_STATUS, entity.getId());
            if (StringUtils.isNotEmpty(status)) {
                entity.setSignalStatus(Integer.valueOf(status));
            }
        }
        return list;
    }

    /**
     * @param param 信号机ID和信号机类型（只返回在线的信号机）
     * @return 信号机信息
     * @description: 根据信号机ID和信号机类型查询信号机信息
     * @author yinguijin
     * @date 2019/5/7 11:51
     */
    @Override
    public List<BaseSignal> selectByBaseSignal(BaseSignal param) {
        List<BaseSignal> result = new ArrayList<>();
        List<BaseSignal> list = myBaseMapper.selectByBaseSignal(param);
        for (BaseSignal entity : list) {
            String field = entity.getSignalType() + Const.SEPARATOR_UNDER_LINE + entity.getSignalId();
            String status = redisUtil.getHash(RedisKeyConst.KEY_PREFIX + RedisKeyConst.TELESEME_STATUS, field);
            if (StringUtils.isNotEmpty(status) && Integer.valueOf(status) == BaseEnum.StatusEnum.ON.getCode()) {
                result.add(entity);
            }
        }
        return result;
    }

    /**
     * @description: 根据信号机表主键ID集合查询信号机列表
     * @param ids 主键ID集合
     * @return Map key厂商类型  值信号机列表
     * @author yinguijin
     * @date 2019/5/8 15:52
     */
    @Override
    public Map<String, List<TelesemeList>> selectByIds(List<String> ids) {
        Map<String, List<TelesemeList>> map = new HashMap<>();
        //查询结果
        List<TelesemeList> lists = myBaseMapper.selectByIds(ids);
        //青松
        List<TelesemeList> qsList = new ArrayList<>();
        //SCATS
        List<TelesemeList> scatsList = new ArrayList<>();
        //海信
        List<TelesemeList> hsList = new ArrayList<>();
        //海康
        List<TelesemeList> hkList = new ArrayList<>();

        //获取Redis中实时状态
        for (TelesemeList entity : lists) {
            String status = redisUtil.getHash(RedisKeyConst.KEY_PREFIX + RedisKeyConst.TELESEME_STATUS, entity.getId());
            if (StringUtils.isNotEmpty(status)) {
                entity.setSignalStatus(Integer.valueOf(status));
            }
            //封装
            if (entity.getSignalType().equals(BaseEnum.VendorTypeEnum.QS.getNick())) {
                qsList.add(entity);
            } else if (entity.getSignalType().equals(BaseEnum.VendorTypeEnum.SCATS.getNick())) {
                scatsList.add(entity);
            } else if (entity.getSignalType().equals(BaseEnum.VendorTypeEnum.HS.getNick())) {
                hsList.add(entity);
            } else if (entity.getSignalType().equals(BaseEnum.VendorTypeEnum.HK.getNick())) {
                hkList.add(entity);
            }
        }
        //青松
        map.put(BaseEnum.VendorTypeEnum.QS.getNick(), qsList);
        //SCATS
        map.put(BaseEnum.VendorTypeEnum.SCATS.getNick(), scatsList);
        //海信
        map.put(BaseEnum.VendorTypeEnum.HS.getNick(), hsList);
        //海康
        map.put(BaseEnum.VendorTypeEnum.HK.getNick(), hkList);
        return map;
    }

    /**
     * @description: 实时刷新信号机状态--数据库
     * @author yinguijin
     * @date 2019/5/7 19:39
     */
    @Override
    public void refreshSignalStatusToDB() {
        BaseEnum.VendorTypeEnum[] enums = BaseEnum.VendorTypeEnum.values();
        for (BaseEnum.VendorTypeEnum vendorTypeEnum : enums) {
            //查询信号机列表
            TelesemeList query = new TelesemeList();
            query.setSignalType(vendorTypeEnum.getNick());
            List<TelesemeList> lists = select(query);
            if (ListUtil.isEmpty(lists)) {
                continue;
            }
            //请求外部接口封装参数
            List<BaseSignal> baseSignals = new ArrayList<>();
            Map<String, Integer> map = new HashMap<>();
            for (TelesemeList entity : lists) {
                map.put(entity.getSignalId(), entity.getSignalStatus());
                BaseSignal baseSignal = new BaseSignal();
                baseSignal.setSignalId(entity.getSignalId());
                baseSignal.setSignalType(entity.getSignalType());
                baseSignals.add(baseSignal);
            }
            //查询信号机状态
            List<SignalRunStatusVo> statusVos;
            if (vendorTypeEnum.getNick().equals(BaseEnum.VendorTypeEnum.QS.getNick()) && adSwitch) {
                statusVos = qsGetSignalMethodService.queryQsRunStatus(baseSignals);
            } else {
                ParamEntity<List<BaseSignal>> param = new ParamEntity<>();
                param.setDataContent(baseSignals);
                param.setSourceType(vendorTypeEnum.getNick());
                param.setSystemType(Const.SYSTEM_TYPE);
                param.setUpdateTime(new Date());
                statusVos = signalRunService.queryRunState(param).getDataContent();
            }
            //判断状态是否变更
            if (null != statusVos) {
                List<SignalRunStatusVo> updateList = new ArrayList<>();
                for (SignalRunStatusVo statusVo : statusVos) {
                    if (statusVo.getCommStatus() != map.get(statusVo.getSignalId())) {
                        updateList.add(statusVo);
                    }
                }
                //更新数据库
                if (ListUtil.isEmpty(updateList)) {
                    continue;
                }
                myBaseMapper.batchUpdateStatus(updateList);
            }
        }
    }

    /**
     * @description: 实时刷新信号机状态-redis
     * @author yinguijin
     * @date 2019/5/7 19:39
     */
    @Override
    public void refreshSignalStatusToRedis() {
        BaseEnum.VendorTypeEnum[] enums = BaseEnum.VendorTypeEnum.values();
        for (BaseEnum.VendorTypeEnum vendorTypeEnum : enums) {
            //查询信号机列表
            TelesemeList query = new TelesemeList();
            query.setSignalType(vendorTypeEnum.getNick());
            List<TelesemeList> lists = select(query);
            if (ListUtil.isEmpty(lists)) {
                continue;
            }
            //请求外部接口封装参数
            List<BaseSignal> baseSignals = new ArrayList<>();
            for (TelesemeList entity : lists) {
                BaseSignal baseSignal = new BaseSignal();
                baseSignal.setSignalId(entity.getSignalId());
                baseSignal.setSignalType(entity.getSignalType());
                baseSignals.add(baseSignal);
            }
            //查询信号机状态
            List<SignalRunStatusVo> statusVos;
            if (vendorTypeEnum.getNick().equals(BaseEnum.VendorTypeEnum.QS.getNick()) && adSwitch) {
                statusVos = qsGetSignalMethodService.queryQsRunStatus(baseSignals);
            } else {
                ParamEntity<List<BaseSignal>> param = new ParamEntity<>();
                param.setDataContent(baseSignals);
                param.setSourceType(vendorTypeEnum.getNick());
                param.setSystemType(Const.SYSTEM_TYPE);
                param.setUpdateTime(new Date());
                statusVos = signalRunService.queryRunState(param).getDataContent();
            }
            //刷新Redis状态
            if (null != statusVos) {
                for (SignalRunStatusVo statusVo : statusVos) {
                    String field = vendorTypeEnum.getNick() + Const.SEPARATOR_UNDER_LINE + statusVo.getSignalId();
                    redisUtil.setHash(RedisKeyConst.KEY_PREFIX + RedisKeyConst.TELESEME_STATUS, field, statusVo.getCommStatus()+"");
                }
            }
        }
    }

    /**
     * 信号机对象处理成标准接口请求对象（过滤不在线的信号机）
     * @param telesemeLists
     * @return
     */
    public List<BaseSignal> getBaseSignals(List<TelesemeList> telesemeLists) {
        List<BaseSignal> baseSignals = new ArrayList<>();
        BaseSignal baseSignal;
        for (TelesemeList entity : telesemeLists) {
            //过滤不在线的信号机
            if (entity.getSignalStatus() == BaseEnum.StatusEnum.OFF.getCode()) {
                continue;
            }
            baseSignal = new BaseSignal();
            baseSignal.setSignalId(entity.getSignalId());
            baseSignal.setSignalType(entity.getSignalType());
            baseSignals.add(baseSignal);
        }
        return baseSignals;
    }
}
