package com.example.hello.service.impl;

import com.example.hello.common.PageResult;
import com.example.hello.mapper.StudentInfoMapper;
import com.example.hello.pojo.StudentInfo;
import com.example.hello.service.StudentInfoService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentInfoServiceImpl implements StudentInfoService {

    @Autowired
    private StudentInfoMapper studentInfoMapper;

    @Override
    public PageResult<StudentInfo> list(String name, String studentNumber, String education, Integer classId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<StudentInfo> list = studentInfoMapper.list(name, studentNumber, education, classId);
        Page<StudentInfo> p = (Page<StudentInfo>) list;
        return new PageResult<>(p.getTotal(), p.getResult());
    }

    @Override
    public StudentInfo findById(Integer id) {
        return studentInfoMapper.findById(id);
    }

    @Override
    public void add(StudentInfo studentInfo) {
        // 不再需要转换性别字段
        // convertGender(studentInfo);
        
        // TODO: Add validation, e.g., check if student_number is unique, check if class_id exists
        studentInfoMapper.insert(studentInfo);
    }

    @Override
    public void update(StudentInfo studentInfo) {
        // 不再需要转换性别字段
        // convertGender(studentInfo);

        // TODO: Add validation
        studentInfoMapper.update(studentInfo);
    }

    @Override
    public void delete(Integer id) {
        studentInfoMapper.deleteById(id);
    }
} 