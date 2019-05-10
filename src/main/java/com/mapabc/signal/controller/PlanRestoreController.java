package com.mapabc.signal.controller;

import com.mapabc.signal.common.annotation.AspectLog;
import com.mapabc.signal.common.component.Result;
import com.mapabc.signal.common.constant.Const;
import com.mapabc.signal.common.enums.BaseEnum;
import com.mapabc.signal.common.exception.WarnException;
import com.mapabc.signal.dao.model.TelesemeList;
import com.mapabc.signal.service.TelesemeListService;
import com.mapabc.signal.service.qs.QsPutPlanSignalService;
import com.mapabc.signal.service.source.PlanRestoreService;
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
 * @Description: [方案计划恢复]
 * Created on 2019/4/26 11:57
 */
@Api(value = "方案计划恢复API接口", description = "方案计划恢复API接口", tags = {"控制指令接口"})
@RestController
@RequestMapping("/")
public class PlanRestoreController extends BaseController {

    //适配器开关
    @Value("${signalamp.vendor.qs.switch}")
    private Boolean adSwitch;

    //标准下发service
    @Resource
    private PlanRestoreService planRestoreService;

    //信号机service
    @Resource
    private TelesemeListService telesemeListService;

    //青松下发service
    @Resource
    private QsPutPlanSignalService qsPutPlanSignalService;

    @AspectLog(description = "在信号机设置特殊控制之后，用于恢复时间表方案", operationType = BaseEnum.OperationTypeEnum.UPDATE)
    @PostMapping("/{signalId}/normalplan")
    @ApiOperation(value = "恢复时间", notes = "在信号机设置特殊控制之后，用于恢复时间表方案")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceType", value = "厂家简称 QS/SCATS/HS/HK", paramType = "query", required = true, dataType = "String", defaultValue = "QS"),
    })
    public ResponseEntity updateNormalPlan(HttpServletRequest request,
                                           @PathVariable String signalId,
                                           @RequestParam String sourceType) {
        try {
            //查询信号机信息
            TelesemeList entity = telesemeListService.selectByPrimaryKey(signalId);
            if (null == entity) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("参数错误，信号机ID不正确。");
            }
            Result result;
            //开启适配服务
            if (sourceType.equals(BaseEnum.VendorTypeEnum.QS.getNick()) && adSwitch) {
                result = qsPutPlanSignalService.updateQsNormalPlan(entity.getSignalId());

            } else {
                int command = Const.TRUE;//1 恢复
                result = planRestoreService.updateNormalPlan(entity.getSignalId(), sourceType, entity.getSignalType(), command);
            }
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
