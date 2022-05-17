package com.example.examsys.form.ToService;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author: ximo
 * @date: 2022/5/16 19:43
 * @description: 用于用户注册时
 */
@Data
public class UserDTO {

    private String userId;

    private String name;

    private String password;
}
