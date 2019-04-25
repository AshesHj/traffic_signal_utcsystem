package com.mapabc.signal.dao.vo.signalrunstatus;

import com.mapabc.signal.common.component.BaseSignal;
import lombok.Data;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [信号机运行状态实体]
 * Created on 2019/4/22 17:01
 */
@Data
public class SignalRunStatusVo extends BaseSignal {

    //通讯状态：1 通讯正常 2 通讯中断
    private Integer commStatus;

    //控制模式：1 本地时间表 2 中心手动 3 感应方案 4 自适应控制 5 黄闪控制
    // 6 全红控制 7 方案锁定 8 相位锁定 9 特勤控制 10 实时优化
    private Integer controlMode;

}
