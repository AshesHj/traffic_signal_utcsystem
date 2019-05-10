package com.mapabc.signal.controller;

import com.mapabc.signal.common.annotation.AspectLog;
import com.mapabc.signal.common.enums.BaseEnum;
import com.mapabc.signal.common.exception.WarnException;
import com.mapabc.signal.service.TelesemeListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yinguijin
 * @version 1.0
 * Created on 2019/5/7 20:51
 * @description: [信号机列表接口]
 */
@RestController
@RequestMapping("/teleseme")
@Api(value = "信号机列表接口API", description = "信号机列表接口API", tags = {"数据库交互接口"})
public class TelesemeListController extends BaseController {

    @Resource
    private TelesemeListService telesemeListService;

    @AspectLog(description = "手动刷新信号机状态", operationType = BaseEnum.OperationTypeEnum.UPDATE)
    @ApiOperation(value = "手动刷新信号机状态", notes = "手动刷新信号机状态")
    @GetMapping("/db")
    public ResponseEntity refreshSignalStatusToDB() {
        try {
            telesemeListService.refreshSignalStatusToDB();
            return ResponseEntity.ok("success");
        } catch (WarnException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            exceptionService.handle("手动刷新信号机状态异常", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("手动刷新信号机状态异常");
        }
    }


    @AspectLog(description = "手动刷新Redis信号机状态", operationType = BaseEnum.OperationTypeEnum.UPDATE)
    @ApiOperation(value = "手动刷新Redis信号机状态", notes = "手动刷新Redis信号机状态")
    @GetMapping("/redis")
    public ResponseEntity refreshSignalStatusToRedis() {
        try {
            telesemeListService.refreshSignalStatusToRedis();
            return ResponseEntity.ok("success");
        } catch (WarnException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            exceptionService.handle("手动刷新信号机状态异常", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("手动刷新信号机状态异常");
        }
    }
}
