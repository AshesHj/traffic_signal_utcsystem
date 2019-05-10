package com.mapabc.signal.common.util;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: [字符串工具类]
 * @author yinguijin
 * @version 1.0
 * Created on 2019/4/16 15:07
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * @description: 判断字符串是否为空
     * @param value 字符串
     * @return java.lang.Boolean 空：false  不是空：true
     * @author yinguijin
     * @date 2019/4/17 9:54
     */
    public static Boolean isNotEmpty(String value) {
        if (value == null || value.length() == 0) {
            return false;
        }
        return true;
    }


    /**
     * @description: 判断字符串是否为空
     * @param value 字符串
     * @return java.lang.Boolean 空：true  不是空：false
     * @author yinguijin
     * @date 2019/4/17 9:54
     */
    public static Boolean isEmpty(String value) {
        if (value == null || value.length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * @description: 判断对象是否为空
     * @param obj 对象
     * @return java.lang.Boolean 空：true  不是空：false
     * @author yinguijin
     * @date 2019/4/17 9:54
     */
    public static Boolean isEmpty(Object obj) {
        if (null == obj) {
            return true;
        }
        return false;
    }


    /**
     * @description: 判断字符串是否为空、空字符、null
     * @param string 字符串
     * @return java.lang.Boolean 空：true  不是空：false
     * @author yinguijin
     * @date 2019/4/17 9:54
     */
    public static Boolean isRedisEmpty(String string) {
        if (StringUtils.isEmpty(string) || "null".equals(string) || "".equals(string)) {
            return true;
        }
        return false;
    }


    /**
     * @description: null转化为空字符
     * @param str 字符串对象
     * @return java.lang.String 空字符
     * @author yinguijin
     * @date 2019/4/17 9:58
    */
    public static String replaceNullToEmpty(String str) {
        if (str == null) {
            str = "";
        }
        return str;
    }

    /**
     * @description: 根据字符串获取Integer值，空字符串返回null
     * @param str 字符串
     * @return java.lang.Integer
     * @author yinguijin
     * @date 2019/4/16 15:34
     */
    public static Integer getIntValue(String str) {

        if (org.apache.commons.lang3.StringUtils.isBlank(str)) {
            return null;
        }
        if (!isNumeric(str)) {
            return null;
        }
        return Integer.parseInt(str);
    }

    /**
     * @description: 根据字符串获取Double值，空字符串返回null
     * @param str 字符串
     * @return java.lang.Double
     * @author yinguijin
     * @date 2019/4/16 15:34
     */
    public static Double getDoubleValue(String str) {
        if (org.apache.commons.lang3.StringUtils.isBlank(str)) {
            return null;
        }
        if (!isNumber(str)) {
            return null;
        }
        return Double.parseDouble(str);
    }

    /**
     * @description: 根据字符串获取BigDecimal值，空字符串返回null
     * @param str 字符串
     * @return BigDecimal
     * @author yinguijin
     * @date 2019/4/16 15:34
     */
    public static BigDecimal getBigDecimalValue(String str) {
        if (org.apache.commons.lang3.StringUtils.isBlank(str)) {
            return null;
        }
        if (!isNumber(str)) {
            return null;
        }
        return new BigDecimal(str);
    }

    /**
     * <p>Description:[判断字符串是否为整数]</p>
     * @param str 字符串
     * @return boolean 是否是整数
     * @author yinguijin
     * @date 2019/4/16 15:34
     */
    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Description:[判断字符串是否为数字类型]</p>
     * @param str 字符串
     * @return boolean 是否是数字类型
     * @author yinguijin
     * @date 2019/4/16 15:34
     */
    public static boolean isNumber(String str) {
        String reg = "^[0-9]+(.[0-9]+)?$";
        return str.matches(reg);
    }

    /**
     * <p>Description:[字符串数组转int数组]</p>
     * @param strings 字符串数组
     * @return int[] int类型数组
     * @author yinguijin
     * @date 2019/4/16 15:34
     */
    public static int[] stringArrayToIntArray(String[] strings) {
        try {
            if (null == strings || strings.length == 0) {
                return null;
            }
            int[] ints = new int[strings.length];
            if (ints.length > 0) {
                for (int i = 0; i < strings.length; i++) {
                    if (isBlank(strings[i])) {
                        ints[i] = 0;
                    } else {
                        ints[i] = Integer.parseInt(strings[i]);
                    }
                }
            }
            return ints;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * <p>Description:[字符串数组转Long数组]</p>
     * @param strings 字符串数组
     * @return int[] int类型数组
     * @author yinguijin
     * @date 2019/4/16 15:34
     */
    public static Long[] stringArrayToLongArray(String[] strings) {
        if (null == strings || strings.length == 0) {
            return null;
        }
        Long[] longs = new Long[strings.length];
        if (null != longs && longs.length > 0) {
            for (int i = 0; i < strings.length; i++) {
                longs[i] = Long.parseLong(strings[i]);
            }
        }
        return longs;
    }

    /**
     * <p>Description:[字符类型list转Integer类型List]</p>
     * @param stringList 字符串数组
     * @return List<Integer> Integer类型List
     * @author yinguijin
     * @date 2019/4/16 15:34
     */
    public static List<Integer> stringListToIntegerList(List<String> stringList) {
        if (null == stringList || stringList.size() == 0) {
            return null;
        }
        List<Integer> integerList = new ArrayList<>();
        for (String string : stringList) {
            integerList.add(getIntValue(string));
        }
        return integerList;
    }

    /**
     * <p>Description:[字符类型list转Long类型List]</p>
     * @param stringList 字符串数组
     * @return List<Long> Long类型List
     * @author yinguijin
     * @date 2019/4/16 15:34
     */
    public static List<Long> stringListToLongList(List<String> stringList) {
        if (null == stringList || stringList.size() == 0) {
            return null;
        }
        List<Long> longList = new ArrayList<>();
        for (String string : stringList) {
            if(isEmpty(string)||string.equals("null")){
                continue;
            }
            longList.add(Long.parseLong(string));
        }
        return longList;
    }

    /**
     * <p>Description:[字符串转小写字符串]</p>
     * @param origString 原始字符串
     * @return 转换后的字符串
     * @author yinguijin
     * @date 2019/4/16 15:34
     */
    public static String getLowerString(String origString) {
        if (StringUtils.isBlank(origString)) {
            return origString;
        }
        return origString.toLowerCase();
    }

    /**
     * <p>Description:[字符串转小写字符串]</p>
     * @param origString 原始字符串
     * @return 转换后的字符串
     * @author yinguijin
     * @date 2019/4/16 15:34
     */
    public static String getUpperString(String origString) {
        if (StringUtils.isBlank(origString)) {
            return origString;
        }
        return origString.toUpperCase();
    }

    /**
     * <p>Description:[判断字符串是否只包含数字和字母]</p>
     * @author yinguijin
     * @date 2019/4/16 15:34
     */
    public static boolean containLetterAndNumber(String string) {
        return string.matches("[0-9A-Za-z]*");
    }

    /**
     * <p>Description:[判断字符串是否只包含数字和横杠（电话号校验）]</p>
     * @author yinguijin
     * @date 2019/4/16 15:34
     */
    public static boolean isPhone(String string) {
        return string.matches("[0-9-]*");
    }

    /**
     * <p>Description:[过滤表情符号]</p>
     * @author yinguijin
     * @date 2019/4/16 15:34
     */
    public static String filterEmoji(String string) {
        if(StringUtils.isEmpty(string)){
            return null;
        }
        //Pattern emoji = Pattern.compile ("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Pattern emoji = Pattern.compile ("(?:[\uD83C\uDF00-\uD83D\uDDFF]|[\uD83E\uDD00-\uD83E\uDDFF]|[\uD83D\uDE00-\uD83D\uDE4F]|[\uD83D\uDE80-\uD83D\uDEFF]|[\u2600-\u26FF]\uFE0F?|[\u2700-\u27BF]\uFE0F?|\u24C2\uFE0F?|[\uD83C\uDDE6-\uD83C\uDDFF]{1,2}|[\uD83C\uDD70\uD83C\uDD71\uD83C\uDD7E\uD83C\uDD7F\uD83C\uDD8E\uD83C\uDD91-\uD83C\uDD9A]\uFE0F?|[\u0023\u002A\u0030-\u0039]\uFE0F?\u20E3|[\u2194-\u2199\u21A9-\u21AA]\uFE0F?|[\u2B05-\u2B07\u2B1B\u2B1C\u2B50\u2B55]\uFE0F?|[\u2934\u2935]\uFE0F?|[\u3030\u303D]\uFE0F?|[\u3297\u3299]\uFE0F?|[\uD83C\uDE01\uD83C\uDE02\uD83C\uDE1A\uD83C\uDE2F\uD83C\uDE32-\uD83C\uDE3A\uD83C\uDE50\uD83C\uDE51]\uFE0F?|[\u203C\u2049]\uFE0F?|[\u25AA\u25AB\u25B6\u25C0\u25FB-\u25FE]\uFE0F?|[\u00A9\u00AE]\uFE0F?|[\u2122\u2139]\uFE0F?|\uD83C\uDC04\uFE0F?|\uD83C\uDCCF\uFE0F?|[\u231A\u231B\u2328\u23CF\u23E9-\u23F3\u23F8-\u23FA]\uFE0F?)",Pattern.UNICODE_CASE | Pattern . CASE_INSENSITIVE ) ;
        Matcher emojiMatcher = emoji.matcher(string);
        if (emojiMatcher.find()) {
            string = emojiMatcher.replaceAll("");
        }
        return string;
    }
	
	/**
	 * <p>Description:[获取URL文件名称KEY]</p>
	 * @param imgUrl
	 * @return String
     * @author yinguijin
     * @date 2019/4/16 15:34
	 */
    public static String getFileUrlName(String imgUrl) {
		if (imgUrl == null) {
			return null;
		}
		String[] strs = imgUrl.split("/");
		if(strs == null || strs.length == 0){
			return null;
		}
		String temp = strs[strs.length - 1];
		if(StringUtils.isBlank(temp)){
			return null;
		}
		if (temp.lastIndexOf(".") < 0) {
            return null;
        }
		temp = temp.substring(0, temp.lastIndexOf("."));
		return temp;
	}

	/**
	 * <p>Description:[获取uuid]</p>
     * @author yinguijin
     * @date 2019/4/16 15:34
	 */
	public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * <p>Description:[获取两个字符串数组交集]</p>
     * @param str1  字符串1（逗号分隔）
     * @param str2  字符串2（逗号分隔）
     * @author yinguijin
     * @date 2019/4/16 15:34
     */
    public static String getIntersection(String str1, String str2){
	    String[] ary1 = str1.split(",");
	    String[] ary2 = str2.split(",");

        Set<String> result = new HashSet<>();
        for (String s1: ary1) {
            for (String s2: ary2) {
                if(s1.equalsIgnoreCase(s2)){
                    result.add(s1.trim());
                }
            }
        }
	    return result.toString().replace("[","").replace("]","");
    }
}
