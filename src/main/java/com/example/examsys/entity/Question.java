package com.example.examsys.entity;

import java.util.List;

/**
 * @author: ximo
 * @date: 2022/5/15 11:40
 * @description:
 */
public class Question {

    private String questionTitle;
    private Integer questionType;
    private List<String> options;
    private Double score;
    private List<String> trueAnswer;
}
