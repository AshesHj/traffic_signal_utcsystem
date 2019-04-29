package com.mapabc.signal.dao.vo.phase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description: [相位锁定实体类]
 * @author yinguijin
 * @version 1.0
 * Created on 2019/4/28 11:53
 */
@Data
@ApiModel(value = "PhaseLock", description = "相位锁定实体类")
public class PhaseLock {

    //相位编号
    @ApiModelProperty(value = "相位编号")
    private String phaseId;

    //1 锁定;0 取消
    @ApiModelProperty(value = "1 锁定;0 取消")
    private Integer command;

    //相位锁定时间, 999 永久锁定，必须手动取消。0~999 过了这个这个时间，自动取消锁定。
    @ApiModelProperty(value = "相位锁定时间,999 永久锁定,必须手动取消;0~999,自动取消")
    private Integer lockTime;
}
