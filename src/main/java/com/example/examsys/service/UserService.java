package com.example.examsys.service;

import com.example.examsys.entity.Course;
import com.example.examsys.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: ximo
 * @date: 2022/5/14 16:51
 * @description:
 */
@Service
public interface UserService {
    String addUser(User userMap);

    String updateUser(User userMap);

    String deleteUser(String id);

    User findById(String id);

    List<User> findByName(String name);

    List<User> getAll();

    String login(String id, String password);

    String modifyPwd(String id, String oldPwd,
                     String newPwd);

    List<Course> findByStudentIdListContains(String studentId);
}
