package com.mapabc.signal.controller;

import com.mapabc.signal.common.annotation.AspectLog;
import com.mapabc.signal.common.component.BaseSignal;
import com.mapabc.signal.common.component.ParamEntity;
import com.mapabc.signal.common.component.VendorResult;
import com.mapabc.signal.common.constant.Const;
import com.mapabc.signal.common.enums.BaseEnum;
import com.mapabc.signal.common.exception.WarnException;
import com.mapabc.signal.dao.vo.signal.SignalInfoVo;
import com.mapabc.signal.dao.vo.signalalarms.SignalAlarmsVo;
import com.mapabc.signal.dao.vo.signalrunstatus.SignalRunStatusVo;
import com.mapabc.signal.dao.vo.signalvolume.SignalVolumeVo;
import com.mapabc.signal.service.SignalRunService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [信号机实时运行数据获取接口]
 * Created on 2019/4/25 20:21
 */
@Api(value = "信号机实时运行数据接口API", description = "信号机实时运行数据接口API", tags = {"动态数据获取接口"})
@RestController
@RequestMapping("/")
public class SignalRunController extends BaseController {

    @Resource
    private SignalRunService signalRunService;

    @AspectLog(description = "获取路口信号机的实时运行状态数据", operationType = BaseEnum.OperationTypeEnum.QUERY)
    @GetMapping("/runstate")
    @ApiOperation(value = "信号运行状态数据", notes = "获取路口信号机的实时运行状态数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceType", value = "厂家简称 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String", defaultValue = "QS"),
            @ApiImplicitParam(name = "signalId", value = "信号机编号", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "signalType", value = "信号机类型 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String")
    })
    public ResponseEntity queryRunstate (HttpServletRequest request, @RequestParam String sourceType, @ApiIgnore BaseSignal signal) {
        try {
            //设置参数
            ParamEntity<BaseSignal> param = new ParamEntity<>();
            param.setSourceType(sourceType);
            param.setUpdateTime(new Date());
            param.setDataContent(signal);
            param.setSystemType(Const.SYSTEM_TYPE);
            //调用接口
            VendorResult<SignalRunStatusVo> result = signalRunService.queryRunstate(param);
            return ResponseEntity.ok(result.getDataContent());
        } catch (WarnException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            exceptionService.handle("获取路口信号机的实时运行状态数据异常", e, request);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("获取路口信号机的实时运行状态数据异常");
        }
    }


    @AspectLog(description = "获取路口信号机的实时灯态数据", operationType = BaseEnum.OperationTypeEnum.QUERY)
    @GetMapping("/alarms")
    @ApiOperation(value = "信号机告警数据", notes = "获取路口信号机的实时灯态数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceType", value = "厂家简称 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String", defaultValue = "QS"),
            @ApiImplicitParam(name = "signalId", value = "信号机编号", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "signalType", value = "信号机类型 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String")
    })
    public ResponseEntity queryAlarms (HttpServletRequest request, @RequestParam String sourceType, @ApiIgnore BaseSignal signal) {
        try {
            //设置参数
            ParamEntity<BaseSignal> param = new ParamEntity<>();
            param.setSourceType(sourceType);
            param.setUpdateTime(new Date());
            param.setDataContent(signal);
            param.setSystemType(Const.SYSTEM_TYPE);
            //调用接口
            VendorResult<SignalAlarmsVo> result = signalRunService.queryAlarms(param);
            return ResponseEntity.ok(result.getDataContent());
        } catch (WarnException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            exceptionService.handle("获取路口信号机的实时灯态数据异常", e, request);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("获取路口信号机的实时灯态数据异常");
        }
    }


    @AspectLog(description = "获取路口信号机的实时灯态数据", operationType = BaseEnum.OperationTypeEnum.QUERY)
    @GetMapping("/signal")
    @ApiOperation(value = "信号灯态数据", notes = "获取路口信号机的实时灯态数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceType", value = "厂家简称 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String", defaultValue = "QS"),
            @ApiImplicitParam(name = "signalId", value = "信号机编号", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "signalType", value = "信号机类型 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String")
    })
    public ResponseEntity querySignalInfo (HttpServletRequest request, @RequestParam String sourceType, @ApiIgnore BaseSignal signal) {
        try {
            //设置参数
            ParamEntity<BaseSignal> param = new ParamEntity<>();
            param.setSourceType(sourceType);
            param.setUpdateTime(new Date());
            param.setDataContent(signal);
            param.setSystemType(Const.SYSTEM_TYPE);
            //调用接口
            VendorResult<SignalInfoVo> result = signalRunService.querySignalInfo(param);
            return ResponseEntity.ok(result.getDataContent());
        } catch (WarnException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            exceptionService.handle("获取路口信号机的实时灯态数据异常", e, request);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("获取路口信号机的实时灯态数据异常");
        }
    }


    @AspectLog(description = "获取路口信号机的实时流量数据", operationType = BaseEnum.OperationTypeEnum.QUERY)
    @GetMapping("/volume")
    @ApiOperation(value = "信号流量数据", notes = "获取路口信号机的实时流量数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceType", value = "厂家简称 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String", defaultValue = "QS"),
            @ApiImplicitParam(name = "signalId", value = "信号机编号", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "signalType", value = "信号机类型 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String")
    })
    public ResponseEntity querySignalVolume (HttpServletRequest request, @RequestParam String sourceType, @ApiIgnore BaseSignal signal) {
        try {
            //设置参数
            ParamEntity<BaseSignal> param = new ParamEntity<>();
            param.setSourceType(sourceType);
            param.setUpdateTime(new Date());
            param.setDataContent(signal);
            param.setSystemType(Const.SYSTEM_TYPE);
            //调用接口
            VendorResult<SignalVolumeVo> result = signalRunService.querySignalVolume(param);
            return ResponseEntity.ok(result.getDataContent());
        } catch (WarnException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            exceptionService.handle("获取路口信号机的实时流量数据异常", e, request);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("获取路口信号机的实时流量数据异常");
        }
    }
}
