package com.example.hello.mapper;

import com.example.hello.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 部门Mapper接口
 */
@Mapper
public interface DeptMapper {

    /**
     * 查询所有部门
     */
    @Select("SELECT * FROM dept")
    List<Dept> list();

    /**
     * 根据ID查询部门
     */
    @Select("SELECT * FROM dept WHERE id = #{id}")
    Dept getById(Integer id);

    /**
     * 添加部门
     */
    @Insert("INSERT INTO dept(name) VALUES(#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void add(Dept dept);

    /**
     * 修改部门
     */
    @Update("UPDATE dept SET name = #{name} WHERE id = #{id}")
    void update(Dept dept);

    /**
     * 删除部门
     */
    @Delete("DELETE FROM dept WHERE id = #{id}")
    void delete(Integer id);
} 