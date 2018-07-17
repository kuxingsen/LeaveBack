package com.yiban.service;

import com.yiban.dto.AClassResult;
import com.yiban.dto.IsSuccessResult;
import com.yiban.dto.Result;
import com.yiban.entity.ClassTable;
import com.yiban.mapper.ClassMapper;
import com.yiban.utils.excel.WriteExcel;
import com.yiban.utils.yibanApi.User;
import net.sf.json.JSONObject;
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
public class ClassService {
    @Autowired
    private ClassMapper classMapper;
    private Logger logger = LoggerFactory.getLogger(ClassService.class);

    /**
     * ����ָ���༶��ŵĵ�����¼
     * @param classId �༶��ţ�Ϊclass�������,һ�㲻Ϊnull
     * @return �����༶��¼
     */
    public AClassResult searchClassById(String classId)
    {
        AClassResult aClassResult = classMapper.searchClassById(classId);//��class�����ð༶���ΪclassId�ļ�¼
        if(aClassResult != null)
        {
            aClassResult.setCode(0);
        }else {
            aClassResult = new AClassResult();//��ʱaClassResultΪnull�����Ա���newһ��
            aClassResult.setCode(-1);
        }
        return aClassResult;
    }

    /**
     * @param id �װ��ţ������Ǹ���Ա�������Ρ��೤��
     * @param access_token ���ڻ�ȡ�װ�id��Ӧ���ǳ�(https://openapi.yiban.cn/user/other)
     * @return ��Ӧ������
     */
    public String getName(String id, String access_token) {
        JSONObject object = JSONObject.fromObject(User.other(id,access_token));
//        System.out.println(object);
        if(object.get("status").equals("success")){
            return (String) (JSONObject.fromObject(object.get("info"))).get("yb_username");
        }else {
            return null;
        }
    }

    /**
     * ������Ӱ༶
     * @param classTableList �༶�б�
     * @return �ɹ���ӵ�����
     */
    private int addClassList(List<ClassTable> classTableList) {
        int count = 0;
        for (ClassTable c:classTableList)
        {
            if(0 == classMapper.addClass(c))
            {
                System.out.println(c.getClassId()+"���ʧ��");
            }else {
                count++;
            }
        }
        return count;
    }

