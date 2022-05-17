package com.example.examsys.form.ToService;

import lombok.Data;

import java.util.List;

/**
 * @author:Benjamin
 * @Date:2022/5/17 20:52
 * @description:基本题目信息
 **/
@Data
public class QuestionBankDTO {

    private String questionTitle;

    private List<String> questionField;

    private Integer questionType;

    private List<String> options;

    private List<String> trueAnswer;
}
