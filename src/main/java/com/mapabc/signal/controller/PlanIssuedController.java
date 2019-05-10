package com.mapabc.signal.controller;

import com.mapabc.signal.common.annotation.AspectLog;
import com.mapabc.signal.common.component.ParamEntity;
import com.mapabc.signal.common.component.Result;
import com.mapabc.signal.common.constant.Const;
import com.mapabc.signal.common.enums.BaseEnum;
import com.mapabc.signal.common.exception.WarnException;
import com.mapabc.signal.dao.model.TelesemeList;
import com.mapabc.signal.dao.vo.phase.PhasePlanVo;
import com.mapabc.signal.dao.vo.runplan.RunplanVo;
import com.mapabc.signal.dao.vo.sectionplan.SectionPlanVo;
import com.mapabc.signal.dao.vo.timeplan.TimePlanVo;
import com.mapabc.signal.service.TelesemeListService;
import com.mapabc.signal.service.qs.QsPutPlanSignalService;
import com.mapabc.signal.service.source.PlanIssuedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [控制指令-相位/配时/时段/运行方案下发API接口]
 * Created on 2019/4/25 17:07
 */
@Api(value = "相位/配时/时段/运行方案下发API接口", description = "相位/配时/时段/运行方案下发API接口", tags = {"控制指令接口"})
@RestController
@RequestMapping("/")
public class PlanIssuedController extends BaseController {

    //适配器开关
    @Value("${signalamp.vendor.qs.switch}")
    private Boolean adSwitch;

    //标准下发service
    @Resource
    private PlanIssuedService planIssuedService;

    //信号机service
    @Resource
    private TelesemeListService telesemeListService;

    //青松下发service
    @Resource
    private QsPutPlanSignalService qsPutPlanSignalService;


