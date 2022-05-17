package com.example.examsys.service;

import com.example.examsys.entity.Course;
import com.example.examsys.form.ToService.CourseDTO;
import org.springframework.stereotype.Service;

@Service
public interface CourseService {
    String addCourse(CourseDTO courseDTO);

    String deleteCourse(String id);

    Course findById(String id);


}
