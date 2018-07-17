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

    /**
     * 修改单个学生的班级
     * @param student 学生信息（只有学号和新班级）
     * @return 修改的条数
     */
    public int modifyClass(Student student) {
        int i = studentMapper.hasStudent(student);//看数据库有没有这个学生，一般是有的
//        System.out.println(i);
        if (i > 0) {
            i = studentMapper.modifyClass(student);//根据学号修改班级
        }
        return i;
    }

    /**
     * 读取excel文件，批量导入班级（没有进行校验）
     * @param file 导入的文件，该文件需为指定格式（学生学号、学生姓名、易班id、新班级id）
     *             实际使用的只有学号和新班级（因为一份excel只有两列的话怪怪的）
     */
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
                for (int i = 1;i < sheet.getLastRowNum()+1;i++)
                {
                    Row row = sheet.getRow(i);
                    for (Cell cell : row) {
                        //根据不同类型转化成字符串
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                    }
                    Student student = new Student();
                    //遍历每行中的每列
                    student.setStudentId(row.getCell(0).getStringCellValue());//学号
                    student.setName(row.getCell(1).getStringCellValue());//姓名
                    student.setYibanId(row.getCell(2).getStringCellValue());//易班id
                    student.setJudgeIsNewClassId(row.getCell(3).getStringCellValue());//新班级id
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

    /**
     * 批量修改学生班级
     * @param studentList 需修改的学生列表
     */
    private void modifyStudentList(List<Student> studentList) {
        for(Student student:studentList)
        {
            studentMapper.modifyClass(student);
        }
    }

    /**
     * 通过学号获取学生姓名
     * @param studentId 学号
     * @return 相应的姓名
     */
    public String getName(String studentId) {
        return studentMapper.getName(studentId);//从student中获取姓名
    }
}
