package com.example.examsys.controller;

import cn.yueshutong.springbootstartercurrentlimiting.annotation.CurrentLimiter;
import com.example.examsys.entity.User;
import com.example.examsys.form.ToService.UserDTO;
import com.example.examsys.result.ExceptionMsg;
import com.example.examsys.result.Response;
import com.example.examsys.result.ResponseData;
import com.example.examsys.service.UserService;
import com.example.examsys.utils.LocalUser;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author: ximo
 * @date: 2022/5/14 16:57
 * @description:
 */
@RestController
@RequestMapping("/user")
class UserController {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    /**
     * @param userDTO 用户信息
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Response register(@RequestBody UserDTO userDTO) {
        String id = userService.addUser(userDTO);
        logger.warn("create user id: {} ", id);
        return new Response(ExceptionMsg.CREATE_SUCCESS);
    }

    /**
     * 管理员 设置 老师
     *
     * @param userId 用户ID
     * @return
     */
    @RequestMapping(value = "/modify_type/{id}", method = RequestMethod.POST)
    public Response modifyType(@PathVariable("id") String userId) {
        String id = userService.modifyType(userId);
        logger.warn("user id: {} to Teacher", id);
        return new Response(ExceptionMsg.UPDATE_SUCCESS);
    }

    /**
     * @param id 用户ID
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public Response deleteUser(@PathVariable String id) {
        String sid = userService.deleteUser(id);
        logger.warn("delete user id: {} ", sid);
        return new Response(ExceptionMsg.DELETE_SUCCESS);
    }

    /**
     * 通过用户ID获取用户信息
     *
     * @param id 用户ID
     * @return
     */
    @RequestMapping(value = "/view/id/{id}", method = RequestMethod.GET)
    public ResponseData viewUserById(@PathVariable String id) {
        User user = userService.findById(id);
        logger.warn("query user id: {}", id);
        return new ResponseData(ExceptionMsg.QUERY_SUCCESS, user);
    }

    /**
     * 通过用户名称获取用户信息
     *
     * @param name 用户姓名
     * @return
     */
    @RequestMapping(value = "/view/name/{name}", method = RequestMethod.GET)
    public ResponseData viewUserByName(@PathVariable String name) {
        List<User> userList = userService.findByName(name);
        logger.warn("query user name: {}", name);
        return new ResponseData(ExceptionMsg.QUERY_SUCCESS, userList);
    }

    /**
     * 获取所有用户
     *
     * @return
     */
    @RequestMapping(value = "/get_all", method = RequestMethod.GET)
    public ResponseData getAll() {
        List<User> list = userService.getAll();
        logger.warn("query all users");
        return new ResponseData(ExceptionMsg.QUERY_SUCCESS, list);
    }


    /**
     * 通过账号密码实现登陆
     *
     * @param id       用户ID
     * @param password 用户密码
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @CurrentLimiter(QPS = 90)
    public ResponseData login(@RequestParam("id") String id, @RequestParam("password") String password) {

        HashMap<String, Object> token = userService.login(id, password);
        logger.info("user id: {} login", id);
        return new ResponseData(ExceptionMsg.SUCCESS, token);
    }

    /**
     * 登出
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Response logout(@RequestParam("id") String id) {

        boolean x = userService.logout(id);
        logger.info("user id: {} logout", id);
        return new Response(ExceptionMsg.SUCCESS);
    }

    /**
     * 更改密码
     *
     * @param oldPwd 用户原密码
     * @param newPwd 用户新密码
     * @return
     */
    @RequestMapping(value = "/modifyPwd", method = RequestMethod.POST)
    public Response modifyPwd(@RequestParam("oldPwd") String oldPwd,
                              @RequestParam("newPwd") String newPwd) {

        String userId = userService.modifyPwd(LocalUser.USER.get().getUserId(), oldPwd, newPwd);
        logger.warn("user id: {} modified password", userId);
        logger.error(LocalUser.USER.get().getUserId());
        return new Response(ExceptionMsg.UPDATE_SUCCESS);
    }

    /**
     * 获取所有学生
     *
     * @return
     */
    @RequestMapping(value = "/get_all_students", method = RequestMethod.GET)
    public ResponseData getAllStudent() {
        List<User> list = userService.getAllStudent();
        logger.warn("query all users");
        return new ResponseData(ExceptionMsg.QUERY_SUCCESS, list);
    }

    /**
     * 获取所有老师
     *
     * @return
     */
    @RequestMapping(value = "/get_all_teacher", method = RequestMethod.GET)
    public ResponseData getAllTeacher() {
        List<User> list = userService.getAllTeacher();
        logger.warn("query all users");
        return new ResponseData(ExceptionMsg.QUERY_SUCCESS, list);
    }
}