    @AspectLog(description = "下发相位方案信息到路口信号机或信号系统中", operationType = BaseEnum.OperationTypeEnum.UPDATE)
    @PostMapping("/{signalId}/phaseplans")
    @ApiOperation(value = "相位方案下发", notes = "下发相位方案信息到路口信号机或信号系统中")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceType", value = "厂家简称 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String", defaultValue = "QS")
    })
    public ResponseEntity updatePhasePlans(HttpServletRequest request,
                                           @PathVariable String signalId,
                                           @RequestParam String sourceType, @RequestBody PhasePlanVo phasePlanVo) {
        try {
            //查询信号机信息
            TelesemeList entity = telesemeListService.selectByPrimaryKey(signalId);
            if (null == entity) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("参数错误，信号机ID不正确。");
            }
            Result result;
            //开启适配服务
            if (sourceType.equals(BaseEnum.VendorTypeEnum.QS.getNick()) && adSwitch) {
                //调用青松接口实时优化 TODO
                result = new Result();
            } else {
                //设置参数
                ParamEntity<PhasePlanVo> param = new ParamEntity<>();
                param.setSourceType(sourceType);
                param.setUpdateTime(new Date());
                param.setSystemType(Const.SYSTEM_TYPE);
                phasePlanVo.setSignalId(entity.getSignalId());
                phasePlanVo.setSignalType(entity.getSignalType());
                param.setDataContent(phasePlanVo);
                result = planIssuedService.updatePhasePlans(param);
            }
            if (result.isSuccess()) {
                return ResponseEntity.ok("success");
            }
            return ResponseEntity.status(result.getStatus()).body(result.getMsg());
        } catch (WarnException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            exceptionService.handle("相位方案下发-->下发相位方案信息到路口信号机或信号系统异常", e, request);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("下发相位方案信息到路口信号机或信号系统异常");
        }
    }

    @AspectLog(description = "下发配时方案信息到路口信号机或信号系统", operationType = BaseEnum.OperationTypeEnum.UPDATE)
    @PostMapping("/{signalId}/timeplans")
    @ApiOperation(value = "配时方案下发", notes = "下发配时方案信息到路口信号机或信号系统")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceType", value = "厂家简称 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String", defaultValue = "QS")
    })
    public ResponseEntity updateTimePlans(HttpServletRequest request,
                                           @PathVariable String signalId,
                                           @RequestParam String sourceType, @RequestBody TimePlanVo timePlanVo) {
        try {
            //查询信号机信息
            TelesemeList entity = telesemeListService.selectByPrimaryKey(signalId);
            if (null == entity) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("参数错误，信号机ID不正确。");
            }
            Result result;
            //开启适配服务
            if (sourceType.equals(BaseEnum.VendorTypeEnum.QS.getNick()) && adSwitch) {
                //调用青松接口实时优化 TODO
                result = new Result();
            } else {
                //设置参数
                ParamEntity<TimePlanVo> param = new ParamEntity<>();
                param.setSourceType(sourceType);
                param.setUpdateTime(new Date());
                param.setSystemType(Const.SYSTEM_TYPE);
                timePlanVo.setSignalId(entity.getSignalId());
                timePlanVo.setSignalType(entity.getSignalType());
                param.setDataContent(timePlanVo);
                //发送请求
                result = planIssuedService.updateTimePlans(param);
            }
            if (result.isSuccess()) {
                return ResponseEntity.ok("success");
            }
            return ResponseEntity.status(result.getStatus()).body(result.getMsg());
        } catch (WarnException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            exceptionService.handle("下发配时方案信息到路口信号机或信号系统中异常", e, request);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("下发配时方案信息到路口信号机或信号系统中异常");
        }
    }


    @AspectLog(description = "下发时段方案信息到路口信号机或信号系统中", operationType = BaseEnum.OperationTypeEnum.UPDATE)
    @PostMapping("/{signalId}/sectionplans")
    @ApiOperation(value = "时段方案下发", notes = "下发时段方案信息到路口信号机或信号系统中")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceType", value = "厂家简称 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String", defaultValue = "QS")
    })
    public ResponseEntity updateSectionPlans(HttpServletRequest request,
                                          @PathVariable String signalId,
                                          @RequestParam String sourceType, @RequestBody SectionPlanVo sectionPlanVo) {
        try {
            //查询信号机信息
            TelesemeList entity = telesemeListService.selectByPrimaryKey(signalId);
            if (null == entity) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("参数错误，信号机ID不正确。");
            }
            Result result;
            //开启适配服务
            if (sourceType.equals(BaseEnum.VendorTypeEnum.QS.getNick()) && adSwitch) {
                //调用青松接口实时优化 TODO
                result = new Result();
            } else {
                //设置参数
                ParamEntity<SectionPlanVo> param = new ParamEntity<>();
                param.setSourceType(sourceType);
                param.setUpdateTime(new Date());
                param.setSystemType(Const.SYSTEM_TYPE);
                sectionPlanVo.setSignalId(entity.getSignalId());
                sectionPlanVo.setSignalType(entity.getSignalType());
                param.setDataContent(sectionPlanVo);
                //发送请求
                result = planIssuedService.updateSectionPlans(param);
            }
            if (result.isSuccess()) {
                return ResponseEntity.ok("success");
            }
            return ResponseEntity.status(result.getStatus()).body(result.getMsg());
        } catch (WarnException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            exceptionService.handle("下发配时方案信息到路口信号机或信号系统中异常", e, request);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("下发配时方案信息到路口信号机或信号系统中异常");
        }
    }


    @AspectLog(description = "下发运行计划信息到路口信号机或信号系统中", operationType = BaseEnum.OperationTypeEnum.UPDATE)
    @PostMapping("/{signalId}/runplan")
    @ApiOperation(value = "运行计划下发", notes = "下发运行计划信息到路口信号机或信号系统中")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceType", value = "厂家简称 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String", defaultValue = "QS")
    })
    public ResponseEntity updateRunPlan(HttpServletRequest request,
                                             @PathVariable String signalId,
                                             @RequestParam String sourceType, @RequestBody RunplanVo runplanVo) {
        try {
            //查询信号机信息
            TelesemeList entity = telesemeListService.selectByPrimaryKey(signalId);
            if (null == entity) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("参数错误，信号机ID不正确。");
            }
            Result result;
            //开启适配服务
            if (sourceType.equals(BaseEnum.VendorTypeEnum.QS.getNick()) && adSwitch) {
                //调用青松接口实时优化 TODO
                result = new Result();
            } else {
                //设置参数
                ParamEntity<RunplanVo> param = new ParamEntity<>();
                param.setSourceType(sourceType);
                param.setUpdateTime(new Date());
                param.setSystemType(Const.SYSTEM_TYPE);
                runplanVo.setSignalId(entity.getSignalId());
                runplanVo.setSignalType(entity.getSignalType());
                param.setDataContent(runplanVo);
                //发送请求
                result = planIssuedService.updateRunPlan(param);
            }
            if (result.isSuccess()) {
                return ResponseEntity.ok("success");
            }
            return ResponseEntity.status(result.getStatus()).body(result.getMsg());
        } catch (WarnException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            exceptionService.handle("下发运行计划信息到路口信号机或信号系统中异常", e, request);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("下发运行计划信息到路口信号机或信号系统中异常");
        }
    }
}

