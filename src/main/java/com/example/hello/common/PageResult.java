package com.example.hello.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页结果包装类
 */
@Data
@NoArgsConstructor
public class PageResult<T> {
    private Long total; // 总记录数
    private List<T> rows; // 当前页数据列表
    
    public PageResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }
} 