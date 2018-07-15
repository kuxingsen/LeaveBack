package com.yiban.controller;

import com.yiban.dto.IsSuccessResult;
import com.yiban.dto.NameResult;
import com.yiban.entity.Student;
import com.yiban.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 转专业学生的操作
 * Created by Kuexun on 2018/7/14.
 */
@Controller
@RequestMapping("student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @RequestMapping("/changecls")
    @ResponseBody
    public IsSuccessResult modifyClass(Student student)
    {
        int i = studentService.modifyClass(student);
        IsSuccessResult isSuccessResult = new IsSuccessResult();
        if(i > 0){
            isSuccessResult.setCode(0);
            isSuccessResult.setMsg("学生修改班级成功");
        }else {
            isSuccessResult.setCode(-1);
            isSuccessResult.setMsg("学生修改班级失败");
        }
        return isSuccessResult;
    }

    @RequestMapping("/file")
    @ResponseBody
    public IsSuccessResult readExcel(@RequestParam(value = "file", required = false) MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            studentService.readExcel(file);
            return new IsSuccessResult(0,"成功导入数据");
        }
        return new IsSuccessResult(-1,"文件不存在或文件为空");
    }
    @RequestMapping("/getName")
    @ResponseBody
    public NameResult getName(String uId)
    {
        String name = studentService.getName(uId);
        NameResult nameResult = new NameResult();
        if(name == null)
        {
            nameResult.setCode(-1);
        }else {
            nameResult.setCode(0);
            nameResult.setUName(name);
        }
        return nameResult;
    }
}
