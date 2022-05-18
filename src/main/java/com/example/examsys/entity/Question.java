package com.example.examsys.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;

/**
 * @author: ximo
 * @date: 2022/5/15 11:40
 * @description:
 */
@Data
public class Question {

    /**
     * 题目
     */
    private String questionTitle;

    /**
     * 题目类型
     */
    private Integer questionType;

    /**
     * 题目选项
     */
    private List<String> options;

    /**
     * 题目参考答案
     */
    private List<String> trueAnswer;

    /**
     * 题目默认分数
     */
    private Double score;

}
