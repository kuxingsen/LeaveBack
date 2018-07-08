package com.yiban.controller;

import cn.yiban.open.Authorize;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Kuexun on 2018/7/7.
 */
@Controller
@RequestMapping("views")
public class LoginController {

    private static final String appKey = "ba040fceea1043db";
    private static final String appSecret = "c5e8b3715ee3ee7ca68d315fe59f1113";
    private static final String callbackUrl = "http://localhost:8080/views/index";

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
        System.out.println("主页");
        //授权验证
        Authorize authorize = new Authorize(appKey, appSecret);
        HttpSession session = request.getSession();
        String code = request.getParameter("code");
        logger.info("获取的code：{}", code);
        if (code == null || "".equals(code.trim())) {
            String url = authorize.forwardurl(callbackUrl, "test", Authorize.DISPLAY_TAG_T.WEB);
            return "redirect:" + url;
        } else {
            JSONObject object = JSONObject.fromObject(authorize.querytoken(code, callbackUrl));
            logger.info("授权后的toke：{}", object);
            if (object.has("access_token")) {
                String userId = object.getString("userid");
                if(userId.equals("8118009")){
                    String accessToken = object.getString("access_token");
                    session.setAttribute("yiban_id", userId);
                    session.setAttribute("accessToken", accessToken);
                    return "index";
                }
                return "/false";
            } else {
                return "/false";
            }
        }
    }

}
