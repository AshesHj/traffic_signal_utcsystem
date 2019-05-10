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

    /**
     * @description: [操作日志类型枚举]
     * @author yinguijin
     * @date 2019/4/17 14:09
    */
    public enum OperationTypeEnum {
        INSERT,UPDATE,QUERY,DELETE
    }

    /**
     * @description: [状态]
     * @author yinguijin
     * @date 2019/4/19 18:01
     */
    @Getter
    @AllArgsConstructor
    public enum StatusEnum {
        ON(1, "在线/有效"),
        OFF(2, "下线/无效");

        private Integer code;

        private String name;
    }

    /**
     * @description: [厂商类型枚举类]
     * @author yinguijin
     * @date 2019/4/19 18:01
    */
    @Getter
    @AllArgsConstructor
    public enum VendorTypeEnum {
        QS(1, "青松", "QS"),
        SCATS(2, "SCATS", "SCATS"),
        HS(3, "海信", "HS"),
        HK(4, "海康", "HK");

        private Integer code;

        private String name;

        private String nick;

        public static VendorTypeEnum getNameByCode(int code) {
            for (VendorTypeEnum vendorTypeEnum : VendorTypeEnum.values()) {
                if(vendorTypeEnum.getCode() == code){
                    return vendorTypeEnum;
                }
            }
            return null;
        }

        public static VendorTypeEnum getNameByNick(String nick) {
            for (VendorTypeEnum vendorTypeEnum : VendorTypeEnum.values()) {
                if(vendorTypeEnum.getNick().equals(nick)){
                    return vendorTypeEnum;
                }
            }
            return null;
        }
    }

    @Getter
    @AllArgsConstructor
    public enum VendorMethodCodeEnum {
        QS("1", "路口列表数据获取"),
        SCATS("1", "SCATS"),
        HS("1", "海信"),
        HK("1", "海康");

        private String code;

        private String name;

        public static VendorMethodCodeEnum getNameByCode(String code) {
            for (VendorMethodCodeEnum methodTypeEnum : VendorMethodCodeEnum.values()) {
                if(methodTypeEnum.getCode().equals(code)){
                    return methodTypeEnum;
                }
            }
            return null;
        }
    }

    /**
     * @description: [信号方向枚举类]
     * @author yinguijin
     * @date 2019/4/19 18:01
     */
    @Getter
    @AllArgsConstructor
    public enum SignalDirectionEnum {
        N(1, "北", "N"),
        EN(2, "东北", "EN"),
        E(3, "东", "E"),
        ES(4, "东南", "ES"),
        S(5, "南", "S"),
        WS(6, "西南", "WS"),
        W(7, "西", "W"),
        WN(8, "西北", "WN");

        private Integer code;

        private String name;

        private String nick;

        public static SignalDirectionEnum getNameByCode(int code) {
            for (SignalDirectionEnum signalDirectionEnum : SignalDirectionEnum.values()) {
                if(signalDirectionEnum.getCode() == code){
                    return signalDirectionEnum;
                }
            }
            return null;
        }

        public static SignalDirectionEnum getNameByNick(String nick) {
            for (SignalDirectionEnum signalDirectionEnum : SignalDirectionEnum.values()) {
                if(signalDirectionEnum.getNick().equals(nick)){
                    return signalDirectionEnum;
                }
            }
            return null;
        }
    }

    /**
     * @description: [车辆类型枚举类]
     * @author yinguijin
     * @date 2019/4/19 18:01
     */
    @Getter
    @AllArgsConstructor
    public enum VehicleTypeEnum {
        LARGE_VEHICLE("01", "大型车辆"),
        MEDIUM_VEHICLE("02", "中型车辆"),
        SMALL_VEHICLE("03", "小型车辆"),
        MICRO_VEHICLE("04", "微型车辆"),
        TRICYCLE("05", "三轮车"),
        MOTORCYCLE("06", "摩托车"),
        POLICE_VEHICLE("07", "警用车辆"),
        FOREIGN_VEHICLE("08", "外籍车辆"),
        OTHER("99", "其他");

        private String code;
        private String name;

        public static String getNameByCode(int code) {
            for (SignalDirectionEnum signalDirectionEnum : SignalDirectionEnum.values()) {
                if(signalDirectionEnum.getCode() == code){
                    return signalDirectionEnum.getName();
                }
            }
            return "";
        }
    }
}
