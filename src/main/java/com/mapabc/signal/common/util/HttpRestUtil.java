package com.mapabc.signal.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.mapabc.signal.common.component.VendorResult;
import com.mapabc.signal.dao.model.cross.Crossing;
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
     * @description: 发送GET请求
     * @param url 请求URL
     * @param param 请求参数
     * @return com.mapabc.signal.common.component.VendorResult<T> 返回结果
     * @author yinguijin
     * @date 2019/4/19 11:47
     */
    public static <T> VendorResult<T> doGet(String url, JSONObject param) {
        long startTime = System.currentTimeMillis();
        LOGGER.info("\n 请求地址 = {} || 请求参数 = {}",
                url, JSON.toJSONString(param));
        ResponseEntity<String> responseEntity = get().getForEntity(url, String.class, param);
        if (null != responseEntity && HttpStatus.OK.equals(responseEntity.getStatusCode()) && responseEntity.hasBody()) {
            String result = responseEntity.getBody();
            if (StringUtils.isEmpty(result)) {
                return null;
            }
            LOGGER.info("请求地址 = {} || 请求参数 = {} || 响应时长 = {} || 响应结果 = {}",
                    url, JSON.toJSONString(param), System.currentTimeMillis() - startTime ,responseEntity.getBody());
            VendorResult<T> tVendorResult = JSON.parseObject(result, new TypeReference<VendorResult<T>>() {
            });
            return  tVendorResult;
        } else {
            LOGGER.error("请求地址 = {} || 请求参数 = {} || 响应时长 = {} || 响应结果 = {}",
                    url, JSON.toJSONString(param), System.currentTimeMillis() - startTime ,responseEntity.getBody());
            throw new RuntimeException("调用接口 = " + url + " || " +
                    "失败状态 = " + responseEntity.getStatusCode() + " || " +
                    "响应 = " + responseEntity.getBody());
        }
    }


    /**
     * @description: 发送POST请求
     * @param url 请求URL
     * @param param 请求参数
     * @return com.mapabc.signal.common.component.VendorResult<T> 返回结果
     * @author yinguijin
     * @date 2019/4/19 11:47
     */
    public static <T> VendorResult<T> doPost(String url, JSONObject param) {
        long startTime = System.currentTimeMillis();
        LOGGER.info("\n 请求地址 = {} || 请求参数 = {}",
                url, JSON.toJSONString(param));
        ResponseEntity<String> responseEntity = get().postForEntity(url, param, String.class);
        if (null != responseEntity && HttpStatus.OK.equals(responseEntity.getStatusCode()) && responseEntity.hasBody()) {
            String result = responseEntity.getBody();
            if (StringUtils.isEmpty(result)) {
                return null;
            }
            LOGGER.info("请求地址 = {} || 请求参数 = {} || 响应时长 = {} || 响应结果 = {}",
                    url, JSON.toJSONString(param), System.currentTimeMillis() - startTime ,responseEntity.getBody());
            VendorResult<T> tVendorResult = JSON.parseObject(result, new TypeReference<VendorResult<T>>() {
            });
            return  tVendorResult;
        } else {
            LOGGER.error("请求地址 = {} || 请求参数 = {} || 响应时长 = {} || 响应结果 = {}",
                    url, JSON.toJSONString(param), System.currentTimeMillis() - startTime ,responseEntity.getBody());
            throw new RuntimeException("调用接口 = " + url + " || " +
                    "失败状态 = " + responseEntity.getStatusCode() + " || " +
                    "响应 = " + responseEntity.getBody());
        }
    }

    /**
     * @description: 发送请求
     * @param url 请求地址
     * @param headers 请求头
     * @param method 请求类型 POST、GET
     * @param param 参数
     * @return com.mapabc.signal.common.component.VendorResult<T> 返回结果
     * @author yinguijin
     * @date 2019/4/19 12:39
     */
    public static <T> VendorResult<T> doExecute(String url, Map<String, String> headers, HttpMethod method, JSONObject param) {
        long startTime = System.currentTimeMillis();
        LOGGER.info("\n 请求地址 = {} || 请求方式 = {} || 请求头 = {} || 请求参数 = {}",
                url, method.name(), JSON.toJSONString(headers), JSON.toJSONString(param));
        //封装httpEntity
        HttpEntity httpEntity = getHttpEntity(headers);
        //发送请求
        ResponseEntity<String> responseEntity = get().exchange(url, method, httpEntity, String.class, param);
        if (null != responseEntity && HttpStatus.OK.equals(responseEntity.getStatusCode()) && responseEntity.hasBody()) {
            String result = responseEntity.getBody();
            if (StringUtils.isEmpty(result)) {
                return null;
            }
            LOGGER.info("请求地址 = {} || 请求参数 = {} || 响应时长 = {} || 响应结果 = {}",
                    url, JSON.toJSONString(param), System.currentTimeMillis() - startTime ,responseEntity.getBody());
            VendorResult<T> tVendorResult = JSON.parseObject(result, new TypeReference<VendorResult<T>>() {
            });
            return  tVendorResult;
        } else {
            LOGGER.error("请求地址 = {} || 请求参数 = {} || 响应时长 = {} || 响应结果 = {}",
                    url, JSON.toJSONString(param), System.currentTimeMillis() - startTime ,responseEntity.getBody());
            throw new RuntimeException("调用接口 = " + url + " || " +
                    "失败状态 = " + responseEntity.getStatusCode() + " || " +
                    "响应 = " + responseEntity.getBody());
        }
    }


    private static HttpEntity getHttpEntity(Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        Iterator iterator = headers.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry)iterator.next();
            String key = (String)entry.getKey();
            if (!httpHeaders.containsKey(key)) {
                httpHeaders.add(key, (String)entry.getValue());
            }
        }
        return new HttpEntity(httpHeaders);
    }

    public static void main(String[] args) {
//        String result = "{\"systemtype\":\"UTC\",\"sourcetype\":1,\"datacount\":0,\"updatetime\":\"2019-04-19 12:50:12\",\"datacontent\":[{\"signalid\":\"10001\",\"signaltype\":\"1\",\"crossname\":\"1\",\"crosstype\":\"\",\"longitude\":113.456,\"latitude\":39.345,\"directions\":[{\"detector\":\"正前方\",\"roads\":[{\"roadid\":\"1\",\"roadname\":\"黄埔大道\"}]}]}]}";
        String result = "{\"dataCount\":0,\"systemType\":\"UTC\",\"sourceType\":1,\"dataContent\":[{\"crossType\":\"\",\"directions\":[{\"roads\":[{\"roadId\":\"1\",\"roadName\":\"黄埔大道\"}],\"detector\":\"正前方\"}],\"signalType\":\"1\",\"signalId\":\"10001\",\"latitude\":39.345,\"crossName\":\"1\",\"longitude\":113.456}],\"updateTime\":\"2019-04-19 12:50:12\"}";
        System.out.println(JSONObject.parseObject(result));
        VendorResult<Crossing> tVendorResult = JSON.parseObject(result, new TypeReference<VendorResult<Crossing>>() {
        });

        System.out.println(JSON.toJSON(tVendorResult));
    }
}
