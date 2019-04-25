package com.mapabc.signal.dao.vo.timeplan;

import com.mapabc.signal.dao.vo.phase.Phase;
import lombok.Data;

import java.util.List;

/**
 * @author yinguijin
 * @version 1.0
 * @Description: [环数据对象]
 * Created on 2019/4/22 15:54
 */
@Data
public class Ring {
    //环编号
    private String ringNo;

    //相位对象列表
    private List<Phase> phases;

}
