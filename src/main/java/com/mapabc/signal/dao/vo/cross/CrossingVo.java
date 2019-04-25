package com.mapabc.signal.dao.vo.cross;

import com.mapabc.signal.common.component.BaseSignal;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [路口信息实体]
 * Created on 2019/4/18 18:59
 */
@Data
public class CrossingVo extends BaseSignal {

    //路口名称
    private String crossName;
    //路口类型，2行人过街 3 丁字口 4 十字口 5 五岔口 6 六岔口
    private String crossType;
    //经度
    private BigDecimal longitude;
    //纬度
    private BigDecimal latitude;
    //路口进口方向对象列表
    private List<Direction> directions;

}
