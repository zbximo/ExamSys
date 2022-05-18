package com.example.examsys.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;

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

    /**
     * 这个type只区分是否为管理员
     */
    @Field("type")
    private Integer type;


}
