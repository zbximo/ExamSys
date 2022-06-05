package com.example.examsys.config;

import com.example.examsys.interceptor.JWTInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: ximo
 * @date: 2022/5/19 20:58
 * @description:
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor())
                //不放行
                .addPathPatterns("/**")
                //放行，
                .excludePathPatterns("/user/login", "/user/register");

    }

}
