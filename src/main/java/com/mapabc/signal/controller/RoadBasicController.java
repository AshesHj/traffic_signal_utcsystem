package com.mapabc.signal.controller;

import com.mapabc.signal.common.annotation.AspectLog;
import com.mapabc.signal.common.enums.BaseEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: [信号控制系统所有控制路口API]
 * @author yinguijin
 * @version 1.0
 * Created on 2019/4/17 11:49
 */
@Api(value = "控制路口API接口", description = "控制路口API接口", tags = {"控制路口"})
@RestController
@RequestMapping("/controllers")
public class RoadBasicController extends BaseController {


    @AspectLog(description = "获取所有控制路口基本信息", operationType = BaseEnum.OperationTypeEnum.QUERY)
    @GetMapping
    @ApiOperation(value = "获取所有控制路口基本信息", notes = "获取所有控制路口基本信息")
    public ResponseEntity queryRoadBasic() {
        try {
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            exceptionService.handle("获取所有控制路口基本信息异常", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("获取所有控制路口基本信息异常");
        }
    }
}
