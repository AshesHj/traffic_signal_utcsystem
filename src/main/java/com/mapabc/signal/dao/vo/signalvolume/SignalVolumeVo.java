package com.mapabc.signal.dao.vo.signalvolume;

import com.mapabc.signal.common.component.BaseSignal;
import com.mapabc.signal.dao.vo.detector.Detector;
import lombok.Data;

import java.util.List;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [信号实时流量实体]
 * Created on 2019/4/24 11:49
 */
@Data
public class SignalVolumeVo extends BaseSignal {

    //时间粒度，单位：秒
    private Integer granula;

    //日期时间：yyyy-mm-dd hh24:mi:00
    private String datetime;

    //探测器对象
    private List<Detector> detectors;

}
