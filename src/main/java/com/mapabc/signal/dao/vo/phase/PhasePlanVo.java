package com.mapabc.signal.dao.vo.phase;

import com.mapabc.signal.common.component.BaseSignal;
import lombok.Data;

import java.util.List;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [相位方案Vo实体]
 * Created on 2019/4/19 17:28
 */
@Data
public class PhasePlanVo extends BaseSignal {

    //相位方案集合
    private List<PhasePlan> phasePlans;
}
