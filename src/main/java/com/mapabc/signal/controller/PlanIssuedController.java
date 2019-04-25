package com.mapabc.signal.controller;

import com.mapabc.signal.common.annotation.AspectLog;
import com.mapabc.signal.common.enums.BaseEnum;
import com.mapabc.signal.common.exception.WarnException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [控制指令-方案下发接口]
 * Created on 2019/4/25 17:07
 */
@Api(value = "方案下发API接口", description = "方案下发API接口", tags = {"控制指令接口"})
@RestController
@RequestMapping("/")
public class PlanIssuedController extends BaseController {


    @AspectLog(description = "设置当前路口信号机的方案优化", operationType = BaseEnum.OperationTypeEnum.UPDATE)
    @PostMapping("/{signalid}/timeplan")
    @ApiOperation(value = "当前方案优化调整", notes = "设置当前路口信号机的方案优化")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceType", value = "厂家简称 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String", defaultValue = "QS")
    })
    public ResponseEntity timeplan(HttpServletRequest request, @RequestParam String sourceType, @PathVariable String signalid) {
        try {
            return ResponseEntity.ok("sucess");
        } catch (WarnException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            exceptionService.handle("设置当前路口信号机的方案优化异常", e, request);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("设置当前路口信号机的方案优化异常");
        }
    }
}
