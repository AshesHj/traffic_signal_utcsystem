package com.mapabc.signal.dao.vo.phase;

import com.mapabc.signal.common.component.BaseSignal;
import lombok.Data;

import java.util.List;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [相位锁定实体类]
 * Created on 2019/4/18 18:43
 */
@Data
public class PhaseLockVo extends BaseSignal {

    //相位对象列表
    private List<PhaseLock> phases;
}
