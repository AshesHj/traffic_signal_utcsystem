package com.mapabc.signal.dao.mapper;

import com.mapabc.signal.common.component.BaseSignal;
import com.mapabc.signal.dao.MyBaseMapper;
import com.mapabc.signal.dao.model.TelesemeList;
import com.mapabc.signal.dao.vo.signalrunstatus.SignalRunStatusVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @Description: [信号机列表持久层实现]</p>
* @author yinguijin
* @version 1.0
* Created on 2019年04月30日
*/
@Repository
public interface TelesemeListMapper extends MyBaseMapper<TelesemeList> {

    /**
     * @param param 信号机ID和信号机类型
     * @return 信号机信息
     * @description: 根据信号机ID和信号机类型查询信号机信息
     * @author yinguijin
     * @date 2019/5/7 11:51
     */
    List<BaseSignal> selectByBaseSignal(@Param("entity") BaseSignal param);


    List<TelesemeList> selectByTeleseme(@Param("entity") BaseSignal param);

    /**
     * @description: 根据信号机表主键ID集合查询信号机列表
     * @param ids 主键ID集合
     * @return Map key厂商类型  值信号机列表
     * @author yinguijin
     * @date 2019/5/8 15:52
     */
    List<TelesemeList> selectByIds(@Param("ids") List<String> ids);

    /**
     * @description: 批量更新信号机状态
     * @param list 信号机状态列表
     * @author yinguijin
     * @date 2019/5/7 20:30
    */
    void batchUpdateStatus(@Param("list") List<SignalRunStatusVo> list);
}