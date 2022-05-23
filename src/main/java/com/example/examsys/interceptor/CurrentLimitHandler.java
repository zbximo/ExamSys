package com.example.examsys.interceptor;

import cn.yueshutong.springbootstartercurrentlimiting.annotation.CurrentLimiter;
import cn.yueshutong.springbootstartercurrentlimiting.handler.CurrentAspectHandler;
import com.example.examsys.result.Response;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

/**
 * @author: ximo
 * @date: 2022/5/23 19:15
 * @description:
 */

@Component
public class CurrentLimitHandler implements CurrentAspectHandler {
    @Override
    public Response around(ProceedingJoinPoint pjp, CurrentLimiter rateLimiter) {
        return new Response("500", "系统繁忙，请稍后重试");
    }
}

