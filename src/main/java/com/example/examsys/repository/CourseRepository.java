package com.example.examsys.repository;

import com.example.examsys.entity.Course;
import com.example.examsys.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface CourseRepository extends MongoRepository<Course,String> {
    List<Course> findByStudentIdListContains(User user);
}
