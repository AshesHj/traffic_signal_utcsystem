package com.mapabc.signal.controller;

import com.mapabc.signal.common.annotation.AspectLog;
import com.mapabc.signal.common.component.ParamEntity;
import com.mapabc.signal.common.component.Result;
import com.mapabc.signal.common.constant.Const;
import com.mapabc.signal.common.enums.BaseEnum;
import com.mapabc.signal.common.exception.WarnException;
import com.mapabc.signal.common.util.ListUtil;
import com.mapabc.signal.dao.model.TelesemeList;
import com.mapabc.signal.dao.vo.phase.PhaseLock;
import com.mapabc.signal.dao.vo.phase.PhaseLockVo;
import com.mapabc.signal.dao.vo.timeplan.TimePlanVo;
import com.mapabc.signal.service.TelesemeListService;
import com.mapabc.signal.service.qs.QsPutPlanSignalService;
import com.mapabc.signal.service.source.PhaseSchemeService;
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
import java.util.List;

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

    //适配器开关
    @Value("${signalamp.vendor.qs.switch}")
    private Boolean adSwitch;

    //标准下发service
    @Resource
    private PhaseSchemeService phaseSchemeService;

    //信号机service
    @Resource
    private TelesemeListService telesemeListService;

    //青松下发service
    @Resource
    private QsPutPlanSignalService qsPutPlanSignalService;

    @AspectLog(description = "设置当前路口信号机的方案优化", operationType = BaseEnum.OperationTypeEnum.UPDATE)
    @PostMapping("/{signalId}/timeplan")
    @ApiOperation(value = "当前方案优化调整", notes = "设置当前路口信号机的方案优化")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceType", value = "厂家简称 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String", defaultValue = "QS"),
            @ApiImplicitParam(name = "logId", value = "优化日志表主键ID", paramType = "query", required = true, dataType = "String")
    })
    public ResponseEntity updateTimePlan(HttpServletRequest request,
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
                //调用青松接口实时优化
                result = qsPutPlanSignalService.updateQsTimePlan(timePlanVo);
            } else {
                //设置参数
                ParamEntity<TimePlanVo> param = new ParamEntity<>();
                param.setSourceType(sourceType);
                param.setUpdateTime(new Date());
                param.setSystemType(Const.SYSTEM_TYPE);
                timePlanVo.setSignalId(entity.getSignalId());
                param.setDataContent(timePlanVo);
                result = phaseSchemeService.updateTimePlan(param);
            }
            //返回结果
            if (result.isSuccess()) {
                return ResponseEntity.ok("success");
            }
            return ResponseEntity.status(result.getStatus()).body(result.getMsg());
            //TODO 错误时修改日志表状态
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
            //查询信号机信息
            TelesemeList entity = telesemeListService.selectByPrimaryKey(signalId);
            if (null == entity) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("参数错误，信号机ID不正确。");
            }
            List<PhaseLock> phases = phaseLockVo.getPhases();
            if (ListUtil.isEmpty(phases)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("参数错误，信号机相位数据不正确。");
            }
            Result result;
            //开启适配服务
            if (sourceType.equals(BaseEnum.VendorTypeEnum.QS.getNick()) && adSwitch) {
                PhaseLock lock = phases.get(0);
                result = qsPutPlanSignalService.updateQsLockPhase(entity.getSignalId(), lock.getCommand());

            } else {
                //设置参数
                ParamEntity<PhaseLockVo> param = new ParamEntity<>();
                param.setSourceType(sourceType);
                param.setUpdateTime(new Date());
                param.setSystemType(Const.SYSTEM_TYPE);
                phaseLockVo.setSignalId(entity.getSignalId());
                phaseLockVo.setSignalType(entity.getSignalType());
                param.setDataContent(phaseLockVo);
                result = phaseSchemeService.updateLockPhase(param);
            }
            //返回结果
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
            @ApiImplicitParam(name = "command", value = "1 开始步进 0 取消步进", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "stepNum", value = "0 顺序步进 n 跳过n个相位", paramType = "query", required = true, dataType = "int"),
    })
    public ResponseEntity updateStep(HttpServletRequest request,
                                     @PathVariable String signalId,
                                     @RequestParam String sourceType,
                                     @RequestParam int command, @RequestParam int stepNum) {
        try {
            //查询信号机信息
            TelesemeList entity = telesemeListService.selectByPrimaryKey(signalId);
            if (null == entity) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("参数错误，信号机ID不正确。");
            }
            //调用接口
            Result result = phaseSchemeService.updateStep(entity.getSignalId(), sourceType, entity.getSignalType(), command, stepNum);
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
            @ApiImplicitParam(name = "timePlanId", value = "配时方案编号", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "minutes", value = "配时方案运行时长（分钟）", paramType = "query", dataType = "int")
    })
    public ResponseEntity updateForcePlan(HttpServletRequest request,
                                     @PathVariable String signalId,
                                     @RequestParam String sourceType, @RequestParam String timePlanId, @RequestParam Integer minutes) {
        try {
            //查询信号机信息
            TelesemeList entity = telesemeListService.selectByPrimaryKey(signalId);
            if (null == entity) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("参数错误，信号机ID不正确。");
            }
            Result result;
            //开启适配服务
            if (sourceType.equals(BaseEnum.VendorTypeEnum.QS.getNick()) && adSwitch) {
                result = qsPutPlanSignalService.updateQsForcePlan(entity.getSignalId(), timePlanId, minutes);
            } else {
                //调用接口
                result = phaseSchemeService.updateForcePlan(entity.getSignalId(), sourceType, entity.getSignalType(), timePlanId);
            }
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
