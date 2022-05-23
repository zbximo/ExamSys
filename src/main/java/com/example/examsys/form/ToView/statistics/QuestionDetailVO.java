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

    private Integer correctNumber;

    private Integer wrongNumber;

    /**
     * 客观题每个选项选了多少人
     */
    private List<Integer> options;

    private Double avgScore;
}
