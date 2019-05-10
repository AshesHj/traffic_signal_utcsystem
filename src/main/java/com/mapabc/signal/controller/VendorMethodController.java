package com.mapabc.signal.controller;

import com.mapabc.signal.common.annotation.AspectLog;
import com.mapabc.signal.common.enums.BaseEnum;
import com.mapabc.signal.common.exception.WarnException;
import com.mapabc.signal.service.TBaseVendorMethodService;
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
 * Created on 2019/4/29 10:12
 * @description: [厂商接口信息接口]
 */
@RestController
@RequestMapping("/vendorMethod")
@Api(value = "厂商接口信息接口API", description = "厂商接口信息接口API", tags = {"数据库交互接口"})
public class VendorMethodController extends BaseController {

    @Resource
    private TBaseVendorMethodService tBaseVendorMethodService;

    @AspectLog(description = "手动加载厂商接口URL到内存", operationType = BaseEnum.OperationTypeEnum.UPDATE)
    @ApiOperation(value = "手动加载厂商接口URL到内存", notes = "手动加载厂商接口URL到内存")
    @GetMapping
    public ResponseEntity loadVendorUrl() {
        try {
            tBaseVendorMethodService.initLoadVendor();
            return ResponseEntity.ok("success");
        } catch (WarnException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            exceptionService.handle("手动加载厂商接口URL到内存异常", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("手动加载厂商接口URL到内存异常");
        }
    }
}
