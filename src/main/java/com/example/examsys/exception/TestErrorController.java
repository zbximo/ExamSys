package com.example.examsys.exception;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController

@RequestMapping("/error")
//继承springboot提供的ErrorController
public class TestErrorController implements ErrorController {

    @RequestMapping("/ok")
    @ResponseBody
    public Map<String, Object> noError() {
        //用Map容器返回信息
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code ", 200);
        map.put("msg", "正常，这是测试页面");

        return map;
    }

//这里不要加consumes="text/html;charset=UTF-8",不然不成功,有部分浏览器提交的是空值
    @RequestMapping( value = "",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String errorHtml4040(HttpServletRequest request, HttpServletResponse response) {
        //跳转到error 目录下的 404模板
        return "404错误,不存在";
    }
    @RequestMapping(value = "", consumes="application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  Map<String, Object> errorJson() {
        //用Map容器返回信息
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rspCode", 404);
        map.put("rspMsg", "不存在");
        return map;
    }


    @Override
    public String getErrorPath() {
        return null;
    }
}
