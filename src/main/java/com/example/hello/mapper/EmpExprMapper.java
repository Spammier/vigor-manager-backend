package com.example.hello.mapper;

import com.example.hello.pojo.EmpExpr;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 员工工作经历Mapper接口
 */
@Mapper
public interface EmpExprMapper {
    
    /**
     * 根据员工ID查询工作经历
     */
    @Select("SELECT * FROM emp_expr WHERE emp_id = #{empId}")
    List<EmpExpr> getByEmpId(Integer empId);
    
    /**
     * 新增工作经历
     */
    @Insert("INSERT INTO emp_expr(emp_id, company, position, start_date, end_date) " +
            "VALUES(#{empId}, #{company}, #{position}, #{startDate}, #{endDate})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(EmpExpr empExpr);
    
    /**
     * 批量插入工作经历
     */
    void batchInsert(@Param("list") List<EmpExpr> list);
    
    /**
     * 根据员工ID删除工作经历
     */
    @Delete("DELETE FROM emp_expr WHERE emp_id = #{empId}")
    void deleteByEmpId(Integer empId);
    
    /**
     * 根据ID删除工作经历
     */
    @Delete("DELETE FROM emp_expr WHERE id = #{id}")
    void deleteById(Integer id);
} 