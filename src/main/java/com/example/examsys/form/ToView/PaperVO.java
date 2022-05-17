package com.example.examsys.form.ToView;

import lombok.Data;

import java.util.Date;

/**
 * @author: Benjamin
 * @Date: 2022/5/17 22:01
 * @description: 创建考试
 **/
@Data
public class PaperVO {

    private String paperId;

    private String paperTitle;

    private Date startDate;

    private Date endDate;

    private Integer status;
}
