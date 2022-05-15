package com.example.examsys.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

/**
 * @author: ximo
 * @date: 2022/5/15 11:38
 * @description:
 */
@Data
@Document(collection = "AnswerSheet")
public class AnswerSheet {
    /**
     * 答卷ID
     */
    @Id
    private String answerSheetId;

    /**
     * 试卷，数据库建PaperID，相当于外键
     */
    @DBRef
    @Field("paper_id")
    private Paper paper;
    /**
     * 考生ID
     */
    @DBRef
    @Field("student_id")
    private User userId;
    /**
     * 回答详情
     */
    @Field("answers")
    private List<AnswerDetail> answerDetailList;
    /**
     * 提交时间
     */
    @Field("submit_date")
    private Date submitDate;


}
