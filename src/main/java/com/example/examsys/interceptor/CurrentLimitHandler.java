package com.example.examsys.interceptor;

import cn.yueshutong.springbootstartercurrentlimiting.annotation.CurrentLimiter;
import cn.yueshutong.springbootstartercurrentlimiting.handler.CurrentAspectHandler;
import com.example.examsys.exception.BusinessException;
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
    public Object around(ProceedingJoinPoint pjp, CurrentLimiter rateLimiter) {
        throw new BusinessException(500, "系统繁忙，请稍后重试");
    }
}

