package com.example.examsys.controller;

import com.example.examsys.entity.Course;
import com.example.examsys.entity.User;
import com.example.examsys.form.ToService.UserDTO;
import com.example.examsys.form.ToView.UserVO;
import com.example.examsys.repository.UserRepository;
import com.example.examsys.result.ExceptionMsg;
import com.example.examsys.result.Response;
import com.example.examsys.result.ResponseData;
import com.example.examsys.service.UserService;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseData addUser(@RequestBody UserDTO userDTO) {
        String id = userService.addUser(userDTO);
        logger.warn("create student id: {} ", id);
        return new ResponseData(ExceptionMsg.CREATE_SUCCESS, id);
    }

    /**
     * @param id 用户ID
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Response deleteUser(@RequestParam("id") String id) {
        String sid = userService.deleteUser(id);
        logger.warn("delete student id: {} ", sid);
        return new Response(ExceptionMsg.DELETE_SUCCESS);
    }

    /**
     * 通过用户ID获取用户信息
     *
     * @param id 用户ID
     * @return
     */
    @RequestMapping(value = "/viewUserById", method = RequestMethod.GET)
    public ResponseData viewUserById(@RequestParam("id") String id) {
        User user = userService.findById(id);
        logger.warn("query student id: {}", id);
        return new ResponseData(ExceptionMsg.QUERY_SUCCESS, user);
    }

    /**
     * 通过用户名称获取用户信息
     *
     * @param name 用户姓名
     * @return
     */
    @RequestMapping(value = "/viewUsersByName", method = RequestMethod.GET)
    public ResponseData viewUserByName(@RequestParam("name") String name) {
        List<UserVO> userList = userService.findByName(name);
        logger.warn("query students name: {}", name);
        return new ResponseData(ExceptionMsg.QUERY_SUCCESS, userList);
    }

    /**
     * @return
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseData getAll() {
        List<UserVO> list = userService.getAll();
        logger.warn("query all students");
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
    public Response login(@RequestParam("id") String id, @RequestParam("password") String password) {

        String userId = userService.login(id, password);
        logger.info("student id: {} login", userId);
        return new ResponseData(ExceptionMsg.SUCCESS, userId);
    }

    /**
     * @param id     用户ID
     * @param oldPwd 用户原密码
     * @param newPwd 用户新密码
     * @return
     */
    @RequestMapping(value = "/modifyPwd", method = RequestMethod.POST)
    public Response modifyPwd(@RequestParam("id") String id, @RequestParam("oldPwd") String oldPwd,
                              @RequestParam("newPwd") String newPwd) {

        String userId = userService.modifyPwd(id, oldPwd, newPwd);
        logger.warn("student id: {} modified password", userId);
        return new ResponseData(ExceptionMsg.SUCCESS, userId);
    }

    /**
     * 通过用户Id获取该用户所有课程
     *
     * @param id 用户Id
     * @return
     */
    @RequestMapping(value = "/getCoursesLearned", method = RequestMethod.GET)
    public Response getCoursesLearned(@RequestParam("id") String id) {

        List<Course> courseList = userService.getCoursesLearned(id);
        logger.warn("query courses learned");
        return new ResponseData(ExceptionMsg.SUCCESS, courseList);
    }

    /**
     * 通过用户Id获取该用户所有课程
     *
     * @param id 用户Id
     * @return
     */
    @RequestMapping(value = "/getCoursesTaught", method = RequestMethod.GET)
    public Response getCoursesTaught(@RequestParam("id") String id) {

        List<Course> courseList = userService.getCoursesTaught(id);
        logger.warn("query courses taught");
        return new ResponseData(ExceptionMsg.SUCCESS, courseList);
    }


}
