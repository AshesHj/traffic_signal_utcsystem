package com.mapabc.signal.dao.model.detector;

import com.mapabc.signal.common.component.BaseSignal;
import lombok.Data;

import java.util.List;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [检测器对外厂家接口返回值]
 * Created on 2019/4/19 14:32
 */
@Data
public class DetectorVo extends BaseSignal {

    //检测器对象列表
    private List<Detector> detectors;
}
