package com.example.examsys.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author: ximo
 * @date: 2022/5/14 16:52
 * @description:
 */
@Data
@Document(collection = "User")
public class User {

    @Id
    private String userId;

    @Field("name")
    private String name;

    @Field("password")
    private String password;

    @Field("type")
    private Integer type;


}
