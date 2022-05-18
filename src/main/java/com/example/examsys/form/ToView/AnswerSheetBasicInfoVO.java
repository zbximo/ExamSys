package com.example.examsys.form.ToView;

import com.example.examsys.entity.AnswerDetail;
import com.example.examsys.entity.Paper;
import com.example.examsys.entity.User;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

/**
 * @author: ximo
 * @date: 2022/5/18 12:27
 * @description: 老师查看一次考试的所有考生情况, 学生查看考试的情况
 */
@Data
public class AnswerSheetBasicInfoVO {

    private String answerSheetId;

    private User student;

    private Integer status;
}
