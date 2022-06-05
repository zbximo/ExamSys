package com.example.examsys.form.ToView;

import com.example.examsys.entity.User;
import lombok.Data;

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
