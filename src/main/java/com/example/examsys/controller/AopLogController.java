package com.example.examsys.controller;

/**
 * @author:Benjamin
 * @Date:2022/5/17 10:29
 **/
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AopLogController {
    @GetMapping("/aoptest")
    public String aVoid(){
        return "hello aop test";
    }
}
