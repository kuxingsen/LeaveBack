package com.yiban.mapper;

import com.yiban.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Kuexun on 2018/7/7.
 */
@Repository
public interface StudentMapper {
    int modifyClass(Student student);

    int hasStudent(Student student);

    int insert(Student student);

    String getName(@Param("studentId") String studentId);
}
