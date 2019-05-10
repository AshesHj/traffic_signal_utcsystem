package com.mapabc.signal.service.impl;

import com.mapabc.signal.common.component.BaseSignal;
import com.mapabc.signal.common.component.ParamEntity;
import com.mapabc.signal.common.enums.BaseEnum;
import com.mapabc.signal.common.util.ListUtil;
import com.mapabc.signal.dao.mapper.TCrossLightsMapper;
import com.mapabc.signal.dao.model.TCrossLights;
import com.mapabc.signal.dao.model.TelesemeList;
import com.mapabc.signal.service.TCrossLightsService;
import com.mapabc.signal.service.TelesemeListService;
import com.mapabc.signal.service.qs.QsGetSignalMethodService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @Description: [路口灯组表service实现]</p>
* @author yinguijin
* @version 1.0
* Created on 2019年04月30日
*/
@Service
public class TCrossLightsServiceImpl extends BaseServiceImpl<TCrossLights, TCrossLightsMapper> implements TCrossLightsService {

    @Value("${signalamp.vendor.qs.switch}")
    private Boolean adSwitch;

    //青松接口
    @Resource
    private QsGetSignalMethodService qsGetSignalMethodService;

    //信号灯接口
    @Resource
    private TelesemeListService telesemeListService;

    /**
     * @param param
     * @description: 保存信号机灯组信息列表
     * @author yinguijin
     * @date 2019/4/30 11:09
     */
    @Override
    public void initCrossLights(ParamEntity<BaseSignal> param) {
        List<TCrossLights> crossLights = new ArrayList<>();
        if (BaseEnum.VendorTypeEnum.QS.getNick().equalsIgnoreCase(param.getSourceType()) && adSwitch) {
            List<TelesemeList> telesemeLists = telesemeListService.selectByTeleseme(param.getDataContent());
            //青松接口
            crossLights = qsGetSignalMethodService.queryQsCrossLights(telesemeLists);
        } else {

        }
        if (ListUtil.isEmpty(crossLights)) {
            return;
        }
        insertList(crossLights);
    }
}
