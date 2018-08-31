package com.yiban.controller;

import com.yiban.dto.IsSuccessResult;
import com.yiban.dto.NameResult;
import com.yiban.entity.Student;
import com.yiban.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

/**
 * 转专业学生的操作
 * Created by Kuexun on 2018/7/14.
 */
@Controller
@RequestMapping("student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    private Logger logger = LoggerFactory.getLogger(StudentController.class);

    /**
     * 修改单个学生的班级（/views/changecls.html）
     * @param student 学生信息（只有学号和新班级）
     * @return 成功或失败
     */
    @RequestMapping("/changecls")
    @ResponseBody
    public IsSuccessResult modifyClass(Student student)
    {
        int i = studentService.modifyClass(student);//修改班级
        IsSuccessResult isSuccessResult = new IsSuccessResult();
        if(i > 0){
            isSuccessResult.setCode(0);
            isSuccessResult.setMsg("学生修改班级成功");
        }else {
            isSuccessResult.setCode(-1);
            isSuccessResult.setMsg("学生修改班级失败，没有这个学生");
        }
        return isSuccessResult;
    }

    @RequestMapping("/file")
    @ResponseBody
    public IsSuccessResult readExcel(@RequestParam(value = "file", required = false) MultipartFile file, HttpSession session) {
        if (file != null && !file.isEmpty()) {
            String access_token = (String) session.getAttribute("accessToken");
            IsSuccessResult i = studentService.readExcel(file,access_token);//读取excel
            if(i.getCode() == -1){
                logger.error("文件出现错误：{}", i.getMsg());
            }
            return i;
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
