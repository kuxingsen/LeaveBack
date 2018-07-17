package com.yiban.mapper;

import com.yiban.dto.AClassResult;
import com.yiban.dto.Result;
import com.yiban.entity.ClassTable;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Kuexun on 2018/7/7.
 */
@Repository
public interface ClassMapper {
    List<ClassTable> searchAllClass();

    //用于修改页面时的回显，从class表里获得班级编号为classId的记录
    AClassResult searchClassById(@Param("classId") String classId);
    //用于查找班级时的回显，从class表里获得班级编号为classId的记录
    ClassTable searchClassByClassId(String classId);


    //查找学生对应的辅导员的易班ID
    String searchTeacherByStudentId(String id);
    //查找学生对应的班主任
    String searchDeanByStudentId(String id);
    //查找教师对应的班级
    List<String> searchTeacher(String id);


    int addClass(ClassTable c);

    int modifyClass(ClassTable classTable);

    int deleteClass(@Param("classId") String classId);

    List<ClassTable> searchAllClassInPage(@Param("begin") int begin, @Param("count") int count);

    int getAllClassTotal();

    //从class表中获取指定classId的班级名称
    String getClassName(@Param("classId") String classId);
}
