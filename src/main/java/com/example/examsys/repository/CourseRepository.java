package com.example.examsys.repository;

import com.example.examsys.entity.Course;
import com.example.examsys.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {
    /**
     * 返回学生列表有该用户ID的所有课程，即学的课
     *
     * @param user 带用户ID即可
     * @return
     */
    List<Course> findByStudentListContains(User user);

    /**
     * 返回课程教师为该用户ID的课程，即教的课
     *
     * @param id 用户ID
     * @return
     */
    List<Course> findByTeacher_UserId(String id);

    Course findByCourseId(String id);

    void deleteByCourseId(String id);
}
