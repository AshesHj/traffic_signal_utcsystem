package com.mapabc.signal.controller;

import com.mapabc.signal.common.annotation.AspectLog;
import com.mapabc.signal.common.component.BaseSignal;
import com.mapabc.signal.common.component.ParamEntity;
import com.mapabc.signal.common.component.VendorResult;
import com.mapabc.signal.common.constant.Const;
import com.mapabc.signal.common.enums.BaseEnum;
import com.mapabc.signal.common.exception.WarnException;
import com.mapabc.signal.dao.vo.cross.CrossingVo;
import com.mapabc.signal.dao.vo.detector.DetectorVo;
import com.mapabc.signal.dao.vo.phase.PhasePlanVo;
import com.mapabc.signal.dao.vo.runplan.RunplanVo;
import com.mapabc.signal.dao.vo.sectionplan.SectionPlanVo;
import com.mapabc.signal.dao.vo.timeplan.TimePlanVo;
import com.mapabc.signal.service.CrossingService;
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
 * @Description: [信号控制系统所有控制路口API]
 * @author yinguijin
 * @version 1.0
 * Created on 2019/4/17 11:49
 */
@Api(value = "静态数据接口API", description = "静态数据接口API", tags = {"静态数据获取接口"})
@RestController
@RequestMapping("/")
public class RoadBasicController extends BaseController {

    @Resource
    private CrossingService crossingService;

