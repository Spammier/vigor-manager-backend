package com.example.hello.service.impl;

import com.example.hello.common.PageResult;
import com.example.hello.mapper.ClassInfoMapper;
import com.example.hello.mapper.StudentInfoMapper;
import com.example.hello.pojo.ClassInfo;
import com.example.hello.service.ClassInfoService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 引入事务
import java.time.LocalDate;
import java.util.List;

@Service
public class ClassInfoServiceImpl implements ClassInfoService {

    private static final Logger log = LoggerFactory.getLogger(ClassInfoServiceImpl.class);

    @Autowired
    private ClassInfoMapper classInfoMapper;
    
    @Autowired // 注入 StudentInfoMapper 用于删除前检查
    private StudentInfoMapper studentInfoMapper;

    @Override
    public PageResult<ClassInfo> list(String className, String classStatus, LocalDate startTimeStart, LocalDate startTimeEnd, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<ClassInfo> list = classInfoMapper.list(className, classStatus, startTimeStart, startTimeEnd);
        Page<ClassInfo> p = (Page<ClassInfo>) list;
        return new PageResult<>(p.getTotal(), p.getResult());
    }

    @Override
    public ClassInfo findById(Integer id) {
        return classInfoMapper.findById(id);
    }

    @Override
    @Transactional // 为 add 方法显式添加事务管理
    public void add(ClassInfo classInfo) {
        log.info("准备新增班级: {}", classInfo);
        try {
            classInfoMapper.insert(classInfo);
            log.info("班级新增 Mapper 调用完成, 生成的 ID (如果配置了): {}", classInfo.getId());
        } catch (Exception e) {
            log.error("新增班级时发生数据库异常", e);
            // 可以选择向上抛出自定义异常
            throw new RuntimeException("新增班级失败", e);
        }
    }

    @Override
    @Transactional // 为 update 方法也加上，保持一致性
    public void update(ClassInfo classInfo) {
        log.info("准备更新班级: {}", classInfo);
         try {
            classInfoMapper.update(classInfo);
            log.info("班级更新 Mapper 调用完成.");
        } catch (Exception e) {
            log.error("更新班级时发生数据库异常", e);
            throw new RuntimeException("更新班级失败", e);
        }
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        log.info("准备删除班级 ID: {}", id);
        // 删除班级前检查是否有学员
        int studentCount = studentInfoMapper.countByClassId(id);
        if (studentCount > 0) {
            log.warn("尝试删除班级 ID: {}, 但班级下存在 {} 名学员，无法删除。", id, studentCount);
            // 抛出业务异常，让 Controller 层能捕获并返回友好提示
            // 你需要创建一个自定义业务异常类，或者暂时用 RuntimeException
            throw new RuntimeException("班级下存在学员，无法删除"); 
        }
        
        try {
            classInfoMapper.deleteById(id);
            log.info("班级 ID: {} 删除 Mapper 调用完成。", id);
        } catch (Exception e) {
             log.error("删除班级 ID: {} 时发生数据库异常", id, e);
            throw new RuntimeException("删除班级失败", e);
        }
    }
} 