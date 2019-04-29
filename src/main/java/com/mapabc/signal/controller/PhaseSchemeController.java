package com.mapabc.signal.controller;

import com.mapabc.signal.common.annotation.AspectLog;
import com.mapabc.signal.common.component.ParamEntity;
import com.mapabc.signal.common.component.Result;
import com.mapabc.signal.common.constant.Const;
import com.mapabc.signal.common.enums.BaseEnum;
import com.mapabc.signal.common.exception.WarnException;
import com.mapabc.signal.dao.vo.phase.PhaseLockVo;
import com.mapabc.signal.dao.vo.timeplan.TimePlanVo;
import com.mapabc.signal.service.PhaseSchemeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [控制指令-相位锁定/步进/当前方案优化API接口]
 * Created on 2019/4/25 17:07
 */
@Api(value = "相位锁定/步进/当前方案优化API接口", description = "相位锁定/步进/当前方案优化API接口", tags = {"控制指令接口"})
@RestController
@RequestMapping("/")
public class PhaseSchemeController extends BaseController {

    @Resource
    private PhaseSchemeService phaseSchemeService;

    @AspectLog(description = "设置当前路口信号机的方案优化", operationType = BaseEnum.OperationTypeEnum.UPDATE)
    @PostMapping("/{signalId}/timeplan")
    @ApiOperation(value = "当前方案优化调整", notes = "设置当前路口信号机的方案优化")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceType", value = "厂家简称 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String", defaultValue = "QS")
    })
    public ResponseEntity updateTimeplan(HttpServletRequest request,
                                         @PathVariable String signalId,
                                         @RequestParam String sourceType, @RequestBody TimePlanVo timePlanVo) {
        try {
            //设置参数
            ParamEntity<TimePlanVo> param = new ParamEntity<>();
            param.setSourceType(sourceType);
            param.setUpdateTime(new Date());
            param.setSystemType(Const.SYSTEM_TYPE);
            timePlanVo.setSignalId(signalId);
            param.setDataContent(timePlanVo);
            Result result = phaseSchemeService.updateTimePlan(param);
            if (result.isSuccess()) {
                return ResponseEntity.ok("success");
            }
            return ResponseEntity.status(result.getStatus()).body(result.getMsg());
        } catch (WarnException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            exceptionService.handle("设置当前路口信号机的方案优化异常", e, request);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("设置当前路口信号机的方案优化异常");
        }
    }


    @AspectLog(description = "相位锁定控制", operationType = BaseEnum.OperationTypeEnum.UPDATE)
    @PostMapping("/{signalId}/lockphase")
    @ApiOperation(value = "相位锁定控制", notes = "设置当前路口信号机的一个或多个相位放行")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceType", value = "厂家简称 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String", defaultValue = "QS"),
    })
    public ResponseEntity updateLockPhase(HttpServletRequest request,
                                          @PathVariable String signalId,
                                          @RequestParam String sourceType, @RequestBody PhaseLockVo phaseLockVo) {
        try {
            //设置参数
            ParamEntity<PhaseLockVo> param = new ParamEntity<>();
            param.setSourceType(sourceType);
            param.setUpdateTime(new Date());
            param.setSystemType(Const.SYSTEM_TYPE);
            phaseLockVo.setSignalId(signalId);
            param.setDataContent(phaseLockVo);
            Result result = phaseSchemeService.updateLockPhase(param);
            if (result.isSuccess()) {
                return ResponseEntity.ok("success");
            }
            return ResponseEntity.status(result.getStatus()).body(result.getMsg());
        } catch (WarnException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            exceptionService.handle("相位锁定控制异常", e, request);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("相位锁定控制异常");
        }
    }


    @AspectLog(description = "相位步进控制", operationType = BaseEnum.OperationTypeEnum.UPDATE)
    @PostMapping("/{signalId}/step")
    @ApiOperation(value = "相位步进控制", notes = "设置当前路口信号机由一个相位向下一个相位或后续某个相位进行平滑过渡")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceType", value = "厂家简称 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String", defaultValue = "QS"),
            @ApiImplicitParam(name = "signalType", value = "信号机类型 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "command", value = "1 开始步进 0 取消步进", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "stepNum", value = "0 顺序步进 n 跳过n个相位", paramType = "query", required = true, dataType = "int"),
    })
    public ResponseEntity updateStep(HttpServletRequest request,
                                     @PathVariable String signalId,
                                     @RequestParam String sourceType, @RequestParam String signalType,
                                     @RequestParam int command, @RequestParam int stepNum) {
        try {
            Result result = phaseSchemeService.updateStep(signalId, sourceType, signalType, command, stepNum);
            if (result.isSuccess()) {
                return ResponseEntity.ok("success");
            }
            return ResponseEntity.status(result.getStatus()).body(result.getMsg());
        } catch (WarnException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            exceptionService.handle("相位步进控制异常", e, request);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("相位步进控制异常");
        }
    }


    @AspectLog(description = "设置当前路口信号机执行某方案", operationType = BaseEnum.OperationTypeEnum.UPDATE)
    @PostMapping("/{signalId}/forceplan")
    @ApiOperation(value = "强制方案控制", notes = "设置当前路口信号机执行某方案")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceType", value = "厂家简称 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String", defaultValue = "QS"),
            @ApiImplicitParam(name = "signalType", value = "信号机类型 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "timePlanId", value = "配时方案编号", paramType = "query", required = true, dataType = "String"),
    })
    public ResponseEntity updateForcePlan(HttpServletRequest request,
                                     @PathVariable String signalId,
                                     @RequestParam String sourceType, @RequestParam String signalType, @RequestParam String timePlanId) {
        try {
            Result result = phaseSchemeService.updateForcePlan(signalId, sourceType, signalType, timePlanId);
            if (result.isSuccess()) {
                return ResponseEntity.ok("success");
            }
            return ResponseEntity.status(result.getStatus()).body(result.getMsg());
        } catch (WarnException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            exceptionService.handle("设置当前路口信号机执行某方案异常", e, request);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("设置当前路口信号机执行某方案异常");
        }
    }
}
