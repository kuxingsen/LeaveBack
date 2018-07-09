package com.yiban.controller;

import com.yiban.dto.ClassResult;
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
    public String addClass()
    {
        System.out.println("��Ӱ༶");
        return "add";
    }
    @RequestMapping("/getDean")
    @ResponseBody
    public ClassResult getDean(String deanId, HttpSession session)
    {
        System.out.println("��ð���������");
        //readExcel();
        return classService.getDean(deanId,session);
    }

    @RequestMapping("/file")
    public void readExcel(@RequestParam(value = "file", required = false) MultipartFile file) {
            if (file != null && !file.isEmpty()) {
                classService.readExcel(file);
            }
    }
}
