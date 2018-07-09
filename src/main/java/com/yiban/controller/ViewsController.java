package com.yiban.controller;

import com.yiban.service.ClassService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Kuexun on 2018/7/7.
 */
@Controller
@RequestMapping("views")
public class ViewsController {


    @Autowired
    private ClassService classService;
    private Logger logger = LoggerFactory.getLogger(ViewsController.class);

    @RequestMapping("/index")
    public String index() {
        System.out.println("首页");
        return "index";
    }
    @RequestMapping("/add")
    public String add()
    {
        System.out.println("添加班级页面");
        return "add";
    }

    @RequestMapping("/show")
    public String show()
    {
        System.out.println("请假记录");
        return "show";
    }
}
