package com.yiban.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 前端控制器,将网址对应映射到页面（基本没什么用）
 * Created by Kuexun on 2018/7/7.
 */
@Controller
@RequestMapping("views")
public class ViewsController {

    @RequestMapping("/index")
    public String index() {
//        System.out.println("首页");
        return "index";//views/index.html
    }
    @RequestMapping("/add")
    public String add()
    {
//        System.out.println("添加班级页面");
        return "add";//views/add.html
    }

    @RequestMapping("/show")
    public String show()
    {
//        System.out.println("请假记录");
        return "show";//views/show.html
    }
    @RequestMapping("/changecls")
    public String changecls()
    {
//        System.out.println("转专业");
        return "changecls";//views/changecls.html
    }
}
