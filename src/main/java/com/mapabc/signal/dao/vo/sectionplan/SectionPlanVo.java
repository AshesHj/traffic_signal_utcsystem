package com.mapabc.signal.dao.vo.sectionplan;

import com.mapabc.signal.common.component.BaseSignal;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [路口时段方案Vo]
 * Created on 2019/4/22 16:17
 */
@Data
public class SectionPlanVo extends BaseSignal {

    //日时段方案数据对象列表
    private List<SectionPlan> sectionPlans;

}
