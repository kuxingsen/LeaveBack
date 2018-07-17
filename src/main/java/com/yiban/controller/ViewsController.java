package com.yiban.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ǰ�˿�����,����ַ��Ӧӳ�䵽ҳ�棨����ûʲô�ã�
 * Created by Kuexun on 2018/7/7.
 */
@Controller
@RequestMapping("views")
public class ViewsController {

    @RequestMapping("/index")
    public String index() {
//        System.out.println("��ҳ");
        return "index";//views/index.html
    }
    @RequestMapping("/add")
    public String add()
    {
//        System.out.println("��Ӱ༶ҳ��");
        return "add";//views/add.html
    }

    @RequestMapping("/show")
    public String show()
    {
//        System.out.println("��ټ�¼");
        return "show";//views/show.html
    }
    @RequestMapping("/changecls")
    public String changecls()
    {
//        System.out.println("תרҵ");
        return "changecls";//views/changecls.html
    }
}
