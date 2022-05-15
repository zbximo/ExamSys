package com.example.examsys.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author: ximo
 * @date: 2022/5/15 11:39
 * @description:
 */
@Document("QuestionBank")
public class QuestionBank {
    @Id
    private String questionId;
    @DBRef
    @Field("question")
    private Question question;
}
