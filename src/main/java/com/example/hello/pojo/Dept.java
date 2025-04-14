package com.example.hello.pojo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 部门实体类
 */
@Data
public class Dept {
    /**
     * 部门ID
     */
    private Integer id;
    
    /**
     * 部门名称
     */
    private String name;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 最后操作时间
     */
    private LocalDateTime updateTime;
} 