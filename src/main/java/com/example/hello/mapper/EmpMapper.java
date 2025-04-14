package com.example.hello.mapper;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.hello.pojo.Emp;

/**
 * 员工Mapper接口
 */
@Mapper
public interface EmpMapper {
    
    /**
     * 员工分页查询
     */
    List<Emp> list(@Param("name") String name,
                  @Param("gender") Integer gender,
                  @Param("begin") LocalDate begin,
                  @Param("end") LocalDate end);
    
    /**
     * 查询全部员工
     */
    @Select("SELECT e.*, d.name AS deptName FROM emp e LEFT JOIN dept d ON e.dept_id = d.id ORDER BY e.update_time DESC")
    List<Emp> listAll();
    
    /**
     * 根据ID查询员工
     */
    @Select("SELECT e.*, d.name AS deptName FROM emp e LEFT JOIN dept d ON e.dept_id = d.id WHERE e.id = #{id}")
    Emp getById(Integer id);
    
    /**
     * 新增员工
     */
    @Insert("INSERT INTO emp(username, password, name, gender, phone, position, salary, image, hire_date, dept_id, create_time, update_time) " +
            "VALUES(#{username}, #{password}, #{name}, #{gender}, #{phone}, #{position}, #{salary}, #{image}, #{hireDate}, #{deptId}, " +
            "CONVERT_TZ(NOW(), '+00:00', '+08:00'), CONVERT_TZ(NOW(), '+00:00', '+08:00'))")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Emp emp);
    
    /**
     * 更新员工
     */
    @Update("UPDATE emp SET username = #{username}, name = #{name}, gender = #{gender}, " +
            "phone = #{phone}, position = #{position}, salary = #{salary}, image = #{image}, " +
            "hire_date = #{hireDate}, dept_id = #{deptId}, password = #{password}, " +
            "update_time = CONVERT_TZ(NOW(), '+00:00', '+08:00') " +
            "WHERE id = #{id}")
    void update(Emp emp);
    
    /**
     * 根据ID列表批量删除员工
     */
    void deleteByIds(@Param("ids") List<Integer> ids);

    /**
     * 根据用户名查询员工
     */
    @Select("SELECT * FROM emp WHERE username = #{username}")
    Emp findByUsername(String username);

    /**
     * 根据用户名更新密码
     * @param username 用户名
     * @param newPassword 加密后的新密码
     */
    @Update("UPDATE emp SET password = #{newPassword}, update_time = CONVERT_TZ(NOW(), '+00:00', '+08:00') WHERE username = #{username}")
    void updatePassword(@Param("username") String username, @Param("newPassword") String newPassword);
} 