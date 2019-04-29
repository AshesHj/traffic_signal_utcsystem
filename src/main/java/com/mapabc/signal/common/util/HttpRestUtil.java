package com.mapabc.signal.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.mapabc.signal.common.component.VendorResult;
import com.mapabc.signal.dao.vo.cross.CrossingVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.Map;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [HttpRest请求工具类]
 * Created on 2019/4/19 10:21
 */
public class HttpRestUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRestUtil.class);

    public HttpRestUtil() {

    }

    private static RestTemplate get() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

    /**
     * @description: 发送GET请求，支持转换类型，应用多变性返回值，classType返回值类型
     * @param url 请求URL
     * @param param 请求参数
     * @param classType 转换对象
     * @return T 返回结果
     * @author yinguijin
     * @date 2019/4/19 11:47
     */
    public static <T> T doGet(String url, Map<String, String> headers, String param, Class<T> classType) {
        long startTime = System.currentTimeMillis();
        LOGGER.info("\n 请求地址 = {} || 请求方式 = {} || 请求头 = {} || 请求参数 = {}",
                url, "GET", JSON.toJSONString(headers), JSON.toJSONString(param));
        //封装httpEntity
        HttpEntity httpEntity = getHttpEntity(headers, param);
        //发送请求
        ResponseEntity<T> responseEntity = get().exchange(url, HttpMethod.GET, httpEntity, classType);
        //响应状态值 200 201 202
        boolean statusBool = false;
        if (HttpStatus.OK.equals(responseEntity.getStatusCode()) || HttpStatus.CREATED.equals(responseEntity.getStatusCode()) || HttpStatus.ACCEPTED.equals(responseEntity.getStatusCode())) {
            statusBool = true;
        }
        if (null != responseEntity && statusBool && responseEntity.hasBody()) {
            T result = responseEntity.getBody();
            LOGGER.info("请求地址 = {} || 请求参数 = {} || 响应时长 = {} || 响应结果 = {}",
                    url, JSON.toJSONString(param), System.currentTimeMillis() - startTime, JSON.toJSONString(result));
            if (StringUtils.isEmpty(result)) {
                return null;
            }
            return result;
        } else {
            LOGGER.error("请求地址 = {} || 请求参数 = {} || 响应时长 = {} || 响应结果 = {}",
                    url, JSON.toJSONString(param), System.currentTimeMillis() - startTime ,JSON.toJSONString(responseEntity.getBody()));
            throw new RuntimeException("调用接口 = " + url + " || " +
                    "失败状态 = " + responseEntity.getStatusCode() + " || " +
                    "响应 = " + JSON.toJSONString(responseEntity.getBody()));
        }
    }


    /**
     * @description: 发送POST请求，支持转换类型，应用多变性返回值，classType返回值类型
     * @param url 请求URL
     * @param headers 请求头
     * @param param 请求参数
     * @param classType 转换类型
     * @return T 返回结果
     * @author yinguijin
     * @date 2019/4/28 14:54
     */
    public static <T> T doPost(String url, Map<String, String> headers, String param, Class<T> classType) {
        long startTime = System.currentTimeMillis();
        LOGGER.info("\n 请求地址 = {} || 请求方式 = {} || 请求头 = {} || 请求参数 = {}",
                url, "POST", JSON.toJSONString(headers), JSON.toJSONString(param));
        //封装httpEntity
        HttpEntity httpEntity = getHttpEntity(headers, param);
        ResponseEntity<T> responseEntity = get().postForEntity(url, httpEntity, classType);
        //响应状态值 200 201 202
        boolean statusBool = false;
        if (HttpStatus.OK.equals(responseEntity.getStatusCode()) || HttpStatus.CREATED.equals(responseEntity.getStatusCode()) || HttpStatus.ACCEPTED.equals(responseEntity.getStatusCode())) {
            statusBool = true;
        }
        if (null != responseEntity && statusBool && responseEntity.hasBody()) {
            T result = responseEntity.getBody();
            LOGGER.info("请求地址 = {} || 请求参数 = {} || 响应时长 = {} || 响应结果 = {}",
                    url, JSON.toJSONString(param), System.currentTimeMillis() - startTime ,JSON.toJSONString(responseEntity.getBody()));
            if (StringUtils.isEmpty(result)) {
                return null;
            }
            return result;
        } else {
            LOGGER.error("请求地址 = {} || 请求参数 = {} || 响应时长 = {} || 响应结果 = {}",
                    url, JSON.toJSONString(param), System.currentTimeMillis() - startTime ,JSON.toJSONString(responseEntity.getBody()));
            throw new RuntimeException("调用接口 = " + url + " || " +
                    "失败状态 = " + responseEntity.getStatusCode() + " || " +
                    "响应 = " + JSON.toJSONString(responseEntity.getBody()));
        }
    }


    /**
     * @description: 发送GET请求，返回固定的返回类型VendorResult
     * @param url 请求URL
     * @param headers 请求头
     * @param param 请求参数
     * @return com.mapabc.signal.common.component.VendorResult<T> 返回结果
     * @author yinguijin
     * @date 2019/4/19 11:47
     */
    public static <T> VendorResult<T> doGet(String url, Map<String, String> headers, String param) {
        String result = doExecute(url, headers, HttpMethod.GET, param);
        if (StringUtils.isEmpty(result)) {
            return null;
        }
        // 转化结果
        VendorResult<T> tVendorResult = JSON.parseObject(result, new TypeReference<VendorResult<T>>() {
        });
        return tVendorResult;
    }

    /**
     * @description: 发送POST请求，返回固定的返回类型VendorResult
     * @param url 请求URL
     * @param headers 请求头
     * @param param 请求参数
     * @return com.mapabc.signal.common.component.VendorResult<T> 返回结果
     * @author yinguijin
     * @date 2019/4/19 11:47
     */
    public static <T> VendorResult<T> doPost(String url, Map<String, String> headers, String param) {
        String result = doExecute(url, headers, HttpMethod.POST, param);
        if (StringUtils.isEmpty(result)) {
            return null;
        }
        // 转化结果
        VendorResult<T> tVendorResult = JSON.parseObject(result, new TypeReference<VendorResult<T>>() {
        });
        return tVendorResult;
    }

    /**
     * @description: 发送请求，返回string字符串，需要自己转换，应用返回结果简单的请求
     * @param url 请求地址
     * @param headers 请求头
     * @param method 请求类型 POST、GET
     * @param param 参数
     * @return String 返回结果
     * @author yinguijin
     * @date 2019/4/19 12:39
     */
    public static String doExecute(String url, Map<String, String> headers, HttpMethod method, String param) {
        long startTime = System.currentTimeMillis();
        LOGGER.info("\n 请求地址 = {} || 请求方式 = {} || 请求头 = {} || 请求参数 = {}",
                url, method.name(), JSON.toJSONString(headers), JSON.toJSONString(param));
        //封装httpEntity
        HttpEntity httpEntity = getHttpEntity(headers, param);
        //发送请求
        ResponseEntity<String> responseEntity = get().exchange(url, method, httpEntity, String.class);
        //响应状态值 200 201 202
        boolean statusBool = false;
        if (HttpStatus.OK.equals(responseEntity.getStatusCode()) || HttpStatus.CREATED.equals(responseEntity.getStatusCode()) || HttpStatus.ACCEPTED.equals(responseEntity.getStatusCode())) {
            statusBool = true;
        }
        if (null != responseEntity && statusBool && responseEntity.hasBody()) {
            String result = responseEntity.getBody();
            LOGGER.info("请求地址 = {} || 请求参数 = {} || 响应时长 = {} || 响应结果 = {}",
                    url, JSON.toJSONString(param), System.currentTimeMillis() - startTime, result);
            if (StringUtils.isEmpty(result)) {
                return null;
            }
            return result;
        } else {
            LOGGER.error("请求地址 = {} || 请求参数 = {} || 响应时长 = {} || 响应结果 = {}",
                    url, JSON.toJSONString(param), System.currentTimeMillis() - startTime ,responseEntity.getBody());
            throw new RuntimeException("调用接口 = " + url + " || " +
                    "失败状态 = " + responseEntity.getStatusCode() + " || " +
                    "响应 = " + responseEntity.getBody());
        }
    }

    private static HttpEntity getHttpEntity(Map<String, String> headers, String param) {
        HttpHeaders httpHeaders = new HttpHeaders();
        Iterator iterator = headers.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry)iterator.next();
            String key = (String)entry.getKey();
            if (!httpHeaders.containsKey(key)) {
                httpHeaders.add(key, (String)entry.getValue());
            }
        }
        return new HttpEntity(param, httpHeaders);
    }

    public static void main(String[] args) {
//        String result = "{\"systemtype\":\"UTC\",\"sourcetype\":1,\"datacount\":0,\"updatetime\":\"2019-04-19 12:50:12\",\"datacontent\":[{\"signalid\":\"10001\",\"signaltype\":\"1\",\"crossname\":\"1\",\"crosstype\":\"\",\"longitude\":113.456,\"latitude\":39.345,\"directions\":[{\"detector\":\"正前方\",\"roads\":[{\"roadid\":\"1\",\"roadname\":\"黄埔大道\"}]}]}]}";
        String result = "{\"datacount\":0,\"systemtype\":\"UTC\",\"sourcetype\":1,\"datacontent\":[{\"crosstype\":\"\",\"directions\":[{\"roads\":[{\"roadid\":\"1\",\"roadname\":\"黄埔大道\"}],\"detector\":\"正前方\"}],\"signaltype\":\"1\",\"signalid\":\"10001\",\"latitude\":39.345,\"crossname\":\"1\",\"longitude\":113.456}],\"updatetime\":\"2019-04-19 12:50:12\"}";
        System.out.println(JSONObject.parseObject(result));
        VendorResult<CrossingVo> tVendorResult = JSON.parseObject(result, new TypeReference<VendorResult<CrossingVo>>() {
        });

        System.out.println(JSON.toJSON(tVendorResult));
    }
}
