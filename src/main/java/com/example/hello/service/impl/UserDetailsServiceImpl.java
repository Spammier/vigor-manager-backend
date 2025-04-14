package com.example.hello.service.impl;

import com.example.hello.mapper.EmpMapper;
import com.example.hello.pojo.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private EmpMapper empMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. 根据用户名从数据库查询员工信息
        Emp emp = empMapper.findByUsername(username);

        // 2. 如果用户不存在，抛出 UsernameNotFoundException 异常
        if (emp == null) {
            throw new UsernameNotFoundException("用户 '" + username + "' 不存在");
        }

        // 3. 如果用户存在，将其信息封装成 Spring Security 的 UserDetails 对象返回
        //    - 第一个参数：用户名
        //    - 第二个参数：数据库中存储的加密后的密码
        //    - 第三个参数：权限集合 (这里暂时用空集合，后续可以根据角色扩展)
        return new User(emp.getUsername(), emp.getPassword(), Collections.emptyList());
    }
} 