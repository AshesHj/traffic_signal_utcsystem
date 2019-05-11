package com.mapabc.signal.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.CollationKey;
import java.text.Collator;
import java.util.*;

/**
 * @description: 集合处理工具类
 * @author yinguijin
 * @version 1.0
 * Created on 2019/5/11 9:34
 */
public class ListUtil {

    public final static String SORT_DESC = "desc";

    public final static String SORT_ASC = "asc";

	/**
	 * list 相位序号排序
	 * @param resultList
	 */
    public static void listSort(List<Map<String, Object>> resultList) {
        Collections.sort(resultList, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                String phaseNo1=o1.get("phaseOrderNo").toString();
                String phaseNo2=o2.get("phaseOrderNo").toString();
                Collator instance = Collator.getInstance();
                return instance.compare(phaseNo1, phaseNo2);

            }
        });
    }
    /**
     * 时段排序
     * @param jarray
     */
    public static void JSONlistSort(JSONArray jarray) {
    	List<JSONObject> resultList=new ArrayList<JSONObject>();
    	for (int i = 0; i < jarray.size(); i++) {
			resultList.add((JSONObject) jarray.get(i));
		}
        Collections.sort(resultList, new Comparator<JSONObject>() {
			@Override
			public int compare(JSONObject o1, JSONObject o2) {
				String startTime1 = o1.getString("from") ;
				String startTime2 = o2.getString("from") ;
				int St1=Integer.valueOf(startTime1);
				int St2=Integer.valueOf(startTime2);
                return (St1-St2);
			}
        });
        jarray.clear();
        for (int j = 0; j < resultList.size(); j++) {
        	jarray.add(resultList.get(j));
		}
        System.out.println(jarray);
    }

    /**
     * 时段排序
     * @param resultList
     */
    public static void listSortTime(List<Map<String, Object>> resultList) {
        Collections.sort(resultList, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                String startTime1=o1.get("from").toString();
                String startTime2=o2.get("from").toString();
                int St1=Integer.valueOf(startTime1);
				int St2=Integer.valueOf(startTime2);
				//Collator instance = Collator.getInstance();
                return (St1-St2);
            }
        });
    }
    
    
    /**
	 * list week排序
	 * @param resultList
	 */
    public static void listSortWeek(List<Map<String, Object>> resultList) {
        Collections.sort(resultList, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                String phaseNo1=o1.get("weekId").toString();
                String phaseNo2=o2.get("weekId").toString();
                Collator instance = Collator.getInstance();
                return instance.compare(phaseNo1, phaseNo2);

            }
        });
    }
    
    
    /**
	 * list week排序
	 * @param jarray
	 */
    public static void listSortWeek(JSONArray jarray) {
    	List<JSONObject> resultList=new ArrayList<JSONObject>();
    	for (int i = 0; i < jarray.size(); i++) {
			resultList.add((JSONObject) jarray.get(i));
		}
        Collections.sort(resultList, new Comparator<JSONObject>() {
        	public int compare(JSONObject o1, JSONObject o2) {
        		if (null==o1.getString("weekDay")) {
        			o1.put("weekDay", 0);
				}
        		if (null==o2.getString("weekDay")) {
        			o2.put("weekDay", 0);
        		}
				String startTime1 = o1.getString("weekDay") ;
				String startTime2 = o2.getString("weekDay") ;
				int St1=Integer.valueOf(startTime1);
				int St2=Integer.valueOf(startTime2);
                return (St1-St2);
			}
        });
        jarray.clear();
        for (int j = 0; j < resultList.size(); j++) {
        	jarray.add(resultList.get(j));
		}
        System.out.println(jarray);
    }



    /**
     * <p>Description:[List集合排序类(可按中文排序)]</p>
     * @param list     目标集合
     * @param property 排序字段名
     * @param sortType 正序 (SORT_ASC)、倒序 (SORT_DESC)
     * @param isCN     是否按中文排序
     * @author: yinguijin
     */
    public static <T> void sortList(List<T> list, final String property, final String sortType, final boolean isCN) {
        Collections.sort(list, new Comparator<T>() {
            private Collator collator = null;

            public int compare(T a, T b) {
                int ret = 0;
                Field field = ReflectionUtils.findField(a.getClass(), property);
                String getterMethodName = "get" + org.apache.commons.lang3.StringUtils.capitalize(property);
                Method method = ReflectionUtils.findMethod(a.getClass(), getterMethodName);
                Object value_a = ReflectionUtils.invokeMethod(method, a);
                Object value_b = ReflectionUtils.invokeMethod(method, b);
                if (field.getType() == String.class) {
                    if (isCN) {
                        collator = Collator.getInstance();
                        CollationKey key1 = collator.getCollationKey(value_a.toString());
                        CollationKey key2 = collator.getCollationKey(value_b.toString());
                        if (sortType != null && sortType.equals(SORT_DESC)) {
                            ret = key2.compareTo(key1);
                        } else {
                            ret = key1.compareTo(key2);
                        }
                    } else {
                        if (sortType != null && sortType.equals(SORT_DESC)) {
                            ret = value_b.toString().compareTo(value_a.toString());
                        } else {
                            ret = value_a.toString().compareTo(value_b.toString());
                        }
                    }
                } else if (field.getType() == Integer.class || field.getType() == Long.class || field.getType() == BigDecimal.class) {
                    BigDecimal decA = new BigDecimal(value_a.toString());
                    BigDecimal decB = new BigDecimal(value_b.toString());
                    if (sortType != null && sortType.equals(SORT_DESC)) {
                        ret = decB.compareTo(decA);
                    } else {
                        ret = decA.compareTo(decB);
                    }
                } else if (field.getType() == Date.class) {
                    if (sortType != null && sortType.equals(SORT_DESC)) {
                        ret = ((Date) value_b).compareTo((Date) value_a);
                    } else {
                        ret = ((Date) value_a).compareTo((Date) value_b);
                    }
                }
                return ret;
            }
        });
    }

    /**
     * <p>Description:[List集合排序类（默认不按照中文排序）]</p>
     * @param list     目标集合
     * @param property 排序字段名
     * @param sortType 正序 (SORT_ASC)、倒序 (SORT_DESC)
     * @author: yinguijin
     */
    public static <T> void sortList(List<T> list, final String property, final String sortType) {
        sortList(list, property, sortType, false);
    }

    /**
     * <p>Description:[对象数组排序(可按中文排序)]</p>
     * @param array    对象数组
     * @param property 排序字段名
     * @param sortType 正序 (SORT_ASC)、倒序 (SORT_DESC)
     * @param isCN     是否按中文排序
     * @author: yinguijin
     */
    public static <T> void sortObjectArray(T[] array, final String property, final String sortType, final boolean isCN) {
        Arrays.sort(array, new Comparator<T>() {
            private Collator collator = null;

            public int compare(T a, T b) {
                int ret = 0;
                Field field = ReflectionUtils.findField(a.getClass(), property);
                String getterMethodName = "get" + org.apache.commons.lang3.StringUtils.capitalize(property);
                Method method = ReflectionUtils.findMethod(a.getClass(), getterMethodName);
                Object value_a = ReflectionUtils.invokeMethod(method, a);
                Object value_b = ReflectionUtils.invokeMethod(method, b);
                if (field.getType() == String.class) {
                    if (isCN) {
                        collator = Collator.getInstance();
                        CollationKey key1 = collator.getCollationKey(value_a.toString());
                        CollationKey key2 = collator.getCollationKey(value_b.toString());
                        if (sortType != null && sortType.equals(SORT_DESC)) {
                            ret = key2.compareTo(key1);
                        } else {
                            ret = key1.compareTo(key2);
                        }
                    } else {
                        if (sortType != null && sortType.equals(SORT_DESC)) {
                            ret = value_b.toString().compareTo(value_a.toString());
                        } else {
                            ret = value_a.toString().compareTo(value_b.toString());
                        }
                    }
                } else if (field.getType() == Integer.class || field.getType() == Long.class || field.getType() == BigDecimal.class) {
                    BigDecimal decA = new BigDecimal(value_a.toString());
                    BigDecimal decB = new BigDecimal(value_b.toString());
                    if (sortType != null && sortType.equals(SORT_DESC))
                        ret = decB.compareTo(decA);
                    else
                        ret = decA.compareTo(decB);
                } else if (field.getType() == Date.class) {
                    if (sortType != null && sortType.equals(SORT_DESC))
                        ret = ((Date) value_b).compareTo((Date) value_a);
                    else
                        ret = ((Date) value_a).compareTo((Date) value_b);
                }
                return ret;
            }
        });
    }


    /**
     * <p>Description:[对象数组排序（默认不按照中文排序）]</p>
     * @param array    对象数组
     * @param property 排序字段名
     * @param sortType 正序 (SORT_ASC)、倒序 (SORT_DESC)
     * @author: yinguijin
     */
    public static <T> void sortObjectArray(T[] array, final String property, final String sortType) {
        sortObjectArray(array, property, sortType, false);
    }

    /**
     * <p>Description:[字符串数组排序(可按中文排序)]</p>
     * @param array    字符串数组
     * @param sortType 正序 (SORT_ASC)、倒序 (SORT_DESC)
     * @param isCN     是否按中文排序
     * @author: yinguijin
     */
    public static <T> void sortArray(T[] array, final String sortType, final boolean isCN) {
        if (sortType != null && sortType.equals(SORT_DESC)) {
            if (isCN) {
                Arrays.sort(array, Collections.reverseOrder(Collator.getInstance(Locale.CHINA)));
            } else {
                Arrays.sort(array, Collections.reverseOrder());
            }
        } else {
            if (isCN) {
                Arrays.sort(array, Collator.getInstance(Locale.CHINA));
            } else {
                Arrays.sort(array);
            }
        }
    }

    /**
     * <p>Description:[字符串数组排序（默认不按照中文排序）]</p>
     * @param array    字符串数组
     * @param sortType 正序 (SORT_ASC)、倒序 (SORT_DESC)
     * @author: yinguijin
     */
    public static <T> void sortArray(T[] array, final String sortType) {
        sortArray(array, sortType, false);
    }

    /**
     * <p>Description:[获取list的toString(值以逗号分隔，无中括号)]</p>
     * @param list
     * @return
     * @author: yinguijin
     */
    public static <T> String getString(List<T> list) {
        Iterator<T> it = list.iterator();
        if (!it.hasNext()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (; ; ) {
            T e = it.next();
            sb.append(e);
            if (!it.hasNext()) {
                return sb.toString();
            }
            sb.append(',').append(' ');
        }
    }

    /**
     * <p>Description:[获取set的toString(值以逗号分隔，无中括号)]</p>
     * @param set
     * @return
     * @author:武超强
     */
    public static <T> String getString(Set<T> set) {
        Iterator<T> it = set.iterator();
        if (!it.hasNext())
            return "";

        StringBuilder sb = new StringBuilder();
        for (; ; ) {
            T e = it.next();
            sb.append(e);
            if (!it.hasNext())
                return sb.toString();
            sb.append(',').append(' ');
        }
    }

    /**
     * <p>Description:[获取数组的toString(值以逗号分隔，无中括号)]</p>
     * @param arr
     * @return
     * @author: yinguijin
     */
    public static <T> String getString(T[] arr) {
        List<T> list = Arrays.asList(arr);
        return getString(list);
    }

    /**
     * @Description: [验证集合是否为空：null或size==0 返回false] <br/>
     * @param collection 集合
     * @return 空或size==0 返回false
     * @author: yinguijin
     */
    public static boolean isNotEmpty(Collection collection) {
        if (collection == null || collection.size() < 1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * @Description: [验证集合是否为空：null或size==0 返回true] <br/>
     * @param collection 集合
     * @return 空或size==0 返回true
     * @author: yinguijin
     */
    public static boolean isEmpty(Collection collection) {
        return !isNotEmpty(collection);
    }

}
