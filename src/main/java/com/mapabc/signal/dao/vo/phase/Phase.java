package com.mapabc.signal.dao.vo.phase;

import com.mapabc.signal.dao.vo.timeplan.Step;
import lombok.Data;

import java.util.List;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [相位数据实体]
 * Created on 2019/4/19 16:56
 */
@Data
public class Phase {

    //相位编号
    private String phaseId;
    //相位名称
    private String phaseName;
    //相位描述
    private String phaseDesc;
    //相位顺序号
    private Integer phaseOrderId;
    //相位时长（绿信比）
    private Integer phaseTime;
    //最大绿时间
    private Integer maxgreenTime;
    //最小绿时间
    private Integer mingreenTime;
    //黄灯时间
    private Integer yellowTime;
    //红灯时间
    private Integer redTime;
    //所有跟随相位，相位编号
    private String[] followPhases;
    //步数据对象列表
    private List<Step> steps;
    //机动车方向
    private List<Vdirection> vdirections;
    //行人方向
    private List<Pdirection> pdirections;
}
