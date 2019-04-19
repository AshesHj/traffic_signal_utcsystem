package com.mapabc.signal.common.component;

import lombok.Data;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [基础信号机实体--定义基础字段--需要该字段则继承它]
 * Created on 2019/4/18 18:50
 */
@Data
public class BaseSignal {

    //信号机编号
    private String signalId;

    //信号机类型
    private String signalType;
}
