package com.example.examsys.form.ToView;

import com.example.examsys.entity.User;
import lombok.Data;

import java.util.List;

/**
 * @author: ximo
 * @date: 2022/5/16 21:02
 * @description:
 */
@Data
public class CourseVO {

    private String courseId;

    private String courseName;

    private List<UserVO> studentIdList;

    private User teacherId;
}
