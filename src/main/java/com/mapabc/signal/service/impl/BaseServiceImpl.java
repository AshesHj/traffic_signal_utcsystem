package com.mapabc.signal.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mapabc.signal.dao.MyBaseMapper;
import com.mapabc.signal.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

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
}
