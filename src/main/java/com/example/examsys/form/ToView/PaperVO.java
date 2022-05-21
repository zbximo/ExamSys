package com.example.examsys.form.ToView;

import lombok.Data;

import java.util.Date;

/**
 * @author: Benjamin
 * @Date: 2022/5/17 22:01
 * @description: 试卷基本信息(不含题目)
 **/
@Data
public class PaperVO {
    private String answerSheetId;

    private String paperId;

    private String paperTitle;

    private Date startDate;

    private Date endDate;

    private Integer status;
}
