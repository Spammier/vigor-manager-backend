package com.example.hello.controller;

import com.example.hello.common.PageResult;
import com.example.hello.common.Result;
import com.example.hello.pojo.StudentInfo;
import com.example.hello.service.StudentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students") // 基础路径设为 /api/students
public class StudentInfoController {

    @Autowired
    private StudentInfoService studentInfoService;

    /**
     * 分页及条件查询学员列表
     */
    @GetMapping
    public Result<PageResult<StudentInfo>> list(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String studentNumber,
            @RequestParam(required = false) String education,
            @RequestParam(required = false) Integer classId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageResult<StudentInfo> pageResult = studentInfoService.list(name, studentNumber, education, classId, page, pageSize);
        return Result.success(pageResult);
    }

    /**
     * 根据ID查询学员信息
     */
    @GetMapping("/{id}")
    public Result<StudentInfo> findById(@PathVariable Integer id) {
        StudentInfo studentInfo = studentInfoService.findById(id);
        if (studentInfo != null) {
            return Result.success(studentInfo);
        } else {
            return Result.error("学员不存在");
        }
    }

    /**
     * 新增学员
     */
    @PostMapping
    public Result<?> add(@RequestBody StudentInfo studentInfo) {
        try {
            // TODO: Add more specific input validation here
            studentInfoService.add(studentInfo);
            return Result.success();
        } catch (DataIntegrityViolationException e) {
            // 捕获数据完整性/约束违反异常
            if (e.getMessage() != null && e.getMessage().contains("student_info.student_number")) {
                return Result.error("学号已存在，请使用其他学号");
            } else if (e.getMessage() != null && e.getMessage().contains("class_id")) {
                 return Result.error("指定的班级不存在");
            } else {
                // 其他约束错误
                return Result.error("数据格式或内容不符合要求");
            }
        } catch (Exception e) {
            // 其他通用异常
            return Result.error("新增学员失败: " + e.getMessage());
        }
    }

    /**
     * 更新学员信息
     */
    @PutMapping
    public Result<?> update(@RequestBody StudentInfo studentInfo) {
        if (studentInfo.getId() == null) {
            return Result.error("更新时必须提供学员ID");
        }
        try {
            // TODO: Add more specific input validation here
            studentInfoService.update(studentInfo);
            return Result.success();
        } catch (DataIntegrityViolationException e) {
            // 捕获数据完整性/约束违反异常
            if (e.getMessage() != null && e.getMessage().contains("student_info.student_number")) {
                return Result.error("学号已存在，请使用其他学号");
            } else if (e.getMessage() != null && e.getMessage().contains("class_id")) {
                 return Result.error("指定的班级不存在");
            } else {
                // 其他约束错误
                return Result.error("数据格式或内容不符合要求");
            }
        } catch (Exception e) {
            // 其他通用异常
            return Result.error("更新学员失败: " + e.getMessage());
        }
    }

    /**
     * 根据ID删除学员
     */
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        try {
            studentInfoService.delete(id);
            return Result.success();
        } catch (Exception e) {
            // More specific exception handling is better
            return Result.error("删除学员失败: " + e.getMessage());
        }
    }
} 