package com.yiban.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Kuexun on 2018/7/7.
 */
@Controller
@RequestMapping("views")
public class ClassController {
    @RequestMapping("/add")
    public String addClass()
    {
        System.out.println("添加班级");
        return "add";
    }
}
