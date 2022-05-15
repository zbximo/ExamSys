package com.example.examsys.service;

import com.example.examsys.entity.User;
import org.springframework.stereotype.Service;

/**
 * @author: ximo
 * @date: 2022/5/14 16:51
 * @description:
 */
@Service
public interface UserService {
    String addUser(User userMap);
}
