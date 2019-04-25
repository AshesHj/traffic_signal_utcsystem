package com.mapabc.signal.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BaseService<T> {

    /**
     * @description: 保存一个实体，null的属性也会保存，不会使用数据库默认值
     * @author yinguijin
     * @param record 实体对象
     */
    void insert(T record);

    /**
     * @description: 保存一个实体集合，null的属性也会保存，不会使用数据库默认值
     * @author yinguijin
     * @param records 实体对象集合
     */
    void insertList(List<T> records);

    /**
     * @description: 保存一个实体，null的属性不会保存，会使用数据库默认值
     * @author yinguijin
     * @param record 实体对象
     */
    void insertSelective(T record);

    /**
     * @description: 根据主键更新实体全部字段，null值会被更新
     * @author yinguijin
     * @param record 实体对象
     */
    void updateByPrimaryKey(T record);

    /**
     * @description: 根据主键更新属性不为null的值
     * @author yinguijin
     * @param record 实体对象
     */
    void updateByPrimaryKeySelective(T record);

    /**
     * @description: 主键条件进行逻辑删除(因代码生成规则问题，本方法未实现，需要自己通过修改方法实现)
     * @author yinguijin
     * @param key 主键
     */
    void deleteLogic(Object key);

    /**
     * @description: 根据主键条件进行物理删除
     * @author yinguijin
     * @param key 主键
     */
    void deletePhysics(Object key);

    /**
     * @description: 根据实体中的属性值进行查询，查询条件使用等号
     * @author yinguijin
     * @param record 实体对象
     * @return 影响记录数
     */
    List<T> select(T record);

    /**
     * @description: 查询全部结果
     * @author yinguijin
     * @return 影响记录数
     */
    List<T> selectAll();

    /**
     * @description: 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     * @author yinguijin
     * @param key 主键
     * @return 影响记录数
     */
    T selectByPrimaryKey(Object key);

    /**
     * @description: 分页查询记录
     * @author yinguijin
     * @param page 分页对象
     * @param record 查询条件对象
     * @return 分页记录
     */
    PageInfo<T> selectPage(Page<T> page, T record) throws Exception;

}
