package com.example.hello.pojo;

import lombok.Data;

import java.time.LocalDate;

/**
 * 员工工作经历实体类
 */
@Data
public class EmpExpr {
    /**
     * 经历ID
     */
    private Integer id;
    
    /**
     * 关联员工的ID
     */
    private Integer empId;
    
    /**
     * 公司名称
     */
    private String company;
    
    /**
     * 担任职位
     */
    private String position;
    
    /**
     * 开始日期
     */
    private LocalDate startDate;
    
    /**
     * 结束日期
     */
    private LocalDate endDate;
    
    // 手动添加setter方法
    public void setEmpId(Integer empId) {
        this.empId = empId;
    }
} 