package com.yiban.mapper;

import com.yiban.dto.Result;
import com.yiban.entity.Info;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Kuexun on 2018/7/10.
 */
@Repository
public interface LeaveMapper {
    List<Info> getAllInfo();

    List<Info> searchInfoByStudentId(String studentId);

    List<Info> getAllInfoInPage(@Param("begin") int begin, @Param("count") int count);

    int getAllLeaveTotal();
}
