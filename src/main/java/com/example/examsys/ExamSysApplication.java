package com.example.examsys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@SpringBootApplication
@EnableCaching
@EnableScheduling
@ServletComponentScan
public class ExamSysApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExamSysApplication.class, args);
    }

}
