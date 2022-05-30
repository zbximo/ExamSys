package com.example.examsys.form.ToView.statistics;

import com.example.examsys.entity.Question;
import lombok.Data;

import java.util.List;

/**
 * @author: ximo
 * @date: 2022/5/23 11:49
 * @description:
 */
@Data
public class QuestionDetailVO {

    private Question question;

    private Object analysis;
}
