package com.yiban.service;

import com.yiban.dto.AClassResult;
import com.yiban.dto.AllClassResult;
import com.yiban.entity.ClassTable;
import com.yiban.mapper.ClassMapper;
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

    public AllClassResult searchAllClass()
    {
        List<ClassTable> classTableList = classMapper.searchAllClass();
        AllClassResult allClassResult = new AllClassResult();
        allClassResult.setTotal(classTableList.size());
        allClassResult.setRows(classTableList);
        return allClassResult;
    }
    public AClassResult searchClassById(String classId)
    {
        AClassResult aClassResult = classMapper.searchClassById(classId);
        if(aClassResult != null)
        {
            aClassResult.setCode(0);
        }else {
            aClassResult.setCode(-1);
        }
        return aClassResult;
    }

    public String getName(String deanId, String access_token) {
        System.out.println(access_token);
        JSONObject object = JSONObject.fromObject(User.other(deanId,access_token));
        System.out.println(object);
        if(object.get("status").equals("success")){
            return (String) (JSONObject.fromObject(object.get("info"))).get("yb_username");
        }else {
            return null;
        }
    }

    public boolean addClassList(List<ClassTable> classTableList) {
        for (ClassTable c:classTableList)
        {
            classMapper.addClass(c);
        }
        return true;
    }

    public void readExcel(MultipartFile file) {
        List<ClassTable> classTableList = new ArrayList<>();
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
                        ClassTable classTable = new ClassTable();
                        //遍历每行中的每列
                        classTable.setClassId(row.getCell(0).getStringCellValue());
                        classTable.setClassName(row.getCell(1).getStringCellValue());

                        classTable.setDeanYiban_id(row.getCell(2).getStringCellValue());
                        classTable.setDeanName(row.getCell(3).getStringCellValue());

                        classTable.setTeacherYibanId(row.getCell(4).getStringCellValue());
                        classTable.setTeacherName(row.getCell(5).getStringCellValue());

                        classTable.setMonitorId(row.getCell(6).getStringCellValue());
                        classTable.setMonitorName(row.getCell(7).getStringCellValue());
                        System.out.println(classTable);
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
                addClassList(classTableList);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 补全classTable的名字属性
     * @param classTable
     * @param access_token
     * @return
     */
    public ClassTable setClassTableName(ClassTable classTable,String access_token)
    {
        String teacherId= classTable.getTeacherYibanId();
        String monitorId = classTable.getMonitorId();
        String deanId = classTable.getDeanYiban_id();
        classTable.setTeacherName(getName(teacherId,access_token));
        classTable.setDeanName(getName(deanId,access_token));
        classTable.setMonitorName(getName(monitorId,access_token));
        return classTable;
    }
    public int addClass(ClassTable classTable) {
        System.out.println(classTable);
        return classMapper.addClass(classTable);
    }

    public int modifyClass(ClassTable classTable) {
        return classMapper.modifyClass(classTable);
    }

    public int deleteClass(String classId) {
        return classMapper.deleteClass(classId);
    }
}
