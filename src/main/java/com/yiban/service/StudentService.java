package com.yiban.service;

import com.yiban.dto.IsSuccessResult;
import com.yiban.entity.ClassTable;
import com.yiban.entity.Student;
import com.yiban.mapper.StudentMapper;
import com.yiban.utils.excel.WriteExcel;
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
    public IsSuccessResult readExcel(MultipartFile file,String access_token) {
        List<Student> studentList = new ArrayList<>();
        String msg="";
        try {
            String fileName = file.getOriginalFilename();
            logger.info("上传的文件名：{}", fileName);
            String filePath = WriteExcel.write(file);//存储位置有待商榷
            File excelFile = null;
            if (filePath != null) {
                excelFile = new File(filePath);
            }else {
                msg = "文件保存错误";
                return new IsSuccessResult(-1,msg);
            }
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
                    msg = "文件不是xls或xlsx格式";
                    return new IsSuccessResult(-1,msg);
                }
                //读取第一个工作页sheet
                Sheet sheet = wb.getSheetAt(0);
                //第一行为标题
                for (int i = 1;i < sheet.getLastRowNum()+1;i++)
                {
                    Row row = sheet.getRow(i);
                    for (Cell cell : row) {
                        if(cell != null)
                        {
                            //根据不同类型转化成字符串
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            if(cell.getStringCellValue() == null || (cell.getStringCellValue()).equals("")){
                                msg += (1+row.getRowNum())+"行"+(cell.getColumnIndex()+1)+"列为空\t";//空单元
//                                System.out.println((1+row.getRowNum())+"行"+(cell.getColumnIndex()+1)+"列");
                            }
                        }else {
                            msg += (1+row.getRowNum())+"行"+(1+i)+"列为空\t";//空单元
//                            System.out.println((1+row.getRowNum())+"行"+(1+i)+"列");
                        }
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
            if(!msg.equals(""))
            {
                return new IsSuccessResult(-1,msg);
            }

            int count = modifyStudentList(studentList);
            msg = "文件共有"+studentList.size()+"条记录，成功导入"+count+"条";
            return new IsSuccessResult(0,msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        msg="文件导入失败";
        return new IsSuccessResult(-1,msg);
    }

    /**
     * 批量修改学生班级
     * @param studentList 需修改的学生列表
     */
    private int modifyStudentList(List<Student> studentList) {
        int count = 0;
        for(Student student:studentList)
        {
            if(0 == studentMapper.modifyClass(student))
            {
                logger.error(student.getStudentId()+"添加失败");
            }else {
                count++;
            }
        }
        return count;
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
