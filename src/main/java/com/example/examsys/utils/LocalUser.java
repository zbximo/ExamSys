package com.example.examsys.utils;

import com.example.examsys.entity.User;
import org.springframework.stereotype.Component;

/**
 * @author: ximo
 * @date: 2022/5/19 21:04
 * @description:
 */
@Component
public class LocalUser {
    public static ThreadLocal<User> USER = new ThreadLocal<>();
}
