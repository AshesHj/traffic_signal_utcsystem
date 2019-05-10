package com.mapabc.signal.service;

import com.mapabc.signal.common.component.BaseSignal;
import com.mapabc.signal.common.component.ParamEntity;
import com.mapabc.signal.dao.model.TelesemeList;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
* @Description: [信号机列表service]</p>
* @author yinguijin
* @version 1.0
* Created on 2019年04月30日
*/
@Repository
public interface TelesemeListService extends BaseService<TelesemeList> {

    /**
     * @description: 保存信号控制系统所有控制路口基本信息
     * @return CrossingVo 路口信息结果集合
     * @author yinguijin
     * @date 2019/4/19 14:48
     */
    void initCrossing(ParamEntity<BaseSignal> param);

    /**
     * @description: 根据信号机编号 或 信号机类型查询信号机信息（获取Redis实时信号机状态）
     * @param param 信号机ID和信号机类型
     * @return 信号机信息
     * @author yinguijin
     * @date 2019/5/7 11:51
    */
    List<TelesemeList> selectByTeleseme(BaseSignal param);

    /**
     * @description: 根据信号机编号 或 信号机类型查询信号机信息（只返回在线的信号机）
     * @param param 信号机ID和信号机类型
     * @return 信号机信息
     * @author yinguijin
     * @date 2019/5/7 11:51
     */
    List<BaseSignal> selectByBaseSignal(BaseSignal param);

    /**
     * @description: 根据信号机表主键ID集合查询信号机列表（获取Redis实时信号机状态）
     * @param ids 主键ID集合
     * @return Map key厂商类型  值信号机列表
     * @author yinguijin
     * @date 2019/5/8 15:52
    */
    Map<String, List<TelesemeList>> selectByIds(List<String> ids);

    /**
     * @description: 实时刷新信号机状态-数据库
     * @author yinguijin
     * @date 2019/5/7 19:39
    */
    void refreshSignalStatusToDB();

    /**
     * @description: 实时刷新信号机状态-redis
     * @author yinguijin
     * @date 2019/5/7 19:39
     */
    void refreshSignalStatusToRedis();

    /**
     * 信号机对象处理成标准接口请求对象（过滤不在线的信号机）
     * @param telesemeLists
     * @return
     */
    List<BaseSignal> getBaseSignals(List<TelesemeList> telesemeLists);
}
