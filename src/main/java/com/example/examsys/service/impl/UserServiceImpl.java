package com.example.examsys.service.impl;

import com.example.examsys.entity.Course;
import com.example.examsys.entity.User;
import com.example.examsys.exception.BusinessException;
import com.example.examsys.form.ToService.UserDTO;
import com.example.examsys.repository.CourseRepository;
import com.example.examsys.repository.UserRepository;
import com.example.examsys.service.UserService;
import com.example.examsys.utils.Constants;
import com.example.examsys.utils.ExamSystemUtils;
import com.example.examsys.utils.MD5Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: ximo
 * @date: 2022/5/14 17:00
 * @description:
 */
@Service
class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;
    /**
     * @param userDTO 用户信息
     * @return
     */
    @Override
    public String addUser(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        if (user.getUserId() == null || user.getUserId().equals("")) {
            throw new BusinessException(Constants.PARAM_ERROR, "用户账号为空");
        } else if (userRepository.findByUserId(user.getUserId())!=null){
            throw new BusinessException(Constants.PARAM_ERROR, "用户名已存在");
        }
        else if (user.getPassword() == null || user.getPassword().equals("")) {
            throw new BusinessException(Constants.PARAM_ERROR, "密码为空");
        }
        user.setPassword(MD5Util.getEncryptedPwd(user.getPassword()));
        userRepository.save(user);
        return user.getUserId();
    }

    /**
     * @param id 用户ID
     * @return
     */
    @Override
    public String deleteUser(String id) {
        if (id.equals("")) {
            throw new BusinessException(Constants.PARAM_ERROR, "用户ID为空");
        }
        if (userRepository.findByUserId(id) == null) {
            throw new BusinessException(Constants.QUERY_EMPTY, "找不到该用户: 用户Id: " + id + ", 不能删除");
        }
        userRepository.deleteByUserId(id);
        return id;
    }

    /**
     * @param id 用户ID
     * @return
     */
    @Override
    public User findById(String id) {
        if (id.equals("")) {
            throw new BusinessException(Constants.PARAM_ERROR, "用户ID为空");
        }
        User user = userRepository.findByUserId(id);
        if (user == null) {
            throw new BusinessException(Constants.QUERY_EMPTY, "找不到该用户: 用户Id: " + id);
        }
        return user;
    }

    /**
     * @param name 用户姓名
     * @return
     */
    @Override
    public List<User> findByName(String name) {
        if (name.equals("")) {
            throw new BusinessException(Constants.PARAM_ERROR, "姓名为空");
        }
        List<User> userList = userRepository.findByName(name);
        if (userList.size() == 0) {
            throw new BusinessException(Constants.QUERY_EMPTY, "找不到该用户: 用户姓名: " + name);
        }
        System.out.println(ExamSystemUtils.beanToJson(userList));
        return userList;
    }

    /**
     * @return
     */
    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }


    /**
     * @param id       用户ID
     * @param password 用户密码
     * @return
     */
    @Override
    public String login(String id, String password) {
        if (id.equals("") || password.equals("")) {
            throw new BusinessException(Constants.PARAM_ERROR, "用户名密码不能为空");
        }
        User user = userRepository.findByUserId(id);
        System.out.println(user);
        if (user == null) {
            throw new BusinessException(Constants.QUERY_EMPTY, "用户不存在");
        } else if (!MD5Util.validPassword(password,user.getPassword())) {
            throw new BusinessException(Constants.QUERY_EMPTY, "密码错误");
        } else {
            return id;
        }
    }

    /**
     * @param id     用户ID
     * @param oldPwd 用户原密码
     * @param newPwd 用户新密码
     * @return
     */
    @Override
    public String modifyPwd(String id, String oldPwd,
                            String newPwd) {
        if (id.equals("")) {
            throw new BusinessException(Constants.PARAM_ERROR, "用户ID不能为空");
        }
        User user = userRepository.findByUserId(id);

        if (user == null) {
            throw new BusinessException(Constants.QUERY_EMPTY, "用户不存在");
        } else if (!MD5Util.validPassword(oldPwd,user.getPassword())) {
            throw new BusinessException(Constants.QUERY_EMPTY, "旧密码错误");
        } else {
            user.setPassword(MD5Util.getEncryptedPwd(newPwd));
            userRepository.save(user);
            return user.getUserId();
        }
    }

    @Override
    public List<Course> getCoursesTaught(String teacherId) {
        User u = new User();
        u.setUserId(teacherId);
        return courseRepository.findByTeacher(u);
    }

    @Override
    public List<Course> getCoursesLearned(String studentId) {
        User u = new User();
        u.setUserId(studentId);
        return courseRepository.findByStudentListContains(u);
    }


}
