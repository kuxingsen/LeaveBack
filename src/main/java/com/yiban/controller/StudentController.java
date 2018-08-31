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
 * תרҵѧ���Ĳ���
 * Created by Kuexun on 2018/7/14.
 */
@Controller
@RequestMapping("student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    private Logger logger = LoggerFactory.getLogger(StudentController.class);

    /**
     * �޸ĵ���ѧ���İ༶��/views/changecls.html��
     * @param student ѧ����Ϣ��ֻ��ѧ�ź��°༶��
     * @return �ɹ���ʧ��
     */
    @RequestMapping("/changecls")
    @ResponseBody
    public IsSuccessResult modifyClass(Student student)
    {
        int i = studentService.modifyClass(student);//�޸İ༶
        IsSuccessResult isSuccessResult = new IsSuccessResult();
        if(i > 0){
            isSuccessResult.setCode(0);
            isSuccessResult.setMsg("ѧ���޸İ༶�ɹ�");
        }else {
            isSuccessResult.setCode(-1);
            isSuccessResult.setMsg("ѧ���޸İ༶ʧ�ܣ�û�����ѧ��");
        }
        return isSuccessResult;
    }

    @RequestMapping("/file")
    @ResponseBody
    public IsSuccessResult readExcel(@RequestParam(value = "file", required = false) MultipartFile file, HttpSession session) {
        if (file != null && !file.isEmpty()) {
            String access_token = (String) session.getAttribute("accessToken");
            IsSuccessResult i = studentService.readExcel(file,access_token);//��ȡexcel
            if(i.getCode() == -1){
                logger.error("�ļ����ִ���{}", i.getMsg());
            }
            return i;
        }
        return new IsSuccessResult(-1,"�ļ������ڻ��ļ�Ϊ��");
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
