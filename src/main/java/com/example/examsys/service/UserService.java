package com.example.examsys.service;

import com.example.examsys.entity.Course;
import com.example.examsys.entity.User;
import com.example.examsys.form.ToService.UserDTO;
import org.springframework.stereotype.Service;

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

    String login(String id, String password);

    String modifyPwd(String id, String oldPwd,
                     String newPwd);


}
