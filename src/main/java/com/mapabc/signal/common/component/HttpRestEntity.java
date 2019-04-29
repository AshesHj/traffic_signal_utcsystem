package com.mapabc.signal.common.component;

import com.mapabc.signal.common.constant.Const;
import com.mapabc.signal.common.enums.BaseEnum;
import com.mapabc.signal.common.exception.WarnException;
import com.mapabc.signal.common.util.StringUtils;
import com.mapabc.signal.dao.model.TBaseVendorMethod;
import lombok.Data;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [封装调用外部接口URL和请求头]
 * Created on 2019/4/24 20:53
 */
@Data
public class HttpRestEntity {

    //请求地址
    private String url;

    //请求方式
    private HttpMethod httpMethod;

    //请求头
    private Map<String, String> headers;

    public HttpRestEntity() {

    }

    /**
     * @description: 根据不同厂商获取接口URL地址
     * @param sourceType 厂商信息
     * @param methodCode 厂商下具体接口
     * @return java.lang.String URL地址
     * @author yinguijin
     * @date 2019/4/24 15:23
     */
    public HttpRestEntity(String sourceType, String methodCode) {
        // TODO 待实现具体功能-根据厂商和方法获取请求地址
        TBaseVendorMethod method = Const.urlMap.get(sourceType + Const.SEPARATOR_UNDER_LINE + methodCode);
        if (StringUtils.isEmpty(method)) {
            throw new WarnException("数据库没有该接口信息，请在t_base_vendor_method表中维护该接口信息！");
        }
        url = method.getMethodUrl();
        httpMethod = HttpMethod.valueOf(method.getHttpMethod());
        // TODO 不同的厂商可以定义不同的请求头
        if (sourceType.equals(BaseEnum.VendorTypeEnum.QS.getNick())) {
            headers = handlerHeader();
        } else {
            headers = handlerHeader();
        }
    }

    /**
     * Discription:[封装请求头]
     * @author: yinguijin
     * @date 2019/4/24 15:23
     */
    private Map<String, String> handlerHeader() {
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type","application/json; charset=UTF-8");
        header.put("Accept", MediaType.APPLICATION_JSON.toString()) ;
        return header;
    }
}
