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
    @Id
    private String courseId;
    /**
     * 课程名
     */
    @Field("class_name")
    private String courseName;

    /**
     * 学号列表
     */
    @Field("student_ids")
    private List<String> studentIdList;

    @DBRef
    @Field("teacher_id")
    private User userId;

}
