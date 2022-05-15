package com.example.examsys.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

/**
 * @author: ximo
 * @date: 2022/5/15 11:38
 * @description:
 */
@Data
public class AnswerSheet {
    @Id
    private String answerSheetId;

    @DBRef
    @Field("paper_id")
    private Paper paper;

    @Field("answers")
    private List<List<String>> answerList;

    @Field("scores")
    private List<Double> scoreList;

    @Field("submit_date")
    private Date submitDate;


}
