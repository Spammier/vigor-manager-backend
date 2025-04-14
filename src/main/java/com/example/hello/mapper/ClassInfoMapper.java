package com.example.hello.mapper;

import com.example.hello.pojo.ClassInfo;
import org.apache.ibatis.annotations.*;
import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ClassInfoMapper {

    /**
     * 分页及条件查询班级列表
     * @param className 班级名称 (可选)
     * @param classStatus 状态 (可选)
     * @param startTimeStart 开班时间范围开始 (可选)
     * @param startTimeEnd 开班时间范围结束 (可选)
     * @return 班级列表
     */
    List<ClassInfo> list(@Param("className") String className,
                         @Param("classStatus") String classStatus,
                         @Param("startTimeStart") LocalDate startTimeStart,
                         @Param("startTimeEnd") LocalDate startTimeEnd);

    /**
     * 根据ID查询班级信息
     */
    @Select("SELECT * FROM class_info WHERE id = #{id}")
    ClassInfo findById(Integer id);

    /**
     * 新增班级
     */
    @Insert("INSERT INTO class_info (class_name, classroom, major_direction, start_time, graduate_time, class_status, head_teacher, subject) " +
            "VALUES (#{className}, #{classroom}, #{majorDirection}, #{startTime}, #{graduateTime}, #{classStatus}, #{headTeacher}, #{subject})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(ClassInfo classInfo);

    /**
     * 更新班级信息
     */
    // 使用动态 SQL (需要 XML 或 Provider) 来处理非空字段更新会更好，这里暂时全量更新
    @Update("UPDATE class_info SET class_name=#{className}, classroom=#{classroom}, major_direction=#{majorDirection}, " +
            "start_time=#{startTime}, graduate_time=#{graduateTime}, class_status=#{classStatus}, " +
            "head_teacher=#{headTeacher}, subject=#{subject}, update_time=NOW() WHERE id = #{id}")
    void update(ClassInfo classInfo);

    /**
     * 根据ID删除班级
     */
    @Delete("DELETE FROM class_info WHERE id = #{id}")
    void deleteById(Integer id);

    /**
     * 批量删除班级 (如果需要)
     */
    // void deleteByIds(@Param("ids") List<Integer> ids); 
} 