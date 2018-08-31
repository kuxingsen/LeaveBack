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
     * 返回指定班级编号的单条记录
     * @param classId 班级编号，为class表的主键,一般不为null
     * @return 单条班级记录
     */
    public AClassResult searchClassById(String classId)
    {
        AClassResult aClassResult = classMapper.searchClassById(classId);//从class表里获得班级编号为classId的记录
        if(aClassResult != null)
        {
            aClassResult.setCode(0);
        }else {
            aClassResult = new AClassResult();//此时aClassResult为null，所以必须new一个
            aClassResult.setCode(-1);
        }
        return aClassResult;
    }

    /**
     * @param id 易班编号，可能是辅导员、班主任、班长的
     * @param access_token 用于获取易班id相应的昵称(https://openapi.yiban.cn/user/other)
     * @return 相应的名称
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
     * 批量添加班级
     * @param classTableList 班级列表
     * @return 成功添加的条数
     */
    private int addClassList(List<ClassTable> classTableList) {
        int count = 0;
        for (ClassTable c:classTableList)
        {
            if(0 == classMapper.addClass(c))
            {
                logger.error(c.getClassId()+"添加失败");
            }else {
                count++;
            }
        }
        return count;
    }

    /**
     * 读取excel文件，批量导入班级
     * @param file 导入的文件，该文件需为指定格式（班级编号，班级名称，辅导员易班编号，辅导员姓名（昵称？），班主任编号，班主任姓名，班长编号，班长姓名）
     *             且文件的单元格不能为空，辅导员、班主任、班长的易班id需为真实id
     * @param access_token 用于获取易班id相应的昵称(https://openapi.yiban.cn/user/other)
     * @return 成功或失败
     */
    public IsSuccessResult readExcel(MultipartFile file,String access_token) {
        List<ClassTable> classTableList = new ArrayList<>();
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
                for (Row row : sheet) {
                    if (row.getRowNum() == 0) continue;//首行不导入
                    for (int i = 0;i < 8;i ++) {
                        Cell cell = row.getCell(i);
                        if(cell != null) {
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
                    //遍历每行中的每列
                    ClassTable classTable = setClassTable(row);

                    if(getName(classTable.getTeacherYibanId(),access_token) == null)
                    {
                        msg += (row.getRowNum()+1)+"行"+3+"列不是yiban_id\t";
                    }
                    if(getName(classTable.getDeanYiban_id(),access_token) == null)
                    {
                        msg += (row.getRowNum()+1)+"行"+5+"列不是yiban_id\t";
                    }
                    if(getName(classTable.getMonitorId(),access_token) == null)
                    {
                        msg += (row.getRowNum()+1)+"行"+7+"列不是yiban_id\t";
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
            int count = addClassList(classTableList);//将数据逐一添加进数据库
            msg = "文件共有"+classTableList.size()+"条记录，成功导入"+count+"条";
            return new IsSuccessResult(0,msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        msg="文件导入失败";
        return new IsSuccessResult(-1,msg);
    }

    /**
     * 将excel表中一行的数据填充进新class类
     * @param row excel表的一行
     * @return 已填充完成的class对象
     */
    private ClassTable setClassTable(Row row)
    {
        ClassTable classTable = new ClassTable();
        Cell cell;

        if((cell = row.getCell(0)) != null)
        {
            classTable.setClassId(cell.getStringCellValue());//班级编号
        }
        if((cell = row.getCell(1)) != null)
        {
            classTable.setClassName(cell.getStringCellValue());//班级名称
        }

        if((cell = row.getCell(2)) != null)
        {
            classTable.setTeacherYibanId(cell.getStringCellValue());//辅导员编号
        }
        if((cell = row.getCell(3)) != null)
        {
            classTable.setTeacherName(cell.getStringCellValue());//辅导员姓名
        }

        if((cell = row.getCell(4)) != null)
        {
            classTable.setDeanYiban_id(cell.getStringCellValue());//班主任编号
        }
        if((cell = row.getCell(5)) != null)
        {
            classTable.setDeanName(cell.getStringCellValue());//班主任名称
        }

        if((cell = row.getCell(6)) != null)
        {
            classTable.setMonitorId(cell.getStringCellValue());//班长编号
        }
        if((cell = row.getCell(7)) != null)
        {
            classTable.setMonitorName(cell.getStringCellValue());//班长名称
        }

        return classTable;
    }
    /**
     * 补全classTable的名字属性，通过易班id获取相应的昵称
     * @param classTable 需要补全的班级对象
     * @param access_token 调用 https://openapi.yiban.cn/user/other 需要登录者的access_token
     * @return 补全好的classTable
     */
    public ClassTable setClassTableName(ClassTable classTable,String access_token)
    {
        //获取前端传来的易班编号
        String teacherId= classTable.getTeacherYibanId();
        String monitorId = classTable.getMonitorId();
        String deanId = classTable.getDeanYiban_id();
        //设置从易班网获得的易班昵称
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
     * 将数据更新到数据库
     * @param classTable class实体
     * @return 修改的条数（成功为1，失败为0）
     */
    public int modifyClass(ClassTable classTable) {
        return classMapper.modifyClass(classTable);
    }

    /**
     * 删除数据库的指定班级（这里是物理删除，考虑逻辑删除）
     * @param classId 需删除的班级的id
     * @return 删除的条数（一般为1）
     */
    public int deleteClass(String classId) {
        return classMapper.deleteClass(classId);
    }

    /**
     * 获取指定classId的班级记录
     * @param classId 班级编号，为class表的主键,一般不为null
     * @return 班级表结果集
     */
    public Result<ClassTable> searchClassByClassId(String classId) {
        ClassTable classTable = classMapper.searchClassByClassId(classId);//从class表里获得班级编号为classId的记录
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
     * 获取指定页数条数的班级记录
     * @param count 每页显示的条数
     * @param pageIndex 页面页码
     * @return 班级表结果集
     */
    public Result<ClassTable> searchAllClassInPage(int count,int pageIndex) {
        int begin = count * (pageIndex-1);//从数据库的第begin条开始查找
        List<ClassTable> classTableList = classMapper.searchAllClassInPage(begin,count);//从class表里获得以begin开始的前count条记录
        int total = classMapper.getAllClassTotal();//获取所有班级记录的条数（便于前端分页）
        Result<ClassTable> result = new Result<>();
        if(classTableList != null)//一般不为空
        {
            result.setTotal(total);
            result.setRows(classTableList);
        }else {
            result.setTotal(-1);
        }
        return result;
    }

    /**
     * 获取相应的班级名称
     * @param classId 班级编号
     * @return 班级名称
     */
    public String getClassName(String classId) {
        return classMapper.getClassName(classId);//从class表中获取指定classId的班级名称
    }
}
