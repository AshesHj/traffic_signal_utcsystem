package com.mapabc.signal.service.impl;

import com.mapabc.signal.common.constant.Const;
import com.mapabc.signal.common.util.ListUtil;
import com.mapabc.signal.dao.mapper.TBaseVendorMethodMapper;
import com.mapabc.signal.dao.model.TBaseVendorMethod;
import com.mapabc.signal.service.TBaseVendorMethodService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @Description: [厂商接口信息表service实现]</p>
* @author yinguijin
* @version 1.0
* Created on 2019年04月25日
*/
@Service
public class TBaseVendorMethodServiceImpl extends BaseServiceImpl<TBaseVendorMethod, TBaseVendorMethodMapper> implements TBaseVendorMethodService {

    /**
     * @description: 项目启动加载厂商url到内存
     * @author yinguijin
     * @date 2019/4/25 16:36
     */
    public void initLoadVendor() {
        TBaseVendorMethod entity = new TBaseVendorMethod();
        entity.setIsDelete(Const.IS_DELETE_NO);
        List<TBaseVendorMethod> vendorMethods = select(entity);
        if (ListUtil.isEmpty(vendorMethods)) {
            return;
        }
        //缓存url到map
        for (TBaseVendorMethod method : vendorMethods) {
            String key = method.getVendorNick() + Const.SEPARATOR_UNDER_LINE + method.getMethodCode();
            Const.urlMap.put(key, method);
        }
    }
}
