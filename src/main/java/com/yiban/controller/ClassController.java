package com.yiban.controller;

import com.yiban.dto.AClassResult;
import com.yiban.dto.NameResult;
import com.yiban.dto.Result;
import com.yiban.dto.IsSuccessResult;
import com.yiban.entity.ClassTable;
import com.yiban.service.ClassService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

/**
 * Created by Kuexun on 2018/7/7.
 */
@Controller
@RequestMapping("class")
public class ClassController {
    @Autowired
    private ClassService classService;
    private Logger logger = LoggerFactory.getLogger(ClassController.class);


    @RequestMapping("/gettable")
    @ResponseBody
    public Result<ClassTable> getTable(int pageIndex,String search){
        System.out.println("��ȡ���");
        int count = 10;//ÿҳ��ʾ������
        if(search == null || search.equals("")){
            return classService.searchAllClassInPage(count,pageIndex);
        }else {
            return classService.searchClassByClassId(search);
        }
    }
    @RequestMapping("/getClass")
    @ResponseBody
    public AClassResult getAClass(String classId){
        System.out.println("��ȡ�༶"+classId);
        return classService.searchClassById(classId);
    }
    @RequestMapping("/getClassName")
    @ResponseBody
    public NameResult getClassName(String uId){
        System.out.println("��ȡ�༶"+uId);
        String name = classService.getClassName(uId);
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
    @RequestMapping("/addClass")
    @ResponseBody
    public IsSuccessResult addClass(ClassTable classTable,HttpSession session)
    {
        System.out.println("��Ӱ༶");
        System.out.println(classTable);
        IsSuccessResult msg = null;
        String access_token = (String) session.getAttribute("accessToken");
        classTable = classService.setClassTableName(classTable,access_token);
        int r = classService.addClass(classTable);
        if(r > 0){
            msg = new IsSuccessResult(0,"��ӳɹ�");
        }else {
            msg = new IsSuccessResult(-1,"���ʧ��");
        }
        return msg;
    }

    @RequestMapping("/file")
    @ResponseBody
    public IsSuccessResult readExcel(@RequestParam(value = "file", required = false) MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            classService.readExcel(file);
            return new IsSuccessResult(0,"�ɹ���������");
        }
        return new IsSuccessResult(-1,"�ļ������ڻ��ļ�Ϊ��");
    }

    @RequestMapping("/getName")
    @ResponseBody
    public NameResult getName(String uId, HttpSession session)
    {
        System.out.println("�������");
        String access_token = (String) session.getAttribute("accessToken");
        String name = classService.getName(uId,access_token);
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
    @RequestMapping("/modifyClass")
    @ResponseBody
    public IsSuccessResult modifyClass(ClassTable classTable,HttpSession session)
    {
        System.out.println("�޸İ༶");
        System.out.println(classTable);
        IsSuccessResult msg = null;
        //ǰ��δ����
        String access_token = (String) session.getAttribute("accessToken");
        classTable = classService.setClassTableName(classTable,access_token);
        int r = classService.modifyClass(classTable);
        if(r > 0){
            msg = new IsSuccessResult(0,"�޸ĳɹ�");
        }else {
            msg = new IsSuccessResult(-1,"�޸�ʧ��");
        }
        return msg;
    }

    @RequestMapping("/deleteClass/{id}")
    @ResponseBody
    public IsSuccessResult deleteClass(@PathVariable("id") String classId)
    {
        System.out.println("ɾ���༶"+classId);
        IsSuccessResult msg = null;
        //ǰ��δ����
        int r = classService.deleteClass(classId);
        if(r > 0){
            msg = new IsSuccessResult(0,"�޸ĳɹ�");
        }else {
            msg = new IsSuccessResult(-1,"�޸�ʧ��");
        }
        return msg;
    }

    @RequestMapping("/search/{classId}")
    @ResponseBody
    public Result<ClassTable> searchClassByClassId(@PathVariable("classId") String classId)
    {
        return classService.searchClassByClassId(classId);
    }
}
