package com.example.examsys.service.impl;

import com.example.examsys.entity.Course;
import com.example.examsys.form.ToService.CourseDTO;
import com.example.examsys.repository.CourseRepository;
import com.example.examsys.service.CourseService;
import com.example.examsys.utils.Constants;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: ximo
 * @date: 2022/5/16 19:41
 * @description:
 */
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Override
    public String addCourse(CourseDTO courseDTO) {
        Course course = new Course();
        BeanUtils.copyProperties(courseDTO,course);
        String cId = new ObjectId().toString();
        course.setCourseId(cId);
        course.setIsClosed(Constants.COURSE_OPEN);
        courseRepository.save(course);
        System.out.println(course.toString());
        return cId;
    }
}
