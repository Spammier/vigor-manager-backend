package com.example.hello.service;

import com.example.hello.common.PageResult;
import com.example.hello.pojo.Emp;

import java.time.LocalDate;
import java.util.List;

/**
 * 员工Service接口
 */
public interface EmpService {
    
    /**
     * 员工分页查询
     */
    PageResult<Emp> list(String name, Integer gender, LocalDate begin, LocalDate end, Integer page, Integer pageSize);
    
    /**
     * 查询全部员工
     */
    List<Emp> listAll();
    
    /**
     * 根据ID查询员工
     */
    Emp getById(Integer id);
    
    /**
     * 新增员工
     */
    void add(Emp emp);
    
    /**
     * 更新员工
     */
    void update(Emp emp);
    
    /**
     * 批量删除员工
     */
    void delete(List<Integer> ids);
    
    /**
     * 员工登录
     */
    Emp login(String username, String password);

    /**
     * 员工注册
     * @param emp 包含用户名和密码的员工对象
     * @return true 如果注册成功, false 如果用户名已存在
     */
    boolean register(Emp emp);

    /**
     * 修改密码
     * @param username 当前登录用户的用户名
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return true 如果修改成功, false 如果旧密码错误
     */
    boolean changePassword(String username, String oldPassword, String newPassword);
} 