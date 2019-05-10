package com.mapabc.signal.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mapabc.signal.common.annotation.Ignore;
import com.mapabc.signal.common.annotation.Like;
import com.mapabc.signal.common.constant.Const;
import com.mapabc.signal.common.util.StringUtils;
import com.mapabc.signal.dao.MyBaseMapper;
import com.mapabc.signal.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;


public class BaseServiceImpl<T, D extends MyBaseMapper<T>> implements BaseService<T> {

    @Autowired
    D myBaseMapper;

    @Override
    public void insert(T record) {
        myBaseMapper.insert(record);
    }

    @Override
    public void insertList(List<T> records) {
        myBaseMapper.insertList(records);
    }


    @Override
    public void insertSelective(T record) {
        myBaseMapper.insertSelective(record);
    }

    @Override
    public void updateByPrimaryKey(T record) {
        myBaseMapper.updateByPrimaryKey(record);
    }

    @Override
    public void updateByPrimaryKeySelective(T record) {
        myBaseMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public void deleteLogic(Object key) {
    }

    @Override
    public void deletePhysics(Object key) {
        myBaseMapper.deleteByPrimaryKey(key);
    }

    @Override
    public List<T> select(T record) {
        return myBaseMapper.select(record);
    }

    @Override
    public List<T> selectAll() {
        return myBaseMapper.selectAll();
    }

    @Override
    public T selectByPrimaryKey(Object key) {
        return myBaseMapper.selectByPrimaryKey(key);
    }

    @Override
    public PageInfo<T> selectPage(Page<T> page, T record) throws Exception {
        Page<T> pageInfo = PageHelper.startPage(page.getPageNum(), page.getPageSize());
        myBaseMapper.select(record);
        return pageInfo.toPageInfo();
    }

    /**
     * @description:
     * @param obj
     * @param example
     * @return tk.mybatis.mapper.entity.Example.Criteria
     * @author yinguijin
     * @date 2019/5/5 14:59
     */
    public Example.Criteria buildCriteriaByEntity(Object obj, Example example)  {
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        Example.Criteria criteria = example.createCriteria();
        if(declaredFields!=null){
            for (Field item:declaredFields) {
                boolean isStatic = Modifier.isStatic(item.getModifiers());
                if(isStatic){
                    continue;
                }
                Ignore ignore = item.getDeclaredAnnotation(Ignore.class);
                if(ignore!=null){
                    continue;
                }
                String fieldName = item.getName();
                Like like = item.getDeclaredAnnotation(Like.class);
                String firstLetter = fieldName.substring(0, 1).toUpperCase();
                String getter = "get" + firstLetter + fieldName.substring(1);
                Method method;
                Object value;
                try {
                    method = obj.getClass().getMethod(getter, new Class[] {});
                    value = method.invoke(obj, new Object[] {});
                    if(value == null){
                        continue;
                    }
                    if(value instanceof String && StringUtils.isEmpty(value.toString())){
                        continue;
                    }
                } catch (Exception e) {
                    throw new RuntimeException("构造查询条件失败："+e);
                }
                if(like!=null){
                    value = value.toString().replaceAll(Const.SEPARATOR_UNDER_LINE, Const.TRANS_UNDERLINE);
                    criteria.andLike(fieldName, "%"+value.toString()+"%");
                    continue;
                }
                criteria.andEqualTo(fieldName, value);
            }
        }
        return criteria;
    }
}
