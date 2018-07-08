package com.yiban.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Kuexun on 2018/7/7.
 */
@Controller
@RequestMapping("views")
public class LoginController {
    @RequestMapping("/index")
    public String index()
    {
        System.out.println("主页");
        return "index";
    }
}
