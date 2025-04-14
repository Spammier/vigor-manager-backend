package com.example.hello.exception;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.hello.common.Result;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理SQL唯一约束异常
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result<String> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
        String errorMessage = ex.getMessage();
        
        // 处理手机号重复异常
        if (errorMessage.contains("Duplicate entry") && errorMessage.contains("phone")) {
            return Result.error("手机号已存在，请更换其他手机号");
        }
        
        // 处理用户名重复异常
        if (errorMessage.contains("Duplicate entry") && errorMessage.contains("username")) {
            return Result.error("用户名已存在，请更换其他用户名");
        }
        
        // 其他数据库约束异常
        return Result.error("数据库约束错误：" + errorMessage);
    }
    
    /**
     * 处理Spring的DuplicateKeyException
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public Result<String> handleDuplicateKeyException(DuplicateKeyException ex) {
        String errorMessage = ex.getCause().getMessage();
        
        // 处理手机号重复异常
        if (errorMessage.contains("Duplicate entry") && errorMessage.contains("phone")) {
            return Result.error("手机号已存在，请更换其他手机号");
        }
        
        // 处理用户名重复异常
        if (errorMessage.contains("Duplicate entry") && errorMessage.contains("username")) {
            return Result.error("用户名已存在，请更换其他用户名");
        }
        
        // 其他数据库约束异常
        return Result.error("数据库约束错误：" + errorMessage);
    }
    
    /**
     * 处理日期解析异常
     */
    @ExceptionHandler(java.time.format.DateTimeParseException.class)
    public Result<String> handleDateTimeParseException(java.time.format.DateTimeParseException ex) {
        return Result.error("日期格式错误，请使用正确的日期格式：" + ex.getMessage());
    }
    
    /**
     * 处理其他未知异常
     */
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception ex) {
        ex.printStackTrace();
        return Result.error("系统繁忙，请稍后再试");
    }
} 