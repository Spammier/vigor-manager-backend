package com.example.hello.controller;

import com.example.hello.common.PageResult;
import com.example.hello.common.Result;
import com.example.hello.pojo.Dept;
import com.example.hello.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门管理Controller
 */
@RestController
@RequestMapping("/api/depts")
public class DeptController {

    @Autowired
    private DeptService deptService;

    /**
     * 部门列表查询
     */
    @GetMapping
    public Result<PageResult<Dept>> list() {
        List<Dept> deptList = deptService.list();
        PageResult<Dept> pageResult = new PageResult<>((long) deptList.size(), deptList);
        return Result.success(pageResult);
    }

    /**
     * 删除部门
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Integer id) {
        deptService.delete(id);
        return Result.success();
    }

    /**
     * 添加部门
     */
    @PostMapping
    public Result<Void> add(@RequestBody Dept dept) {
        deptService.add(dept);
        return Result.success();
    }

    /**
     * 根据ID查询部门
     */
    @GetMapping("/{id}")
    public Result<Dept> getById(@PathVariable Integer id) {
        Dept dept = deptService.getById(id);
        return Result.success(dept);
    }

    /**
     * 修改部门
     */
    @PutMapping
    public Result<Void> update(@RequestBody Dept dept) {
        deptService.update(dept);
        return Result.success();
    }
} 