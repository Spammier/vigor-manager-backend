package com.example.hello.controller;

import com.example.hello.common.PageResult;
import com.example.hello.common.Result;
import com.example.hello.pojo.Emp;
import com.example.hello.pojo.PasswordChangeRequest;
import com.example.hello.service.EmpService;
import com.example.hello.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 员工Controller
 */
@RestController
@RequestMapping("/api")
public class EmpController {
    
    private static final Logger log = LoggerFactory.getLogger(EmpController.class);
    
    @Autowired
    private EmpService empService;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtUtils jwtUtils;
    
    /**
     * 员工分页查询
     */
    @GetMapping("/emps")
    public Result<PageResult<Emp>> list(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer gender,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        
        PageResult<Emp> pageResult = empService.list(name, gender, begin, end, page, pageSize);
        return Result.success(pageResult);
    }
    
    /**
     * 查询全部员工
     */
    @GetMapping("/emps/list")
    public Result<List<Emp>> listAll() {
        List<Emp> empList = empService.listAll();
        return Result.success(empList);
    }
    
    /**
     * 根据ID查询员工
     */
    @GetMapping("/emps/{id}")
    public Result<Emp> getById(@PathVariable Integer id) {
        Emp emp = empService.getById(id);
        return Result.success(emp);
    }
    
    /**
     * 新增员工
     */
    @PostMapping("/emps")
    public Result<Void> add(@RequestBody Emp emp) {
        empService.add(emp);
        return Result.success();
    }
    
    /**
     * 更新员工
     */
    @PutMapping("/emps")
    public Result<Void> update(@RequestBody Emp emp) {
        empService.update(emp);
        return Result.success();
    }
    
    /**
     * 批量删除员工
     */
    @DeleteMapping("/emps")
    public Result<Void> delete(@RequestParam String ids) {
        // 将逗号分隔的ID字符串转换为Integer集合
        List<Integer> idList = Arrays.stream(ids.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        
        empService.delete(idList);
        return Result.success();
    }

    /**
     * 员工注册
     * 注意：为了简单起见，注册时只接收 username 和 password。
     * 其他字段（name, gender 等）可以在后续的"更新用户信息"功能中完善。
     * 或者，可以修改 Emp 类，创建一个专门用于注册的 DTO (Data Transfer Object)。
     */
    @PostMapping("/register")
    public Result<Void> register(@RequestBody Emp emp) {
        // 基本输入校验 (添加 name 校验)
        if (emp.getUsername() == null || emp.getUsername().isEmpty() || 
            emp.getPassword() == null || emp.getPassword().isEmpty() ||
            emp.getName() == null || emp.getName().isEmpty()) { // 检查 name 是否为空
            return Result.error("用户名、密码和姓名不能为空");
        }

        // 检查用户名是否存在等逻辑 (不变)
        boolean success = empService.register(emp);

        if (success) {
            return Result.success();
        } else {
            return Result.error("用户名已存在");
        }
    }

    /**
     * 员工登录
     * !!! 重要: 当前实现仅用于提供端点，不执行安全验证 !!!
     * 真实的登录逻辑需要集成 Spring Security (AuthenticationManager, UserDetailsService, PasswordEncoder).
     */
    @PostMapping("/login")
    public Result<?> login(@RequestBody Emp loginEmp) { 
        log.info("接收到登录请求: /api/login, 用户名: {}", loginEmp.getUsername());
        
        // 基本输入校验
        if (loginEmp.getUsername() == null || loginEmp.getUsername().isEmpty() || loginEmp.getPassword() == null || loginEmp.getPassword().isEmpty()) {
            log.warn("登录请求校验失败：用户名或密码为空");
            return Result.error("用户名和密码不能为空");
        }

        try {
            // 1. 调用 AuthenticationManager 的 authenticate 方法进行认证
            //    这会触发 UserDetailsServiceImpl 的 loadUserByUsername
            //    并使用 PasswordEncoder 比较密码
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginEmp.getUsername(), loginEmp.getPassword())
            );

            // 2. 如果认证成功，将认证信息存入 SecurityContextHolder
            //    (对于无状态应用，这一步可能不是必须的，取决于你的会话管理策略，比如 JWT)
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 认证成功，生成 JWT
            String jwt = jwtUtils.generateJwtToken(authentication);
            
            // 将 JWT 包装在 Map 或 DTO 中返回给客户端
            Map<String, String> response = new HashMap<>();
            response.put("token", jwt);

            log.info("登录成功: 用户名={}", loginEmp.getUsername());
            return Result.success(response); // 返回包含 Token 的成功响应

        } catch (AuthenticationException e) {
            log.warn("登录失败: 用户名={}, 错误: {}", loginEmp.getUsername(), e.getMessage());
            return Result.error("用户名或密码错误");
        }
    }

    /**
     * 修改当前登录用户的密码
     */
    @PutMapping("/password") // 使用 PUT 方法表示更新资源
    public Result<?> changePassword(@RequestBody PasswordChangeRequest request) {
        // 基本输入校验
        if (request.getOldPassword() == null || request.getOldPassword().isEmpty() ||
            request.getNewPassword() == null || request.getNewPassword().isEmpty()) {
            return Result.error("旧密码和新密码不能为空");
        }

        // 获取当前登录用户的用户名
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else if (principal instanceof String) { // 有时 principal 可能直接是用户名字符串
            username = (String) principal;
        } else {
            // 无法获取用户信息，可能是匿名访问或配置问题
            return Result.error("无法获取当前用户信息");
        }

        // 调用 Service 修改密码
        boolean success = empService.changePassword(username, request.getOldPassword(), request.getNewPassword());

        if (success) {
            return Result.success("密码修改成功");
        } else {
            return Result.error("旧密码错误");
        }
    }
} 