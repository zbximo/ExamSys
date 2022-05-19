package com.example.examsys.controller;

import com.example.examsys.entity.Course;
import com.example.examsys.form.ToService.CourseDTO;
import com.example.examsys.result.ExceptionMsg;
import com.example.examsys.result.Response;
import com.example.examsys.result.ResponseData;
import com.example.examsys.service.CourseService;
import com.example.examsys.utils.LocalUser;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 添加课程
     *
     * @param courseDTO 课程信息
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseData addCourse(@RequestBody CourseDTO courseDTO) {
        String id = courseService.addCourse(courseDTO);
        logger.warn("create course id: {} ", id);
        return new ResponseData(ExceptionMsg.CREATE_SUCCESS, id);
    }

    /**
     * 删除课程
     *
     * @param id 课程ID
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Response deleteCourse(@RequestParam("id") String id) {
        String cid = courseService.deleteCourse(id);
        logger.warn("delete course id: {} ", cid);
        return new Response(ExceptionMsg.DELETE_SUCCESS);
    }

    /**
     * 通过课程ID查询课程，用于老师获取课程信息
     *
     * @param id 课程ID
     * @return
     */
    @RequestMapping(value = "/viewCourseById", method = RequestMethod.GET)
    public ResponseData viewCourseById(@RequestParam("id") String id) {
        Course course = courseService.findById(id);
        logger.warn("query course id: {}", id);
        return new ResponseData(ExceptionMsg.QUERY_SUCCESS, course);
    }

    /**
     * 可以用获取学生学的所有课
     *
     * @return
     */
    @RequestMapping(value = "/getCoursesLearned", method = RequestMethod.GET)
    public Response getCoursesLearned() {
        List<Course> courseList = courseService.getCoursesLearned(LocalUser.USER.get().getUserId());
        logger.warn("query courses learned");
        return new ResponseData(ExceptionMsg.QUERY_SUCCESS, courseList);
    }

    /**
     * 可用于获取老师教的所有课
     *
     * @return
     */
    @RequestMapping(value = "/getCoursesTaught", method = RequestMethod.GET)
    public Response getCoursesTaught() {
        List<Course> courseList = courseService.getCoursesTaught(LocalUser.USER.get().getUserId());
        logger.warn("query courses taught");
        return new ResponseData(ExceptionMsg.QUERY_SUCCESS, courseList);
    }
}
