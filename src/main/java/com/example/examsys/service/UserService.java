package com.example.examsys.service;

import com.example.examsys.entity.User;
import com.example.examsys.form.ToService.UserDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author: ximo
 * @date: 2022/5/14 16:51
 * @description:
 */
@Service
public interface UserService {
    String addUser(UserDTO userDTO);

    String deleteUser(String id);

    User findById(String id);

    List<User> findByName(String name);

    List<User> getAll();

    HashMap<String, Object> login(String id, String password);

    boolean logout(String id);

    String modifyPwd(String id, String oldPwd,
                     String newPwd);

    String modifyType(String id);

    List<User> getAllStudent();

    List<User> getAllTeacher();
}
