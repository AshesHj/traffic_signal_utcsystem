package com.mapabc.signal.dao.vo.phase;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [行人方向]
 * Created on 2019/4/19 17:20
 */
@Data
@ApiModel(value = "Pdirection", description = "行人方向")
public class Pdirection {

    //方向
    private Integer direction;

    private Integer second;

    private Integer inoutType;

}
