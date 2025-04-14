package com.example.hello.service.impl;

import com.example.hello.mapper.DeptMapper;
import com.example.hello.pojo.Dept;
import com.example.hello.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 部门Service实现类
 */
@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    /**
     * 查询所有部门
     */
    @Override
    public List<Dept> list() {
        return deptMapper.list();
    }

    /**
     * 删除部门
     */
    @Override
    public void delete(Integer id) {
        deptMapper.delete(id);
    }

    /**
     * 添加部门
     */
    @Override
    public void add(Dept dept) {
        deptMapper.add(dept);
    }

    /**
     * 根据ID查询部门
     */
    @Override
    public Dept getById(Integer id) {
        return deptMapper.getById(id);
    }

    /**
     * 修改部门
     */
    @Override
    public void update(Dept dept) {
        deptMapper.update(dept);
    }
} 