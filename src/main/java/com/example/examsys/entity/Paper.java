package com.example.examsys.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

/**
 * @author: ximo
 * @date: 2022/5/15 11:38
 * @description:
 */
public class Paper {
    @Id
    private String paperId;

    @Field("questions")
    private List<Question> questionList;

    @Field("create_date")
    private Date createDate;

    @Field("start_date")
    private Date startDate;

    @Field("end_date")
    private Date endDate;

}
