package com.example.examsys.form.ToService;

import com.example.examsys.entity.User;
import lombok.Data;

import java.util.List;

/**
 * @author: ximo
 * @date: 2022/5/16 19:49
 * @description: 用于创建课程
 */
@Data
public class CourseDTO {

    private String courseName;

    private List<User> studentList;

    private User teacher;
}
