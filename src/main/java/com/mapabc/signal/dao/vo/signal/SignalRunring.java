package com.mapabc.signal.dao.vo.signal;

import lombok.Data;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [信号机运行环数实体]
 * Created on 2019/4/24 11:31
 */
@Data
public class SignalRunring {
    //环号
    private String runringNo;
    //当前相位编号
    private String phaseId;
    //相位时长
    private Integer phaseLen;
    //相位剩余时长
    private String phaseLeft;
    //当前步号
    private String stepNo;
    //当前步长
    private Integer stepLen;
    //当前步剩余时间
    private Integer stepLeft;
    //下一相位id
    private String nextPhaseId;
}
