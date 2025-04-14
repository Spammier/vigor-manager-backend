package com.example.hello.pojo;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ClassInfo {
    private Integer id; // 主键ID
    private String className; // 班级名称
    private String classroom; // 教室
    private String majorDirection; // 专业方向
    private LocalDate startTime; // 开班时间
    private LocalDate graduateTime; // 毕业时间
    private String classStatus; // 状态（在读/未开班/已开班）
    private LocalDateTime updateTime; // 最近更新时间
    private String headTeacher; // 班主任
    private String subject; // 学科
} 