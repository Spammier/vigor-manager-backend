package com.example.hello.service.impl;

import com.example.hello.common.PageResult;
import com.example.hello.mapper.EmpExprMapper;
import com.example.hello.mapper.EmpMapper;
import com.example.hello.pojo.Emp;
import com.example.hello.pojo.EmpExpr;
import com.example.hello.service.EmpService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * 员工Service实现类
 */
@Service
public class EmpServiceImpl implements EmpService {
    
    @Autowired
    private EmpMapper empMapper;
    
    @Autowired
    private EmpExprMapper empExprMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    /**
     * 员工分页查询
     */
    @Override
    public PageResult<Emp> list(String name, Integer gender, LocalDate begin, LocalDate end, Integer page, Integer pageSize) {
        // 设置分页参数
        PageHelper.startPage(page, pageSize);
        
        // 执行查询
        List<Emp> empList = empMapper.list(name, gender, begin, end);
        Page<Emp> p = (Page<Emp>) empList;
        
        // 封装分页结果
        return new PageResult<>(p.getTotal(), p.getResult());
    }
    
    /**
     * 查询全部员工
     */
    @Override
    public List<Emp> listAll() {
        return empMapper.listAll();
    }
    
    /**
     * 根据ID查询员工
     */
    @Override
    public Emp getById(Integer id) {
        // 查询员工基本信息
        Emp emp = empMapper.getById(id);
        if (emp != null) {
            // 查询员工工作经历
            List<EmpExpr> exprList = empExprMapper.getByEmpId(id);
            emp.setExprList(exprList);
        }
        return emp;
    }
    
    /**
     * 新增员工
     */
    @Override
    @Transactional
    public void add(Emp emp) {
        // 设置默认密码
        if (emp.getPassword() == null) {
            emp.setPassword("123456");
        }
        
        // 插入员工基本信息
        empMapper.insert(emp);
        
        // 获取员工ID
        Integer empId = emp.getId();
        
        // 插入员工工作经历
        if (emp.getExprList() != null && !emp.getExprList().isEmpty()) {
            // 设置工作经历的员工ID
            emp.getExprList().forEach(expr -> expr.setEmpId(empId));
            
            // 批量插入工作经历
            empExprMapper.batchInsert(emp.getExprList());
        }
    }
    
    /**
     * 更新员工
     */
    @Override
    @Transactional
    public void update(Emp emp) {
        // 更新员工基本信息
        empMapper.update(emp);
        
        // 获取员工ID
        Integer empId = emp.getId();
        
        // 删除原有的工作经历
        empExprMapper.deleteByEmpId(empId);
        
        // 插入新的工作经历
        if (emp.getExprList() != null && !emp.getExprList().isEmpty()) {
            // 设置工作经历的员工ID
            emp.getExprList().forEach(expr -> expr.setEmpId(empId));
            
            // 批量插入工作经历
            empExprMapper.batchInsert(emp.getExprList());
        }
    }
    
    /**
     * 批量删除员工
     */
    @Override
    @Transactional
    public void delete(List<Integer> ids) {
        // 删除员工工作经历
        ids.forEach(empExprMapper::deleteByEmpId);
        
        // 删除员工基本信息
        empMapper.deleteByIds(ids);
    }

    /**
     * 员工登录
     * 注意：这里仍然使用明文密码查询，但我们应该在 Controller 或 Security 配置中处理密码比较
     */
    @Override
    public Emp login(String username, String password) {
        // 这里不应该直接使用明文密码查询，安全验证应在其他地方完成
        // Emp emp = empMapper.getByUsernameAndPassword(username, password);
        // return emp;
        // 暂时返回 null，登录逻辑需要结合 Spring Security 重构
        return null; 
    }

    /**
     * 员工注册
     */
    @Override
    @Transactional
    public boolean register(Emp emp) {
        // 1. 检查用户名是否存在
        if (empMapper.findByUsername(emp.getUsername()) != null) {
            return false; // 用户名已存在
        }

        // 2. 对密码进行加密
        String encodedPassword = passwordEncoder.encode(emp.getPassword());
        emp.setPassword(encodedPassword);

        // 3. 设置默认值 (如果需要，例如创建时间和更新时间)
        // emp.setCreateTime(LocalDateTime.now());
        // emp.setUpdateTime(LocalDateTime.now());
        // 注意：Mapper 中的 Insert SQL 似乎已经处理了时间

        // 4. 插入数据库
        empMapper.insert(emp); 
        // 理论上 insert 后 emp 对象会包含生成的 ID (如果配置了 useGeneratedKeys)
        
        // 注意：注册时不处理工作经历 (EmpExpr)，可以后续添加

        return true; // 注册成功
    }

    /**
     * 修改密码
     */
    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        // 1. 根据用户名获取用户信息 (包含当前加密的密码)
        Emp currentUser = empMapper.findByUsername(username);
        if (currentUser == null) {
            // 理论上，如果用户已登录，这里不应该为 null
            return false; 
        }

        // 2. 验证旧密码是否正确
        if (!passwordEncoder.matches(oldPassword, currentUser.getPassword())) {
            return false; // 旧密码错误
        }

        // 3. 加密新密码
        String encodedNewPassword = passwordEncoder.encode(newPassword);

        // 4. 更新数据库中的密码
        empMapper.updatePassword(username, encodedNewPassword);

        return true; // 修改成功
    }
} 