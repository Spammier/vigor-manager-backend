package com.example.hello.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 登录响应VO
 */
@Data
@Builder
public class LoginVO {
    private Integer id;
    private String username;
    private String name;
    private String token;
} 