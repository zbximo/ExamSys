package com.example.examsys.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


import java.util.List;
import java.util.Map;

/**
 * @author: ximo
 * @date: 2022/5/15 11:39
 * @description:
 */
@Document(indexName = "QuestionBank", replicas = 0, shards = 5)
public class QuestionBank {
    @Id
    @Field(type = FieldType.Text)
    private String questionId;

    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String questionTitle;

    @Field(type = FieldType.Integer)
    private Integer questionType;

    /**
     * 问题设计的领域
     */
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private List<String> questionField;

    private List<String> options;

    private List<String> trueAnswer;
}
