package com.yiban.controller;

import com.yiban.dto.ClassResult;
import com.yiban.dto.IsSuccessResult;
import com.yiban.dto.nameResult.DeanNameResult;
import com.yiban.dto.nameResult.MonitorNameResult;
import com.yiban.dto.nameResult.TeacherNameResult;
import com.yiban.entity.ClassTable;
import com.yiban.service.ClassService;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.List;

/**
 * Created by Kuexun on 2018/7/7.
 */
@Controller
@RequestMapping("class")
public class ClassController {
    @Autowired
    private ClassService classService;
    private Logger logger = LoggerFactory.getLogger(ClassController.class);

    @RequestMapping("/add")
    public String add()
    {
        System.out.println("添加班级");
        return "add";
    }
    @RequestMapping("/addClass")
    @ResponseBody
    public IsSuccessResult addClass(ClassTable classTable,HttpSession session)
    {
        System.out.println("添加班级");
        System.out.println(classTable);
        IsSuccessResult msg = null;
        String access_token = (String) session.getAttribute("accessToken");
        int r = classService.addClass(classTable,access_token);
        if(r > 0){
            msg = new IsSuccessResult(0,"添加成功");
        }else {
            msg = new IsSuccessResult(-1,"添加失败");
        }
        return msg;
    }

    @RequestMapping("/file")
    @ResponseBody
    public IsSuccessResult readExcel(@RequestParam(value = "file", required = false) MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            classService.readExcel(file);
            return new IsSuccessResult(0,"成功导入数据");
        }
        return new IsSuccessResult(-1,"文件不存在或文件为空");
    }

    @RequestMapping("/getDean")
    @ResponseBody
    public DeanNameResult getDean(String deanId, HttpSession session)
    {
        System.out.println("获得班主任姓名");
        String access_token = (String) session.getAttribute("accessToken");
        String name = classService.getName(deanId,access_token);
        DeanNameResult deanNameResult = new DeanNameResult();
        if(name == null)
        {
            deanNameResult.setCode(-1);
        }else {
            deanNameResult.setCode(0);
            deanNameResult.setDeanName(name);
        }
        return deanNameResult;
    }
    @RequestMapping("/getMonitor")
    @ResponseBody
    public MonitorNameResult getMonitor(String deanId, HttpSession session)
    {
        System.out.println("获得班长姓名");
        String access_token = (String) session.getAttribute("accessToken");
        String name = classService.getName(deanId,access_token);
        MonitorNameResult monitorNameResult = new MonitorNameResult();
        if(name == null)
        {
            monitorNameResult.setCode(-1);
        }else {
            monitorNameResult.setCode(0);
            monitorNameResult.setMonitorName(name);
        }
        return monitorNameResult;
    }
    @RequestMapping("/getTeacher")
    @ResponseBody
    public TeacherNameResult getTeacher(String deanId, HttpSession session)
    {
        System.out.println("获得班主任姓名");
        String access_token = (String) session.getAttribute("accessToken");
        String name = classService.getName(deanId,access_token);
        TeacherNameResult teacherNameResult = new TeacherNameResult();
        if(name == null)
        {
            teacherNameResult.setCode(-1);
        }else {
            teacherNameResult.setCode(0);
            teacherNameResult.setTeacherName(name);
        }
        return teacherNameResult;
    }

    @RequestMapping("/modify")
    public String modify() {
        return "modify";
    }
    @RequestMapping("/modifyClass")
    @ResponseBody
    public IsSuccessResult modifyClass(ClassTable classTable,HttpSession session)
    {
        System.out.println("添加班级");
        System.out.println(classTable);
        IsSuccessResult msg = null;
        String access_token = (String) session.getAttribute("accessToken");
        int r = classService.addClass(classTable,access_token);
        if(r > 0){
            msg = new IsSuccessResult(0,"添加成功");
        }else {
            msg = new IsSuccessResult(-1,"添加失败");
        }
        return msg;
    }
}
