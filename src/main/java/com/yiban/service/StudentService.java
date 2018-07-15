package com.yiban.service;

import com.yiban.entity.ClassTable;
import com.yiban.entity.Student;
import com.yiban.mapper.StudentMapper;
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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Kuexun on 2018/7/8.
 */
@Service
public class StudentService {
    @Autowired
    private StudentMapper studentMapper;
    private Logger logger = LoggerFactory.getLogger(StudentService.class);

    public int modifyClass(Student student) {
        int i = studentMapper.hasStudent(student);
        System.out.println(i);
        if (i > 0) {
            i = studentMapper.modifyClass(student);
        }
        return i;
    }


    public void readExcel(MultipartFile file) {
        List<Student> studentList = new ArrayList<>();
        try {
            logger.info("上传的文件名：{}", file.getOriginalFilename());
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String day = format.format(date);
            String fileName = file.getOriginalFilename();
            String filePath = "D://TestLeave2//" + day + "//" + fileName;
            File excelFile = new File(filePath);
            FileUtils.copyInputStreamToFile(file.getInputStream(),excelFile);

            InputStream is = new FileInputStream(excelFile);
            try {
                String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
                //获取工作薄
                Workbook wb = null;
                if (fileType.equals("xls")) {
                    wb = new HSSFWorkbook(is);
                } else if (fileType.equals("xlsx")) {
                    wb = new XSSFWorkbook(is);
                } else {
                    return;
                }
                //读取第一个工作页sheet
                Sheet sheet = wb.getSheetAt(0);
                //第一行为标题
                for (Row row : sheet) {
                    for (Cell cell : row) {
                        //根据不同类型转化成字符串
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                    }
                    Student student = new Student();
                    //遍历每行中的每列
                    student.setStudentId(row.getCell(0).getStringCellValue());
                    student.setName(row.getCell(1).getStringCellValue());
                    student.setDepartment(row.getCell(2).getStringCellValue());
                    student.setClassName(row.getCell(3).getStringCellValue());
                    student.setYibanId(row.getCell(4).getStringCellValue());
                    student.setJudgeIsNewClassId(row.getCell(5).getStringCellValue());
                    System.out.println(student);
                    studentList.add(student);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            modifyStudentList(studentList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void modifyStudentList(List<Student> studentList) {
        for(Student student:studentList)
        {
            studentMapper.modifyClass(student);
        }
    }

    public String getName(String studentId) {
        return studentMapper.getName(studentId);
    }
}
