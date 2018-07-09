package com.yiban.controller;

import cn.yiban.open.Authorize;
import com.yiban.dto.ClassResult;
import com.yiban.entity.ClassTable;
import com.yiban.service.ClassService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Kuexun on 2018/7/7.
 */
@Controller
@RequestMapping("views")
public class LoginController {


    @Autowired
    private ClassService classService;
    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/index")
    public String index() {
        System.out.println("首页");
        return "index";
    }

    @RequestMapping("/gettable")
    @ResponseBody
    public ClassResult getTable(){
        System.out.println("获取表格");
        return classService.searchAllClass();
    }

}
