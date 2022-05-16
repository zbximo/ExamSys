package com.example.examsys.entity;

import lombok.Data;

import java.util.List;

/**
 * @author: ximo
 * @date: 2022/5/16 16:41
 * @description:
 */
@Data
public class AnswerDetail {
    /**
     * 回答列表
     */
    private List<String> answer;

    /**
     * 每道题的得分
     */
    private Double score;
}
