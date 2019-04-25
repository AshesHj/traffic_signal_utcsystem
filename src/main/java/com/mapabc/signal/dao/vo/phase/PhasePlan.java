package com.mapabc.signal.dao.vo.phase;

import lombok.Data;

import java.util.List;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [相位方案实体]
 * Created on 2019/4/19 16:53
 */
@Data
public class PhasePlan {

    //相位方案编号
   private String phasePlanId;

   //相位方案描述
    private String pahsePlanDesc;

    //相位数据列表
    private List<Phase> phases;

}
