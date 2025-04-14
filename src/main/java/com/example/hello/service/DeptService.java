package com.example.hello.service;

import com.example.hello.pojo.Dept;

import java.util.List;

/**
 * 部门Service接口
 */
public interface DeptService {

    /**
     * 查询所有部门
     */
    List<Dept> list();

    /**
     * 删除部门
     */
    void delete(Integer id);

    /**
     * 添加部门
     */
    void add(Dept dept);

    /**
     * 根据ID查询部门
     */
    Dept getById(Integer id);

    /**
     * 修改部门
     */
    void update(Dept dept);
} 