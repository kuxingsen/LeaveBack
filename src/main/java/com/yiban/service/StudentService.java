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
     * �޸ĵ���ѧ���İ༶
     * @param student ѧ����Ϣ��ֻ��ѧ�ź��°༶��
     * @return �޸ĵ�����
     */
    public int modifyClass(Student student) {
        int i = studentMapper.hasStudent(student);//�����ݿ���û�����ѧ����һ�����е�
//        System.out.println(i);
        if (i > 0) {
            i = studentMapper.modifyClass(student);//����ѧ���޸İ༶
        }
        return i;
    }

    /**
     * ��ȡexcel�ļ�����������༶��û�н���У�飩
     * @param file ������ļ������ļ���Ϊָ����ʽ��ѧ��ѧ�š�ѧ���������װ�id���°༶id��
     *             ʵ��ʹ�õ�ֻ��ѧ�ź��°༶����Ϊһ��excelֻ�����еĻ��ֵֹģ�
     */
    public IsSuccessResult readExcel(MultipartFile file,String access_token) {
        List<Student> studentList = new ArrayList<>();
        String msg="";
        try {
            String fileName = file.getOriginalFilename();
            logger.info("�ϴ����ļ�����{}", fileName);
            String filePath = WriteExcel.write(file);//�洢λ���д���ȶ
            File excelFile = null;
            if (filePath != null) {
                excelFile = new File(filePath);
            }else {
                msg = "�ļ��������";
                return new IsSuccessResult(-1,msg);
            }
            InputStream is = new FileInputStream(excelFile);
            try {
                String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
                //��ȡ������
                Workbook wb = null;
                if (fileType.equals("xls")) {
                    wb = new HSSFWorkbook(is);
                } else if (fileType.equals("xlsx")) {
                    wb = new XSSFWorkbook(is);
                } else {
                    msg = "�ļ�����xls��xlsx��ʽ";
                    return new IsSuccessResult(-1,msg);
                }
                //��ȡ��һ������ҳsheet
                Sheet sheet = wb.getSheetAt(0);
                //��һ��Ϊ����
                for (int i = 1;i < sheet.getLastRowNum()+1;i++)
                {
                    Row row = sheet.getRow(i);
                    for (Cell cell : row) {
                        if(cell != null)
                        {
                            //���ݲ�ͬ����ת�����ַ���
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            if(cell.getStringCellValue() == null || (cell.getStringCellValue()).equals("")){
                                msg += (1+row.getRowNum())+"��"+(cell.getColumnIndex()+1)+"��Ϊ��\t";//�յ�Ԫ
//                                System.out.println((1+row.getRowNum())+"��"+(cell.getColumnIndex()+1)+"��");
                            }
                        }else {
                            msg += (1+row.getRowNum())+"��"+(1+i)+"��Ϊ��\t";//�յ�Ԫ
//                            System.out.println((1+row.getRowNum())+"��"+(1+i)+"��");
                        }
                    }
                    Student student = new Student();
                    //����ÿ���е�ÿ��
                    student.setStudentId(row.getCell(0).getStringCellValue());//ѧ��
                    student.setName(row.getCell(1).getStringCellValue());//����
                    student.setYibanId(row.getCell(2).getStringCellValue());//�װ�id
                    student.setJudgeIsNewClassId(row.getCell(3).getStringCellValue());//�°༶id
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
            msg = "�ļ�����"+studentList.size()+"����¼���ɹ�����"+count+"��";
            return new IsSuccessResult(0,msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        msg="�ļ�����ʧ��";
        return new IsSuccessResult(-1,msg);
    }

    /**
     * �����޸�ѧ���༶
     * @param studentList ���޸ĵ�ѧ���б�
     */
    private int modifyStudentList(List<Student> studentList) {
        int count = 0;
        for(Student student:studentList)
        {
            if(0 == studentMapper.modifyClass(student))
            {
                logger.error(student.getStudentId()+"���ʧ��");
            }else {
                count++;
            }
        }
        return count;
    }

    /**
     * ͨ��ѧ�Ż�ȡѧ������
     * @param studentId ѧ��
     * @return ��Ӧ������
     */
    public String getName(String studentId) {
        return studentMapper.getName(studentId);//��student�л�ȡ����
    }
}
