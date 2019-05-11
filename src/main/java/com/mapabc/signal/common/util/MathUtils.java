package com.mapabc.signal.common.util;

import java.math.BigDecimal;

/**
 * @description: 数字处理工具类
 * @author yinguijin
 * @version 1.0
 * Created on 2019/5/11 9:34
 */
public class MathUtils {

    /** 一百的常量 */
    private static final int HUNDRED_NUMBER = 100;

    /** 默认保留小数位数 */
    private static final int DEFAULT_DECIMAL_NUMBER = 2;

    /**
     *  <p>Description:[根据两个整数获取BigDecimal数值]</p>
     * @param a 分子数字
     * @param b 分母数字
     * @return BigDecimal 保留4位小数返回值
     */
    public static BigDecimal getBigDecimal(int a, int b) {
        if (b == 0) {
            return new BigDecimal(0).setScale(DEFAULT_DECIMAL_NUMBER, BigDecimal.ROUND_HALF_UP);
        }
        return new BigDecimal((float)a/b).setScale(DEFAULT_DECIMAL_NUMBER, BigDecimal.ROUND_HALF_UP);
    }

    /**
     *  <p>Description:[根据两个整数获取BigDecimal数值]</p>
     * @param a 分子数字
     * @param b 分母数字
     * @return BigDecimal 保留4位小数返回值
     */
    public static BigDecimal getBigDecimal(long a, long b) {
        if (b == 0) {
            return new BigDecimal(0).setScale(DEFAULT_DECIMAL_NUMBER, BigDecimal.ROUND_HALF_UP);
        }
        return new BigDecimal((float)a/b).setScale(DEFAULT_DECIMAL_NUMBER, BigDecimal.ROUND_HALF_UP);
    }

    /**
     *  <p>Description:[根据两个整数获取BigDecimal数值]</p>
     * @param a 分子数字
     * @param b 分母数字
     * @return BigDecimal 保留4位小数返回值
     */
    public static BigDecimal getBigDecimal(double a, double b) {
        if (b == 0) {
            return new BigDecimal(0).setScale(DEFAULT_DECIMAL_NUMBER, BigDecimal.ROUND_HALF_UP);
        }
        return new BigDecimal((float)a/b).setScale(DEFAULT_DECIMAL_NUMBER, BigDecimal.ROUND_HALF_UP);
    }

    /**
     *  <p>Description:[计算一个数值的百分比字符串，保留两位小数]</p>
     * @param bigDecimal 参数
     * @return String 保留2位小数返回值
     */
    public static String getPercentBigDecimal(BigDecimal bigDecimal) {
        return getBigDecimal(bigDecimal.multiply(new BigDecimal(HUNDRED_NUMBER)), DEFAULT_DECIMAL_NUMBER).toString() + "%";
    }

    /**
     *  <p>Description:[BigDecimal保留对应的小数位数]</p>
     * @param bigDecimal 数据参数
     * @param scale 保留小数位数
     * @return BigDecimal 保留小数后的数据
     */
    public static BigDecimal getBigDecimal(BigDecimal bigDecimal, int scale) {
        return bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     *  <p>Description:[BigDecimal保留对应的小数位数]</p>
     * @param bigDecimal 数据
     * @param scale 保留小数位数
     * @return BigDecimal 保留小数后的数据
     */
    public static BigDecimal getBigDecimal(String bigDecimal, int scale) {
        if(StringUtils.isEmpty(bigDecimal)) {
            return null;
        }
        BigDecimal value = new BigDecimal(bigDecimal);
        return value.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * @description: 转换int类型
     * @param value 值
     * @return int
     * @author yinguijin
     * @date 2019/5/8 12:03
    */
    public static Integer getInteger(String value) {
        if(StringUtils.isEmpty(value)) {
            return null;
        }
        return Integer.valueOf(value);
    }

    /**
     *  <p>Description:[BigDecimal保留两位小数位数]</p>
     * @param bigDecimal 数据参数
     * @return BigDecimal 保留两位小数后的数据
     */
    public static BigDecimal getBigDecimal(BigDecimal bigDecimal) {
        return bigDecimal.setScale(DEFAULT_DECIMAL_NUMBER, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * * 两个Double数相除，并保留scale位小数 *
     * Created on: 2018/8/11  12:36
     * @author zhushuaifei
     * @param v1 *
     * @param v2 *
     * @param scale *
     * @return Double
     */
    public static Double division(Double v1, Double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return new Double(b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    /**
     * * 两个Integer数相除，并保留scale位小数 *
     * Created on: 2018/8/11  12:36
     * @author zhushuaifei
     * @param v1 *
     * @param v2 *
     * @param scale *
     * @return Double
     */
    public static Double division(Integer v1, Integer v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return new Double(b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue());
    }
}
