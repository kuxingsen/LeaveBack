package com.yiban.controller;

import com.yiban.dto.Result;
import com.yiban.entity.Info;
import com.yiban.service.ClassService;
import com.yiban.service.LeaveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Kuexun on 2018/7/7.
 */
@Controller
@RequestMapping("leave")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;
    private Logger logger = LoggerFactory.getLogger(LeaveController.class);

    @RequestMapping("/getAllInfo")
    @ResponseBody
    public Result<Info> getAllInfo()
    {
        System.out.println("获取请假记录");
        return leaveService.getAllInfo();
    }
}
