package com.yiban.controller;

import cn.yiban.open.Authorize;
import com.yiban.entity.AppContent;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.yiban.entity.AppContent.*;

/**
 * ����������������·�������session���Ҳ���yiban_id����Ҫ������Ȩ������������������˺Ŷ����ܵ���
 * ֻҪ�����޸ļ�������
 * Created by Kuexun on 2018/6/23.
 */
public class AllInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(AllInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HttpSession session = httpServletRequest.getSession();
        String yiban_id = (String) session.getAttribute("yiban_id");
        if(yiban_id == null || yiban_id.equals(""))
        {
            Authorize authorize = new Authorize(appKey, appSecret);
            String code = httpServletRequest.getParameter("code");
            logger.info("��ȡ��code��{}", code);
            if (code == null || "".equals(code.trim())) {
                String url = authorize.forwardurl(callbackUrl, "test", Authorize.DISPLAY_TAG_T.WEB);
                httpServletResponse.sendRedirect(url);
                return false;
            } else {
                JSONObject object = JSONObject.fromObject(authorize.querytoken(code, callbackUrl));
                logger.info("��Ȩ���toke��{}", object);
                if (object.has("access_token")) {
                    String userId = object.getString("userid");
                    if(userId.equals("8118009")){
                        String accessToken = object.getString("access_token");
                        session.setAttribute("yiban_id", userId);
                        session.setAttribute("accessToken", accessToken);
                        httpServletRequest.getRequestDispatcher("/views/index").forward(httpServletRequest,httpServletResponse);
                        return false;
                    }
                    return false;
                } else {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
