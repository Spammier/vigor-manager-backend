package com.example.hello.service;

import com.example.hello.common.PageResult;
import com.example.hello.pojo.StudentInfo;

public interface StudentInfoService {

    /**
     * 分页及条件查询学员列表
     */
    PageResult<StudentInfo> list(String name, String studentNumber, String education, Integer classId, Integer page, Integer pageSize);

    /**
     * 根据ID查询学员信息
     */
    StudentInfo findById(Integer id);

    /**
     * 新增学员
     */
    void add(StudentInfo studentInfo);

    /**
     * 更新学员信息
     */
    void update(StudentInfo studentInfo);

    /**
     * 根据ID删除学员
     */
    void delete(Integer id);
} 