package com.example.examsys.service.impl;

import com.example.examsys.entity.User;
import com.example.examsys.repository.UserRepository;
import com.example.examsys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: ximo
 * @date: 2022/5/14 17:00
 * @description:
 */
@Service
class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public String addUser(User userMap) {
        userRepository.save(userMap);
        return null;
    }
}
