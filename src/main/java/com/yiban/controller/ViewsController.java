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
        System.out.println("��ҳ");
        return "index";
    }
    @RequestMapping("/add")
    public String add()
    {
        System.out.println("��Ӱ༶ҳ��");
        return "add";
    }

    @RequestMapping("/show")
    public String show()
    {
        System.out.println("��ټ�¼");
        return "show";
    }
    @RequestMapping("/changecls")
    public String changecls()
    {
        System.out.println("תרҵ");
        return "changecls";
    }
}
