package com.example.hello.common;

import lombok.Data;

/**
 * 统一响应结果类
 */
@Data
public class Result<T> {
    /**
     * 响应码，0 表示成功，非 0 表示失败 (例如 1 表示通用错误)
     */
    private Integer code;
    
    /**
     * 响应信息
     */
    private String msg;
    
    /**
     * 响应数据
     */
    private T data;
    
    public Result() {
    }
    
    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    
    /**
     * 成功响应
     */
    public static <T> Result<T> success() {
        return new Result<>(0, "操作成功", null);
    }
    
    /**
     * 成功响应（带数据）
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(0, "操作成功", data);
    }
    
    /**
     * 失败响应
     */
    public static <T> Result<T> error(String msg) {
        return new Result<>(1, msg, null);
    }
} 