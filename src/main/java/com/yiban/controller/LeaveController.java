package com.yiban.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Kuexun on 2018/7/7.
 */
@Controller
@RequestMapping("views")
public class LeaveController {
    @RequestMapping("/show")
    public String show()
    {
        System.out.println("请假记录");
        return "show";
    }
}
