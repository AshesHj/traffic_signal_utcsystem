package com.mapabc.signal.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: [枚举类]
 * @author yinguijin
 * @version 1.0
 * Created on 2019/4/16 15:07
 */
public class BaseEnum {

    @Getter
    @AllArgsConstructor
    public enum CommonEnum {
        DELETE_NO(0, "未删除"),
        DELETE_YES(1, "已删除");
        private Integer value;
        private String name;
    }

    /**
     * @description: [操作日志类型枚举]
     * @author yinguijin
     * @date 2019/4/17 14:09
    */
    public  enum OperationTypeEnum {
        INSERT,UPDATE,QUERY,DELETE
    }
}