    /**
     * ��ȡexcel�ļ�����������༶
     * @param file ������ļ������ļ���Ϊָ����ʽ���༶��ţ��༶���ƣ�����Ա�װ��ţ�����Ա�������ǳƣ����������α�ţ��������������೤��ţ��೤������
     *             ���ļ��ĵ�Ԫ����Ϊ�գ�����Ա�������Ρ��೤���װ�id��Ϊ��ʵid
     * @param access_token ���ڻ�ȡ�װ�id��Ӧ���ǳ�(https://openapi.yiban.cn/user/other)
     * @return �ɹ���ʧ��
     */
    public IsSuccessResult readExcel(MultipartFile file,String access_token) {
        List<ClassTable> classTableList = new ArrayList<>();
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
                for (Row row : sheet) {
                    if (row.getRowNum() == 0) continue;//���в�����
                    for (int i = 0;i < 8;i ++) {
                        Cell cell = row.getCell(i);
                        if(cell != null) {
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
                    //����ÿ���е�ÿ��
                    ClassTable classTable = setClassTable(row);

                    if(getName(classTable.getTeacherYibanId(),access_token) == null)
                    {
                        msg += (row.getRowNum()+1)+"��"+3+"�в���yiban_id\t";
                    }
                    if(getName(classTable.getDeanYiban_id(),access_token) == null)
                    {
                        msg += (row.getRowNum()+1)+"��"+5+"�в���yiban_id\t";
                    }
                    if(getName(classTable.getMonitorId(),access_token) == null)
                    {
                        msg += (row.getRowNum()+1)+"��"+7+"�в���yiban_id\t";
                    }
//                    System.out.println(classTable);
                    classTableList.add(classTable);
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
            int count = addClassList(classTableList);//��������һ��ӽ����ݿ�
            msg = "�ļ�����"+classTableList.size()+"����¼���ɹ�����"+count+"��";
            return new IsSuccessResult(0,msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        msg="�ļ�����ʧ��";
        return new IsSuccessResult(-1,msg);
    }

    /**
     * ��excel����һ�е�����������class��
     * @param row excel���һ��
     * @return �������ɵ�class����
     */
    private ClassTable setClassTable(Row row)
    {
        ClassTable classTable = new ClassTable();
        Cell cell;

        if((cell = row.getCell(0)) != null)
        {
            classTable.setClassId(cell.getStringCellValue());//�༶���
        }
        if((cell = row.getCell(1)) != null)
        {
            classTable.setClassName(cell.getStringCellValue());//�༶����
        }

        if((cell = row.getCell(2)) != null)
        {
            classTable.setTeacherYibanId(cell.getStringCellValue());//����Ա���
        }
        if((cell = row.getCell(3)) != null)
        {
            classTable.setTeacherName(cell.getStringCellValue());//����Ա����
        }

        if((cell = row.getCell(4)) != null)
        {
            classTable.setDeanYiban_id(cell.getStringCellValue());//�����α��
        }
        if((cell = row.getCell(5)) != null)
        {
            classTable.setDeanName(cell.getStringCellValue());//����������
        }

        if((cell = row.getCell(6)) != null)
        {
            classTable.setMonitorId(cell.getStringCellValue());//�೤���
        }
        if((cell = row.getCell(7)) != null)
        {
            classTable.setMonitorName(cell.getStringCellValue());//�೤����
        }

        return classTable;
    }
    /**
     * ��ȫclassTable���������ԣ�ͨ���װ�id��ȡ��Ӧ���ǳ�
     * @param classTable ��Ҫ��ȫ�İ༶����
     * @param access_token ���� https://openapi.yiban.cn/user/other ��Ҫ��¼�ߵ�access_token
     * @return ��ȫ�õ�classTable
     */
    public ClassTable setClassTableName(ClassTable classTable,String access_token)
    {
        //��ȡǰ�˴������װ���
        String teacherId= classTable.getTeacherYibanId();
        String monitorId = classTable.getMonitorId();
        String deanId = classTable.getDeanYiban_id();
        //���ô��װ�����õ��װ��ǳ�
        classTable.setTeacherName(getName(teacherId,access_token));
        classTable.setDeanName(getName(deanId,access_token));
        classTable.setMonitorName(getName(monitorId,access_token));
        return classTable;
    }
    public int addClass(ClassTable classTable) {
        System.out.println(classTable);
        return classMapper.addClass(classTable);
    }

    /**
     * �����ݸ��µ����ݿ�
     * @param classTable classʵ��
     * @return �޸ĵ��������ɹ�Ϊ1��ʧ��Ϊ0��
     */
    public int modifyClass(ClassTable classTable) {
        return classMapper.modifyClass(classTable);
    }

    /**
     * ɾ�����ݿ��ָ���༶������������ɾ���������߼�ɾ����
     * @param classId ��ɾ���İ༶��id
     * @return ɾ����������һ��Ϊ1��
     */
    public int deleteClass(String classId) {
        return classMapper.deleteClass(classId);
    }

    /**
     * ��ȡָ��classId�İ༶��¼
     * @param classId �༶��ţ�Ϊclass�������,һ�㲻Ϊnull
     * @return �༶������
     */
    public Result<ClassTable> searchClassByClassId(String classId) {
        ClassTable classTable = classMapper.searchClassByClassId(classId);//��class�����ð༶���ΪclassId�ļ�¼
        Result<ClassTable> classTableResult = new Result<>();
        if(classTable != null){
            List<ClassTable> classTableList = new ArrayList<>();
            classTableList.add(classTable);
            classTableResult.setRows(classTableList);
        }else {
            classTableResult.setRows(null);
        }
        return classTableResult;
    }

    /**
     * ��ȡָ��ҳ�������İ༶��¼
     * @param count ÿҳ��ʾ������
     * @param pageIndex ҳ��ҳ��
     * @return �༶������
     */
    public Result<ClassTable> searchAllClassInPage(int count,int pageIndex) {
        int begin = count * (pageIndex-1);//�����ݿ�ĵ�begin����ʼ����
        List<ClassTable> classTableList = classMapper.searchAllClassInPage(begin,count);//��class��������begin��ʼ��ǰcount����¼
        int total = classMapper.getAllClassTotal();//��ȡ���а༶��¼������������ǰ�˷�ҳ��
        Result<ClassTable> result = new Result<>();
        if(classTableList != null)//һ�㲻Ϊ��
        {
            result.setTotal(total);
            result.setRows(classTableList);
        }else {
            result.setTotal(-1);
        }
        return result;
    }

    /**
     * ��ȡ��Ӧ�İ༶����
     * @param classId �༶���
     * @return �༶����
     */
    public String getClassName(String classId) {
        return classMapper.getClassName(classId);//��class���л�ȡָ��classId�İ༶����
    }
}
