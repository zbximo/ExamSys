package com.example.examsys.form.ToService;

import com.example.examsys.entity.User;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @author: ximo
 * @date: 2022/5/16 19:49
 * @description:
 */
@Data
public class CourseDTO {

    private String courseName;

    private List<User> studentIdList;

    private User teacherId;
}
