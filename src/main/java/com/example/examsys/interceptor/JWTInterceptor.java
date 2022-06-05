package com.example.examsys.interceptor;

/**
 * @author: ximo
 * @date: 2022/5/19 20:59
 * @description:
 */

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.examsys.entity.User;
import com.example.examsys.utils.JWTUtils;
import com.example.examsys.utils.LocalUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public class JWTInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        //获取请求头中的令牌
        String token = request.getHeader("token");
        System.out.println(request.getMethod());
        try {
            //验证令牌
            System.out.println(token);
            JWTUtils.verify(token);
            User user = JWTUtils.getUser(token);
            LocalUser.USER.set(user);
            //放行请求
            return true;
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
            map.put("msg", "无效签名");
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            map.put("msg", "token过期");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "token无效");
        }
        //设置状态
        map.put("state", false);
        //将map 转为json jackson 返回信息
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }

    /**
     * 关闭当前线程
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LocalUser.USER.remove();
    }
}

