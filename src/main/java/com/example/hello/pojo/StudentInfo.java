package com.example.hello.pojo;

import lombok.Data;
import java.time.LocalDate;

@Data
public class StudentInfo {
    private Integer id;
    private String name;
    private Integer gender;
    private String studentNumber;
    private String phone;
    private String qq;
    private String wechat;
    private String idNumber;
    private String address;
    private LocalDate birthday;
    private String education;
    private String graduateSchool;
    private String major;
    private LocalDate graduationDate;
    private String employmentStatus;
    private Integer classId; // 所属班级ID

    // Optional: Add field for class name if needed for display, fetched via join
    // private String className;
} 