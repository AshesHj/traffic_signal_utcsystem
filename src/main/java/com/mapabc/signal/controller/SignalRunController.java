package com.mapabc.signal.controller;

import com.mapabc.signal.common.annotation.AspectLog;
import com.mapabc.signal.common.component.BaseSignal;
import com.mapabc.signal.common.component.ParamEntity;
import com.mapabc.signal.common.component.VendorResult;
import com.mapabc.signal.common.constant.Const;
import com.mapabc.signal.common.enums.BaseEnum;
import com.mapabc.signal.common.exception.WarnException;
import com.mapabc.signal.common.util.ListUtil;
import com.mapabc.signal.dao.model.TelesemeList;
import com.mapabc.signal.dao.vo.signal.SignalInfoVo;
import com.mapabc.signal.dao.vo.signalalarms.SignalAlarmsVo;
import com.mapabc.signal.service.TelesemeListService;
import com.mapabc.signal.service.qs.QsGetSignalMethodService;
import com.mapabc.signal.service.source.SignalRunService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Value("${signalamp.vendor.qs.switch}")
    private Boolean adSwitch;

    //动态数据标准接口
    @Resource
    private SignalRunService signalRunService;
    //青松接口
    @Resource
    private QsGetSignalMethodService qsGetSignalMethodService;
    //信号机接口service
    @Resource
    private TelesemeListService telesemeListService;

    @AspectLog(description = "获取路口信号机的实时运行状态数据", operationType = BaseEnum.OperationTypeEnum.QUERY)
    @GetMapping("/runstate")
    @ApiOperation(value = "信号机运行状态数据", notes = "获取路口信号机的实时运行状态数据")
    public ResponseEntity queryRunState (HttpServletRequest request, @RequestParam(value = "ids", required = false) @ApiParam(value = "信号机表ID集合") List<String> ids) {
        try {
            Map<String, List<TelesemeList>> map = telesemeListService.selectByIds(ids);
            return ResponseEntity.ok(map);
        } catch (WarnException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            exceptionService.handle("获取路口信号机的实时运行状态数据异常", e, request);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("获取路口信号机的实时运行状态数据异常");
        }
    }


    @AspectLog(description = "获取路口信号机的实时告警数据", operationType = BaseEnum.OperationTypeEnum.QUERY)
    @GetMapping("/alarms")
    @ApiOperation(value = "信号机告警数据", notes = "获取路口信号机的实时告警数据")
    public ResponseEntity queryAlarms (HttpServletRequest request, @RequestParam(value = "ids", required = false) @ApiParam(value = "信号机表ID集合") List<String> ids) {
        try {
            Map<String, List<TelesemeList>> map = telesemeListService.selectByIds(ids);
            List<SignalAlarmsVo> list = new ArrayList<>();
            for (String key : map.keySet()) {
                if (ListUtil.isEmpty(map.get(key))) {
                    continue;
                }
                //开启适配服务
                if (key.equals(BaseEnum.VendorTypeEnum.QS.getNick()) && adSwitch) {
                    //调用青松接口 TODO
                    //青松信号机列表
                    List<TelesemeList> qsList = map.get(key);
                } else {
                    //设置参数
                    ParamEntity<List<BaseSignal>> param = new ParamEntity<>();
                    param.setSourceType(key);
                    param.setUpdateTime(new Date());
                    param.setDataContent(telesemeListService.getBaseSignals(map.get(key)));
                    param.setSystemType(Const.SYSTEM_TYPE);
                    //调用接口
                    VendorResult<SignalAlarmsVo> result = signalRunService.queryAlarms(param);
                    if (null != result) {
                        list.addAll(result.getDataContent());
                    }
                }
            }
            return ResponseEntity.ok(list);
        } catch (WarnException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            exceptionService.handle("获取路口信号机的实时告警数据异常", e, request);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("获取路口信号机的实时告警数据异常");
        }
    }


    @AspectLog(description = "获取路口信号机的实时灯态数据", operationType = BaseEnum.OperationTypeEnum.QUERY)
    @GetMapping("/signal")
    @ApiOperation(value = "信号灯态数据", notes = "获取路口信号机的实时灯态数据")
    public ResponseEntity querySignalInfo (HttpServletRequest request, @RequestParam(value = "ids", required = false) @ApiParam(value = "信号机表ID集合") List<String> ids) {
        try {
            Map<String, List<TelesemeList>> map = telesemeListService.selectByIds(ids);
            List<SignalInfoVo> list = new ArrayList<>();
            for (String key : map.keySet()) {
                if (ListUtil.isEmpty(map.get(key))) {
                    continue;
                }
                //开启适配服务
                if (key.equals(BaseEnum.VendorTypeEnum.QS.getNick()) && adSwitch) {
                    //调用青松接口
                    List<SignalInfoVo> signalInfos = qsGetSignalMethodService.queryQsSignalInfo(telesemeListService.getBaseSignals(map.get(key)));
                    list.addAll(signalInfos);
                } else {
                    //设置参数
                    ParamEntity<List<BaseSignal>> param = new ParamEntity<>();
                    param.setSourceType(key);
                    param.setUpdateTime(new Date());
                    param.setDataContent(telesemeListService.getBaseSignals(map.get(key)));
                    param.setSystemType(Const.SYSTEM_TYPE);
                    //调用标准接口
                    VendorResult<SignalInfoVo> result = signalRunService.querySignalInfo(param);
                    if (null != result) {
                        list.addAll(result.getDataContent());
                    }
                }
            }
            return ResponseEntity.ok(list);
        } catch (WarnException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            exceptionService.handle("获取路口信号机的实时灯态数据异常", e, request);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("获取路口信号机的实时灯态数据异常");
        }
    }


//    @AspectLog(description = "获取路口信号机的实时流量数据", operationType = BaseEnum.OperationTypeEnum.QUERY)
//    @GetMapping("/volume")
//    @ApiOperation(value = "信号流量数据", notes = "获取路口信号机的实时流量数据")
//    public ResponseEntity querySignalVolume (HttpServletRequest request, @RequestParam(value = "ids", required = false) @ApiParam(value = "信号机表ID集合") List<String> ids) {
//        try {
//            TODO
//            //设置参数
//            ParamEntity<List<BaseSignal>> param = new ParamEntity<>();
//            param.setSourceType(sourceType);
//            param.setUpdateTime(new Date());
//            param.setDataContent(dataContent);
//            param.setSystemType(Const.SYSTEM_TYPE);
//            //调用接口
//            VendorResult<SignalVolumeVo> result = signalRunService.querySignalVolume(param);
//            return ResponseEntity.ok(result.getDataContent());
//            return ResponseEntity.ok("");
//        } catch (WarnException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        } catch (Exception e) {
//            exceptionService.handle("获取路口信号机的实时流量数据异常", e, request);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("获取路口信号机的实时流量数据异常");
//        }
//    }


}
