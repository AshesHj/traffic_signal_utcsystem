package com.mapabc.signal.controller;

import com.mapabc.signal.common.annotation.AspectLog;
import com.mapabc.signal.common.component.Result;
import com.mapabc.signal.common.enums.BaseEnum;
import com.mapabc.signal.common.exception.WarnException;
import com.mapabc.signal.dao.model.TelesemeList;
import com.mapabc.signal.service.TelesemeListService;
import com.mapabc.signal.service.qs.QsPutPlanSignalService;
import com.mapabc.signal.service.source.PlanSignalLlightService;
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

    //适配器开关
    @Value("${signalamp.vendor.qs.switch}")
    private Boolean adSwitch;

    //标准下发service
    @Resource
    private PlanSignalLlightService planSignalLlightService;

    //信号机service
    @Resource
    private TelesemeListService telesemeListService;

    //青松下发service
    @Resource
    private QsPutPlanSignalService qsPutPlanSignalService;


    @AspectLog(description = "全红控制-->设置当前路口信号机是否要执行全红", operationType = BaseEnum.OperationTypeEnum.UPDATE)
    @PostMapping("/{signalId}/allred")
    @ApiOperation(value = "全红控制", notes = "设置当前路口信号机是否要执行全红")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceType", value = "厂家简称 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String", defaultValue = "QS"),
            @ApiImplicitParam(name = "command", value = "1 全红控制 0 取消全红", paramType = "query", required = true, dataType = "int")
    })
    public ResponseEntity updateAllRed(HttpServletRequest request,
                                     @PathVariable String signalId,
                                     @RequestParam String sourceType, @RequestParam int command) {
        try {
            //查询信号机信息
            TelesemeList entity = telesemeListService.selectByPrimaryKey(signalId);
            if (null == entity) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("参数错误，信号机ID不正确。");
            }
            Result result;
            //开启适配服务
            if (sourceType.equals(BaseEnum.VendorTypeEnum.QS.getNick()) && adSwitch) {
                result = qsPutPlanSignalService.updateQsAllRed(entity.getSignalId(), command);

            } else {
                //下发控制
                result = planSignalLlightService.updateAllRed(entity.getSignalId(), sourceType, entity.getSignalType(), command);
            }
            //返回结果
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
            @ApiImplicitParam(name = "command", value = "1 黄闪控制 0 取消黄闪", paramType = "query", required = true, dataType = "int")
    })
    public ResponseEntity updateYellowFlash(HttpServletRequest request,
                                       @PathVariable String signalId,
                                       @RequestParam String sourceType, @RequestParam int command) {
        try {
            //查询信号机信息
            TelesemeList entity = telesemeListService.selectByPrimaryKey(signalId);
            if (null == entity) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("参数错误，信号机ID不正确。");
            }
            Result result;
            //开启适配服务
            if (sourceType.equals(BaseEnum.VendorTypeEnum.QS.getNick()) && adSwitch) {
                result = qsPutPlanSignalService.updateQsYellowFlash(entity.getSignalId(), command);
            } else {
                //下发
                result = planSignalLlightService.updateYellowFlash(entity.getSignalId(), sourceType, entity.getSignalId(), command);
            }
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
            @ApiImplicitParam(name = "command", value = "1 开灯 0 关灯", paramType = "query", required = true, dataType = "int")
    })
    public ResponseEntity updateOff(HttpServletRequest request,
                                            @PathVariable String signalId,
                                            @RequestParam String sourceType, @RequestParam int command) {
        try {
            //查询信号机信息
            TelesemeList entity = telesemeListService.selectByPrimaryKey(signalId);
            if (null == entity) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("参数错误，信号机ID不正确。");
            }
            //下发
            Result result = planSignalLlightService.updateOff(entity.getSignalId(), sourceType, entity.getSignalId(), command);
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
