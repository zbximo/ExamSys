package com.example.examsys.controller;

import com.example.examsys.entity.Course;
import com.example.examsys.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: ximo
 * @date: 2022/5/18 11:27
 * @description:
 */
@RestController
public class TestController {
    @Autowired
    private CourseRepository courseRepository;
//    @RequestMapping("/test")
//    public List<Course> test(String id){
//        return courseRepository.t(id);
//    }
}
