package com.mapabc.signal.controller;

import com.mapabc.signal.common.annotation.AspectLog;
import com.mapabc.signal.common.component.Result;
import com.mapabc.signal.common.enums.BaseEnum;
import com.mapabc.signal.common.exception.WarnException;
import com.mapabc.signal.service.PlanRestoreService;
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
 * @Description: [方案计划恢复]
 * Created on 2019/4/26 11:57
 */
@Api(value = "方案计划恢复API接口", description = "方案计划恢复API接口", tags = {"控制指令接口"})
@RestController
@RequestMapping("/")
public class PlanRestoreController extends BaseController {

    @Resource
    private PlanRestoreService planRestoreService;

    @AspectLog(description = "在信号机设置特殊控制之后，用于恢复时间表方案", operationType = BaseEnum.OperationTypeEnum.UPDATE)
    @PostMapping("/{signalId}/normalplan")
    @ApiOperation(value = "恢复时间", notes = "在信号机设置特殊控制之后，用于恢复时间表方案")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceType", value = "厂家简称 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String", defaultValue = "QS"),
            @ApiImplicitParam(name = "signalType", value = "信号机类型 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String"),
    })
    public ResponseEntity updateNormalPlan(HttpServletRequest request,
                                           @PathVariable String signalId,
                                           @RequestParam String sourceType, @RequestParam String signalType) {
        try {
            int command = 1;//1 恢复
            Result result = planRestoreService.updateNormalPlan(signalId, sourceType, signalType, command);
            if (result.isSuccess()) {
                return ResponseEntity.ok("success");
            }
            return ResponseEntity.status(result.getStatus()).body(result.getMsg());
        } catch (WarnException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            exceptionService.handle("恢复时间异常", e, request);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("恢复时间异常");
        }
    }
}
