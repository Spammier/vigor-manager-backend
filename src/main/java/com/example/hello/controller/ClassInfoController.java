package com.example.hello.controller;

import com.example.hello.common.PageResult;
import com.example.hello.common.Result;
import com.example.hello.pojo.ClassInfo;
import com.example.hello.service.ClassInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/classes") // 基础路径设为 /api/classes
public class ClassInfoController {

    @Autowired
    private ClassInfoService classInfoService;

    /**
     * 分页及条件查询班级列表
     */
    @GetMapping
    public Result<PageResult<ClassInfo>> list(
            @RequestParam(required = false) String className,
            @RequestParam(required = false) String classStatus,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startTimeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startTimeEnd,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageResult<ClassInfo> pageResult = classInfoService.list(className, classStatus, startTimeStart, startTimeEnd, page, pageSize);
        return Result.success(pageResult);
    }

    /**
     * 根据ID查询班级信息
     */
    @GetMapping("/{id}")
    public Result<ClassInfo> findById(@PathVariable Integer id) {
        ClassInfo classInfo = classInfoService.findById(id);
        if (classInfo != null) {
            return Result.success(classInfo);
        } else {
            return Result.error("班级不存在");
        }
    }

    /**
     * 新增班级
     */
    @PostMapping
    public Result<?> add(@RequestBody ClassInfo classInfo) {
        // 可以在这里添加更严格的输入校验
        classInfoService.add(classInfo);
        return Result.success();
    }

    /**
     * 更新班级信息
     */
    @PutMapping
    public Result<?> update(@RequestBody ClassInfo classInfo) {
        if (classInfo.getId() == null) {
            return Result.error("更新时必须提供班级ID");
        }
        classInfoService.update(classInfo);
        return Result.success();
    }

    /**
     * 根据ID删除班级
     */
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        try {
             classInfoService.delete(id);
             return Result.success();
        } catch (Exception e) {
             // 更具体的异常处理会更好
             return Result.error("删除班级失败: " + e.getMessage());
        }
    }
} 