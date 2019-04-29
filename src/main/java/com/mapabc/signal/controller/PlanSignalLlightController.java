package com.mapabc.signal.controller;

import com.mapabc.signal.common.annotation.AspectLog;
import com.mapabc.signal.common.component.Result;
import com.mapabc.signal.common.enums.BaseEnum;
import com.mapabc.signal.common.exception.WarnException;
import com.mapabc.signal.service.PlanSignalLlightService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [控制指令-全红/黄灯/关灯方案下发API接口]
 * Created on 2019/4/26 11:45
 */
@Api(value = "全红/黄灯/关灯方案下发API接口", description = "全红/黄灯/关灯方案下发API接口", tags = {"控制指令接口"})
@RestController
@RequestMapping("/")
public class PlanSignalLlightController extends BaseController {

    @Resource
    private PlanSignalLlightService planSignalLlightService;

    @AspectLog(description = "全红控制-->设置当前路口信号机是否要执行全红", operationType = BaseEnum.OperationTypeEnum.UPDATE)
    @PostMapping("/{signalId}/allred")
    @ApiOperation(value = "全红控制", notes = "设置当前路口信号机是否要执行全红")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceType", value = "厂家简称 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String", defaultValue = "QS"),
            @ApiImplicitParam(name = "signalType", value = "信号机类型 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "command", value = "1 全红控制 0 取消全红", paramType = "query", required = true, dataType = "int")
    })
    public ResponseEntity updateAllRed(HttpServletRequest request,
                                     @PathVariable String signalId,
                                     @RequestParam String sourceType, @RequestParam String signalType, @RequestParam int command) {
        try {
            Result result = planSignalLlightService.updateAllRed(signalId, sourceType, signalType, command);
            if (result.isSuccess()) {
                return ResponseEntity.ok("success");
            }
            return ResponseEntity.status(result.getStatus()).body(result.getMsg());
        } catch (WarnException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            exceptionService.handle("全红控制-->设置当前路口信号机是否要执行全红异常", e, request);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("设置当前路口信号机是否要执行全红异常");
        }
    }


    @AspectLog(description = "黄闪控制-->设置当前路口信号机是否要执行黄闪", operationType = BaseEnum.OperationTypeEnum.UPDATE)
    @PostMapping("/{signalId}/yellowflash")
    @ApiOperation(value = "黄闪控制", notes = "设置当前路口信号机是否要执行黄闪")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceType", value = "厂家简称 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String", defaultValue = "QS"),
            @ApiImplicitParam(name = "signalType", value = "信号机类型 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "command", value = "1 黄闪控制 0 取消黄闪", paramType = "query", required = true, dataType = "int")
    })
    public ResponseEntity updateYellowFlash(HttpServletRequest request,
                                       @PathVariable String signalId,
                                       @RequestParam String sourceType, @RequestParam String signalType, @RequestParam int command) {
        try {
            Result result = planSignalLlightService.updateYellowFlash(signalId, sourceType, signalType, command);
            if (result.isSuccess()) {
                return ResponseEntity.ok("success");
            }
            return ResponseEntity.status(result.getStatus()).body(result.getMsg());
        } catch (WarnException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            exceptionService.handle("黄闪控制-->设置当前路口信号机是否要执行黄闪异常", e, request);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("设置当前路口信号机是否要执行黄闪异常");
        }
    }


    @AspectLog(description = "关灯控制-->设置当前路口信号机是否要执行开灯或关灯", operationType = BaseEnum.OperationTypeEnum.UPDATE)
    @PostMapping("/{signalId}/off")
    @ApiOperation(value = "关灯控制", notes = "设置当前路口信号机是否要执行开灯或关灯")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceType", value = "厂家简称 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String", defaultValue = "QS"),
            @ApiImplicitParam(name = "signalType", value = "信号机类型 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "command", value = "1 开灯 0 关灯", paramType = "query", required = true, dataType = "int")
    })
    public ResponseEntity updateOff(HttpServletRequest request,
                                            @PathVariable String signalId,
                                            @RequestParam String sourceType, @RequestParam String signalType, @RequestParam int command) {
        try {
            Result result = planSignalLlightService.updateOff(signalId, sourceType, signalType, command);
            if (result.isSuccess()) {
                return ResponseEntity.ok("success");
            }
            return ResponseEntity.status(result.getStatus()).body(result.getMsg());
        } catch (WarnException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            exceptionService.handle("关灯控制-->设置当前路口信号机是否要执行开灯或关灯异常", e, request);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("设置当前路口信号机是否要执行开灯或关灯异常");
        }
    }
}
