package com.yiban.service;

import com.yiban.dto.Result;
import com.yiban.entity.ClassTable;
import com.yiban.entity.Info;
import com.yiban.mapper.LeaveMapper;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Kuexun on 2018/7/10.
 */
@Service
public class LeaveService {
    @Autowired
    private LeaveMapper leaveMapper;

    public Result<Info> getAllInfo() {
        List<Info> infoList = leaveMapper.getAllInfo();
        for(Info i:infoList)
        {
            String status = i.getStatus();
            i.setStatus(statusChange(status));//将状态码转成中文
        }
        Result<Info> result = new Result<>();
        result.setTotal(infoList.size());
        result.setRows(infoList);
        return result;
    }



    private String statusChange(String status){
        //（-1：拒绝，0：待审核，1：已同意未销假，2：已销假）
        String tmp = null;
        switch (status) {
            case "-1":
                tmp = "拒绝";
                break;
            case "0":
                tmp = "待审核";
                break;
            case "1":
                tmp = "已同意未销假";
                break;
            case "2":
                tmp = "已销假";
                break;
        }
        return tmp;
    }

    public Boolean exportInformation (String path) {
        List<Info> infoList = leaveMapper.getAllInfo();
    	/*
		 * 设置表头：对Excel每列取名(必须根据你取的数据编写)
		 */
        String[] tableHeader = {"序号","学号", "姓名","学院", "班级","联系方式","开始日期","截止日期","请假节数","请假原因","请假状态"};
    	/*
		 * 下面的都可以拷贝不用编写
		 */
        short  cellNumber = (short) tableHeader.length;// 表的列数
        HSSFWorkbook workbook = new HSSFWorkbook();;// 创建一个excel
        HSSFCell cell = null; // Excel的列
        HSSFRow row = null; // Excel的行
        HSSFCellStyle style = workbook.createCellStyle(); // 设置表头的类型
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFCellStyle style1 = workbook.createCellStyle(); // 设置数据类型
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont font = workbook.createFont(); // 设置字体
        HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
        HSSFHeader header = sheet.getHeader();// 设置sheet的头
        try {
            /**
             * 根据是否取出数据，设置header信息
             *
             */
            if(infoList.size()<1)
            {
                header.setCenter("查无资料");
            }
            else {
                header.setCenter("学生请假信息表");
                row = sheet.createRow(0);
                row.setHeight((short) 400);
                for (int k = 0; k < cellNumber; k++) {
                    cell = row.createCell(k);// 创建第0行第k列
                    cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
                    sheet.setColumnWidth(k, 8000);// 设置列的宽度
                    font.setColor(HSSFFont.COLOR_NORMAL); // 设置单元格字体的颜色.
                    font.setFontHeight((short) 350); // 设置单元字体高度
                    style1.setFont(font);// 设置字体风格
                    cell.setCellStyle(style1);
                }

                int index=0;
                // 给excel填充数据
                for (int i=0;i<infoList.size();i++)
                {
                    Info information;
                    information =infoList.get(i);
                    row = sheet.createRow((short) (i + 1));// 创建第i+1行
                    row.setHeight((short) 400);// 设置行高
                    cell=row.createCell(0);
                    cell.setCellValue(index++);
                    cell.setCellStyle(style);// 设置风格

                    String tmp=null;
                    int tmp2 = 0;
                    if((tmp = information.getStudentId())!=null)
                    {
                        cell=row.createCell(1);
                        cell.setCellValue(tmp);
                        cell.setCellStyle(style);// 设置风格
                    }
                    if((tmp = information.getStudentName())!=null)
                    {
                        cell=row.createCell(2);
                        cell.setCellValue(tmp);
                        cell.setCellStyle(style);// 设置风格
                    }
                    if((tmp = information.getDepartment())!=null)
                    {
                        cell =row.createCell(3);
                        cell.setCellValue(tmp);
                        cell.setCellStyle(style);// 设置风格
                    }
                    if((tmp = information.getClassName())!=null)
                    {
                        cell =row.createCell(4);
                        cell.setCellValue(tmp);
                        cell.setCellStyle(style);// 设置风格
                    }
                    if((tmp = information.getPhone()) != null)
                    {
                        cell =row.createCell(5);
                        cell.setCellValue(tmp);
                        cell.setCellStyle(style);// 设置风格
                    }
                    if((tmp = information.getBeginTime()) != null)
                    {
                        cell =row.createCell(6);
                        cell.setCellValue(tmp);
                        cell.setCellStyle(style);// 设置风格
                    }
                    if((tmp = information.getEndTime()) !=null)
                    {
                        cell =row.createCell(7);
                        cell.setCellValue(tmp);
                        cell.setCellStyle(style);// 设置风格
                    }
                    if((tmp2 = information.getNumber())>0)
                    {
                        cell =row.createCell(8);
                        cell.setCellValue(tmp2);
                        cell.setCellStyle(style);// 设置风格
                    }
                    if((tmp = information.getReason())!=null)
                    {
                        cell =row.createCell(9);
                        cell.setCellValue(information.getReason());
                        cell.setCellStyle(style);// 设置风格
                    }
                    if((tmp = information.getStatus()) !=null)
                    {
                        cell =row.createCell(10);
                        cell.setCellStyle(style);
                        tmp = statusChange(tmp);
                        cell.setCellValue(tmp);
                    }
                }

            }
        } catch (SecurityException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        // 创建一个HttpServletResponse对象
        FileOutputStream out = null;
        // 创建一个输出流对象
        try {
            // 初始化HttpServletResponse对象
            out = new FileOutputStream(path);
            workbook.write(out);
            out.flush();
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;

    }

    public Result<Info> searchInfoByStudentId(String studentId) {
        List<Info> infoList = leaveMapper.searchInfoByStudentId(studentId);
        for(Info i:infoList)
        {
            String status = i.getStatus();
            i.setStatus(statusChange(status));//将状态码转成中文
        }
        Result<Info> infoResult = new Result<>();
        infoResult.setRows(infoList);
        return infoResult;
    }

    public Result<Info> getAllInfoInPage(int count, int pageIndex) {
        int begin = count * (pageIndex-1);

        List<Info> infoList = leaveMapper.getAllInfoInPage(begin,count);
        for(Info i:infoList)
        {
            String status = i.getStatus();
            i.setStatus(statusChange(status));//将状态码转成中文
        }
        Result<Info> result = new Result<>();
        result.setTotal(infoList.size());
        result.setRows(infoList);
        return result;
    }
}
