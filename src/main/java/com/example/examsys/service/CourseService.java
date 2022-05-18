package com.example.examsys.service;

import com.example.examsys.entity.Course;
import com.example.examsys.form.ToService.CourseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CourseService {

    String addCourse(CourseDTO courseDTO);

    String deleteCourse(String id);

    Course findById(String id);

    List<Course> getCoursesTaught(String teacherId);

    List<Course> getCoursesLearned(String studentId);

}
