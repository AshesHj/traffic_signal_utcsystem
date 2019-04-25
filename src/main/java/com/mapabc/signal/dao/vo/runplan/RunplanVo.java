package com.mapabc.signal.dao.vo.runplan;

import com.alibaba.fastjson.JSONArray;
import com.mapabc.signal.common.component.BaseSignal;
import lombok.Data;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [描述该类概要功能介绍]
 * Created on 2019/4/22 16:41
 */
@Data
public class RunplanVo extends BaseSignal {

    //星期
    private JSONArray weeks;

    //特殊日期
    private JSONArray specialdays;
}
