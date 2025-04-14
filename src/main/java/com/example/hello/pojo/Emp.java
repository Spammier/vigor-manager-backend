package com.example.hello.pojo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 员工实体类
 */
@Data
public class Emp {
    /**
     * 员工ID
     */
    private Integer id;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 登录密码
     */
    private String password;
    
    /**
     * 姓名
     */
    private String name;
    
    /**
     * 性别, 1: 男, 2: 女
     */
    private Integer gender;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 职位, 1: 班主任, 2: 讲师, 3: 学工主管, 4: 教研主管, 5: 咨询师
     */
    private Integer position;
    
    /**
     * 薪资
     */
    private Integer salary;
    
    /**
     * 头像路径
     */
    private String image;
    
    /**
     * 入职日期
     */
    private LocalDate hireDate;
    
    /**
     * 所属部门ID
     */
    private Integer deptId;
    
    /**
     * 部门名称（数据库表不存在该字段，关联查询获取）
     */
    private String deptName;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 最后操作时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 工作经历列表
     */
    private List<EmpExpr> exprList;
    
    // 手动添加缺失的getter/setter方法
    public List<EmpExpr> getExprList() {
        return exprList;
    }
    
    public void setExprList(List<EmpExpr> exprList) {
        this.exprList = exprList;
    }
    
    public Integer getId() {
        return id;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
} 