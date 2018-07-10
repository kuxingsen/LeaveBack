package com.yiban.service;

import com.yiban.controller.ClassController;
import com.yiban.dto.Result;
import com.yiban.entity.ClassTable;
import com.yiban.entity.Info;
import com.yiban.mapper.LeaveMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
