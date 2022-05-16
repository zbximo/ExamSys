package com.example.examsys.controller;

import com.example.examsys.entity.Course;
import com.example.examsys.entity.User;
import com.example.examsys.form.ToService.CourseDTO;
import com.example.examsys.result.ExceptionMsg;
import com.example.examsys.result.ResponseData;
import com.example.examsys.service.CourseService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ximo
 * @date: 2022/5/16 19:40
 * @description:
 */
@RestController
@RequestMapping("/course")
public class CourseController {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(CourseController.class);
    @Autowired
    private CourseService courseService;
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseData addUser(@RequestBody CourseDTO courseDTO) {
        String id = courseService.addCourse(courseDTO);
        logger.warn("create course id: {} ", id);
        return new ResponseData(ExceptionMsg.CREATE_SUCCESS, id);
    }
}
