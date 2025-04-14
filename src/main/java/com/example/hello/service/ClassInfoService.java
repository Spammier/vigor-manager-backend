package com.example.hello.service;

import com.example.hello.common.PageResult;
import com.example.hello.pojo.ClassInfo;
import java.time.LocalDate;

public interface ClassInfoService {

    /**
     * 分页及条件查询班级列表
     */
    PageResult<ClassInfo> list(String className, String classStatus, LocalDate startTimeStart, LocalDate startTimeEnd, Integer page, Integer pageSize);

    /**
     * 根据ID查询班级信息
     */
    ClassInfo findById(Integer id);

    /**
     * 新增班级
     */
    void add(ClassInfo classInfo);

    /**
     * 更新班级信息
     */
    void update(ClassInfo classInfo);

    /**
     * 根据ID删除班级
     */
    void delete(Integer id);
} 