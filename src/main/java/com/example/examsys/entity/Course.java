package com.example.examsys.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @author: ximo
 * @date: 2022/5/14 17:04
 * @description:
 */
@Data
@Document(collection = "Course")
public class Course {
    /**
     * 课程ID
     */
    @Id
    private String courseId;
    /**
     * 课程名
     */
    @Field("course_name")
    private String courseName;

    /**
     * 课程学生学号列表
     */
    @DBRef
    @Field("student_ids")
    private List<User> studentList;

    /**
     * 上课教师
     */
    @DBRef
    @Field("teacher_id")
    private User teacher;

    @Field("course_closed")
    private Integer isClosed;

}
