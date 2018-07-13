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
            i.setStatus(statusChange(status));//��״̬��ת������
        }
        Result<Info> result = new Result<>();
        result.setTotal(infoList.size());
        result.setRows(infoList);
        return result;
    }



    private String statusChange(String status){
        //��-1���ܾ���0������ˣ�1����ͬ��δ���٣�2�������٣�
        String tmp = null;
        switch (status) {
            case "-1":
                tmp = "�ܾ�";
                break;
            case "0":
                tmp = "�����";
                break;
            case "1":
                tmp = "��ͬ��δ����";
                break;
            case "2":
                tmp = "������";
                break;
        }
        return tmp;
    }

    public Boolean exportInformation (String path) {
        List<Info> infoList = leaveMapper.getAllInfo();
    	/*
		 * ���ñ�ͷ����Excelÿ��ȡ��(���������ȡ�����ݱ�д)
		 */
        String[] tableHeader = {"���","ѧ��", "����","ѧԺ", "�༶","��ϵ��ʽ","��ʼ����","��ֹ����","��ٽ���","���ԭ��","���״̬"};
    	/*
		 * ����Ķ����Կ������ñ�д
		 */
        short  cellNumber = (short) tableHeader.length;// �������
        HSSFWorkbook workbook = new HSSFWorkbook();;// ����һ��excel
        HSSFCell cell = null; // Excel����
        HSSFRow row = null; // Excel����
        HSSFCellStyle style = workbook.createCellStyle(); // ���ñ�ͷ������
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFCellStyle style1 = workbook.createCellStyle(); // ������������
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont font = workbook.createFont(); // ��������
        HSSFSheet sheet = workbook.createSheet("sheet1"); // ����һ��sheet
        HSSFHeader header = sheet.getHeader();// ����sheet��ͷ
        try {
            /**
             * �����Ƿ�ȡ�����ݣ�����header��Ϣ
             *
             */
            if(infoList.size()<1)
            {
                header.setCenter("��������");
            }
            else {
                header.setCenter("ѧ�������Ϣ��");
                row = sheet.createRow(0);
                row.setHeight((short) 400);
                for (int k = 0; k < cellNumber; k++) {
                    cell = row.createCell(k);// ������0�е�k��
                    cell.setCellValue(tableHeader[k]);// ���õ�0�е�k�е�ֵ
                    sheet.setColumnWidth(k, 8000);// �����еĿ��
                    font.setColor(HSSFFont.COLOR_NORMAL); // ���õ�Ԫ���������ɫ.
                    font.setFontHeight((short) 350); // ���õ�Ԫ����߶�
                    style1.setFont(font);// ����������
                    cell.setCellStyle(style1);
                }

                int index=0;
                // ��excel�������
                for (int i=0;i<infoList.size();i++)
                {
                    Info information;
                    information =infoList.get(i);
                    row = sheet.createRow((short) (i + 1));// ������i+1��
                    row.setHeight((short) 400);// �����и�
                    cell=row.createCell(0);
                    cell.setCellValue(index++);
                    cell.setCellStyle(style);// ���÷��

                    String tmp=null;
                    int tmp2 = 0;
                    if((tmp = information.getStudentId())!=null)
                    {
                        cell=row.createCell(1);
                        cell.setCellValue(tmp);
                        cell.setCellStyle(style);// ���÷��
                    }
                    if((tmp = information.getStudentName())!=null)
                    {
                        cell=row.createCell(2);
                        cell.setCellValue(tmp);
                        cell.setCellStyle(style);// ���÷��
                    }
                    if((tmp = information.getDepartment())!=null)
                    {
                        cell =row.createCell(3);
                        cell.setCellValue(tmp);
                        cell.setCellStyle(style);// ���÷��
                    }
                    if((tmp = information.getClassName())!=null)
                    {
                        cell =row.createCell(4);
                        cell.setCellValue(tmp);
                        cell.setCellStyle(style);// ���÷��
                    }
                    if((tmp = information.getPhone()) != null)
                    {
                        cell =row.createCell(5);
                        cell.setCellValue(tmp);
                        cell.setCellStyle(style);// ���÷��
                    }
                    if((tmp = information.getBeginTime()) != null)
                    {
                        cell =row.createCell(6);
                        cell.setCellValue(tmp);
                        cell.setCellStyle(style);// ���÷��
                    }
                    if((tmp = information.getEndTime()) !=null)
                    {
                        cell =row.createCell(7);
                        cell.setCellValue(tmp);
                        cell.setCellStyle(style);// ���÷��
                    }
                    if((tmp2 = information.getNumber())>0)
                    {
                        cell =row.createCell(8);
                        cell.setCellValue(tmp2);
                        cell.setCellStyle(style);// ���÷��
                    }
                    if((tmp = information.getReason())!=null)
                    {
                        cell =row.createCell(9);
                        cell.setCellValue(information.getReason());
                        cell.setCellStyle(style);// ���÷��
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
        // ����һ��HttpServletResponse����
        FileOutputStream out = null;
        // ����һ�����������
        try {
            // ��ʼ��HttpServletResponse����
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
            i.setStatus(statusChange(status));//��״̬��ת������
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
            i.setStatus(statusChange(status));//��״̬��ת������
        }
        Result<Info> result = new Result<>();
        result.setTotal(infoList.size());
        result.setRows(infoList);
        return result;
    }
}