    @AspectLog(description = "获取所有控制路口基本信息", operationType = BaseEnum.OperationTypeEnum.QUERY)
    @GetMapping("/controllers")
    @ApiOperation(value = "获取所有控制路口基本信息", notes = "获取所有控制路口基本信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceType", value = "厂家简称 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String", defaultValue = "QS")
    })
    public ResponseEntity queryRoadBasic(HttpServletRequest request, @ApiIgnore ParamEntity<BaseSignal> param) {
        try {
            //设置参数
            param.setUpdateTime(new Date());
            param.setSystemType(Const.SYSTEM_TYPE);
            //调用接口
            VendorResult<CrossingVo> result = crossingService.queryCrossing(param);
            return ResponseEntity.ok(result.getDataContent());
        } catch (WarnException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            exceptionService.handle("获取所有控制路口基本信息异常", e, request);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("获取所有控制路口基本信息异常");
        }
    }

    @AspectLog(description = "获取路口的线圈信息", operationType = BaseEnum.OperationTypeEnum.QUERY)
    @GetMapping("/detectors")
    @ApiOperation(value = "获取路口的线圈信息", notes = "获取路口的线圈信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceType", value = "厂家简称 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String", defaultValue = "QS"),
            @ApiImplicitParam(name = "signalId", value = "信号机编号", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "signalType", value = "信号机类型 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String")
    })
    public ResponseEntity queryDetectors(HttpServletRequest request, @RequestParam String sourceType, @ApiIgnore BaseSignal signal) {
        try {
            //设置参数
            ParamEntity<BaseSignal> param = new ParamEntity<>();
            param.setSourceType(sourceType);
            param.setUpdateTime(new Date());
            param.setDataContent(signal);
            param.setSystemType(Const.SYSTEM_TYPE);
            //调用接口
            VendorResult<DetectorVo> result = crossingService.queryDetector(param);
            return ResponseEntity.ok(result.getDataContent());
        } catch (WarnException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            exceptionService.handle("获取路口的线圈信息异常", e, request);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("获取路口的线圈信息异常");
        }
    }


    @AspectLog(description = "获取路口的相位方案信息", operationType = BaseEnum.OperationTypeEnum.QUERY)
    @GetMapping("/phaseplans")
    @ApiOperation(value = "获取路口的相位方案信息", notes = "获取路口的相位方案信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceType", value = "厂家简称 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String", defaultValue = "QS"),
            @ApiImplicitParam(name = "signalId", value = "信号机编号", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "signalType", value = "信号机类型 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String")
    })
    public ResponseEntity queryPhaseplan(HttpServletRequest request, @RequestParam String sourceType, @ApiIgnore BaseSignal signal) {
        try {
            //设置参数
            ParamEntity<BaseSignal> param = new ParamEntity<>();
            param.setSourceType(sourceType);
            param.setUpdateTime(new Date());
            param.setDataContent(signal);
            param.setSystemType(Const.SYSTEM_TYPE);
            //调用接口
            VendorResult<PhasePlanVo> result = crossingService.queryPhasePlan(param);
            return ResponseEntity.ok(result.getDataContent());
        } catch (WarnException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            exceptionService.handle("获取路口的相位方案信息异常", e, request);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("获取路口的相位方案信息异常");
        }
    }


    @AspectLog(description = "获取路口的配时方案信息", operationType = BaseEnum.OperationTypeEnum.QUERY)
    @GetMapping("/timeplans")
    @ApiOperation(value = "获取路口的配时方案信息", notes = "获取路口的配时方案信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceType", value = "厂家简称 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String", defaultValue = "QS"),
            @ApiImplicitParam(name = "signalId", value = "信号机编号", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "signalType", value = "信号机类型 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String")
    })
    public ResponseEntity queryTimePlan(HttpServletRequest request, @RequestParam String sourceType, @ApiIgnore BaseSignal signal) {
        try {
            //设置参数
            ParamEntity<BaseSignal> param = new ParamEntity<>();
            param.setSourceType(sourceType);
            param.setUpdateTime(new Date());
            param.setDataContent(signal);
            param.setSystemType(Const.SYSTEM_TYPE);
            //调用接口
            VendorResult<TimePlanVo> result = crossingService.queryTimePlan(param);
            return ResponseEntity.ok(result.getDataContent());
        } catch (WarnException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            exceptionService.handle("获取路口的配时方案信息异常", e, request);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("获取路口的配时方案信息异常");
        }
    }


    @AspectLog(description = "获取路口的时段方案（日时段方案）", operationType = BaseEnum.OperationTypeEnum.QUERY)
    @GetMapping("/sectionplans")
    @ApiOperation(value = "获取路口的时段方案（日时段方案）", notes = "获取路口的时段方案（日时段方案）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceType", value = "厂家简称 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String", defaultValue = "QS"),
            @ApiImplicitParam(name = "signalId", value = "信号机编号", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "signalType", value = "信号机类型 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String")
    })
    public ResponseEntity querySectionPlan(HttpServletRequest request, @RequestParam String sourceType, @ApiIgnore BaseSignal signal) {
        try {
            //设置参数
            ParamEntity<BaseSignal> param = new ParamEntity<>();
            param.setSourceType(sourceType);
            param.setUpdateTime(new Date());
            param.setDataContent(signal);
            param.setSystemType(Const.SYSTEM_TYPE);
            //调用接口
            VendorResult<SectionPlanVo> result = crossingService.querySectionPlan(param);
            return ResponseEntity.ok(result.getDataContent());
        } catch (WarnException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            exceptionService.handle("获取路口的时段方案（日时段方案）异常", e, request);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("获取路口的时段方案（日时段方案）异常");
        }
    }


    @AspectLog(description = "获取路口的运行计划表", operationType = BaseEnum.OperationTypeEnum.QUERY)
    @GetMapping("/runplan")
    @ApiOperation(value = "获取路口的运行计划表", notes = "获取路口的运行计划表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceType", value = "厂家简称 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String", defaultValue = "QS"),
            @ApiImplicitParam(name = "signalId", value = "信号机编号", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "signalType", value = "信号机类型 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String")
    })
    public ResponseEntity queryRunplan(HttpServletRequest request, @RequestParam String sourceType, @ApiIgnore BaseSignal signal) {
        try {
            //设置参数
            ParamEntity<BaseSignal> param = new ParamEntity<>();
            param.setSourceType(sourceType);
            param.setUpdateTime(new Date());
            param.setDataContent(signal);
            param.setSystemType(Const.SYSTEM_TYPE);
            //调用接口
            VendorResult<RunplanVo> result = crossingService.queryRunplan(param);
            return ResponseEntity.ok(result.getDataContent());
        } catch (WarnException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            exceptionService.handle("获取路口的运行计划表", e, request);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("获取路口的运行计划表异常");
        }
    }
}
