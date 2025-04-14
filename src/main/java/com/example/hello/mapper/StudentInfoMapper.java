package com.example.hello.mapper;

import com.example.hello.pojo.StudentInfo;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface StudentInfoMapper {

    /**
     * 分页及条件查询学员列表
     * @param name 姓名 (可选)
     * @param studentNumber 学号 (可选)
     * @param education 学历 (可选)
     * @param classId 班级ID (可选)
     * @return 学员列表
     */
    // Consider adding JOIN with class_info if className is needed in the result
    List<StudentInfo> list(@Param("name") String name,
                           @Param("studentNumber") String studentNumber,
                           @Param("education") String education,
                           @Param("classId") Integer classId);

    /**
     * 根据ID查询学员信息
     */
    @Select("SELECT * FROM student_info WHERE id = #{id}")
    StudentInfo findById(Integer id);

    /**
     * 新增学员
     */
    @Insert("INSERT INTO student_info (name, gender, student_number, phone, qq, wechat, id_number, address, birthday, " +
            "education, graduate_school, major, graduation_date, employment_status, class_id) " +
            "VALUES (#{name}, #{gender}, #{studentNumber}, #{phone}, #{qq}, #{wechat}, #{idNumber}, #{address}, #{birthday}, " +
            "#{education}, #{graduateSchool}, #{major}, #{graduationDate}, #{employmentStatus}, #{classId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(StudentInfo studentInfo);

    /**
     * 更新学员信息
     */
    // Again, dynamic SQL is better for updates
    @Update("UPDATE student_info SET name=#{name}, gender=#{gender}, student_number=#{studentNumber}, phone=#{phone}, qq=#{qq}, " +
            "wechat=#{wechat}, id_number=#{idNumber}, address=#{address}, birthday=#{birthday}, education=#{education}, " +
            "graduate_school=#{graduateSchool}, major=#{major}, graduation_date=#{graduationDate}, " +
            "employment_status=#{employmentStatus}, class_id=#{classId} WHERE id = #{id}")
    void update(StudentInfo studentInfo);

    /**
     * 根据ID删除学员
     */
    @Delete("DELETE FROM student_info WHERE id = #{id}")
    void deleteById(Integer id);

    /**
     * 根据班级ID统计学员数量 (用于删除班级前的检查)
     */
    @Select("SELECT COUNT(*) FROM student_info WHERE class_id = #{classId}")
    int countByClassId(Integer classId);
} 