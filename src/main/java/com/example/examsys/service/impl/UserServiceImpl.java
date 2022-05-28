package com.example.examsys.service.impl;

import com.example.examsys.entity.User;
import com.example.examsys.exception.BusinessException;
import com.example.examsys.form.ToService.UserDTO;
import com.example.examsys.repository.CourseRepository;
import com.example.examsys.repository.UserRepository;
import com.example.examsys.service.UserService;
import com.example.examsys.utils.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
    @Autowired
    private RedisUtil redisUtil;

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
        } else if (userRepository.findByUserId(user.getUserId()) != null) {
            throw new BusinessException(Constants.PARAM_ERROR, "用户名已存在");
        } else if (user.getPassword() == null || user.getPassword().equals("")) {
            throw new BusinessException(Constants.PARAM_ERROR, "密码为空");
        }
        user.setPassword(MD5Util.getEncryptedPwd(user.getPassword()));
//        user.setPassword(user.getPassword());
        user.setType(Constants.U_CATEGORY_STUDENT);
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
    public HashMap<String, Object> login(String id, String password) {
        if (id.equals("") || password.equals("")) {
            throw new BusinessException(Constants.PARAM_ERROR, "用户名密码不能为空");
        }

        User user = userRepository.findByUserId(id);
        System.out.println(user);
        if (user == null) {
            throw new BusinessException(Constants.QUERY_EMPTY, "用户不存在");
        } else if (!MD5Util.validPassword(password, user.getPassword())) {
            throw new BusinessException(Constants.QUERY_EMPTY, "密码错误");
        } else {
            HashMap<String, Object> map = new HashMap<>();
            String token = null;
            try {
                //登录Service
                HashMap<String, String> payload = new HashMap<>();
                payload.put("userId", user.getUserId());
                payload.put("userName", user.getName());

                //生成JWT令牌
                token = JWTUtils.getToken(payload);
                //响应token
                map.put("token", token);
                map.put("user", user);
            } catch (Exception e) {
                map.put("msg", e.getMessage());
            }
            String status = null;
            String statusKey = redisUtil.generateUserStatusKey(id);
            if (redisUtil.exists(statusKey)) {
                status = redisUtil.get(statusKey);
                if (status.equals(Constants.U_STATUS_EXAMING)) {
                    throw new BusinessException(500, "用户考试中,不能异地登陆");
                }
            } else {
                // 保存用户在线状态
                redisUtil.set(statusKey, Constants.U_STATUS_ONLINE);
            }
            return map;
        }
    }

    /**
     * @param id 用户ID
     * @return
     */
    @Override
    public boolean logout(String id) {
        try {
            redisUtil.delete(redisUtil.generateUserStatusKey(id));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return true;
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
        } else if (!MD5Util.validPassword(oldPwd, user.getPassword())) {
            throw new BusinessException(Constants.QUERY_EMPTY, "旧密码错误");
        } else {
            user.setPassword(MD5Util.getEncryptedPwd(newPwd));
            userRepository.save(user);
            return user.getUserId();
        }
//        if (user == null) {
//            throw new BusinessException(Constants.QUERY_EMPTY, "用户不存在");
//        } else if (!oldPwd.equals(user.getPassword())) {
//            throw new BusinessException(Constants.QUERY_EMPTY, "旧密码错误");
//        } else {
//            user.setPassword(newPwd);
//            userRepository.save(user);
//            return user.getUserId();
//        }
    }

    @Override
    public String modifyType(String id) {
        if (id.equals("")) {
            throw new BusinessException(Constants.PARAM_ERROR, "用户ID不能为空");
        }
        User user = userRepository.findByUserId(id);

        if (user == null) {
            throw new BusinessException(Constants.QUERY_EMPTY, "用户不存在");
        } else {
            user.setType(Constants.U_CATEGORY_TEACHER);
            userRepository.save(user);
            return user.getUserId();
        }
    }

    @Override
    public List<User> getAllStudent() {
        return userRepository.findByType(Constants.U_CATEGORY_STUDENT);
    }

    @Override
    public List<User> getAllTeacher() {
        return userRepository.findByType(Constants.U_CATEGORY_TEACHER);
    }


}
