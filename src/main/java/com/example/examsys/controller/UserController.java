package com.example.examsys.controller;

import com.example.examsys.entity.User;
import com.example.examsys.repository.UserRepository;
import com.example.examsys.result.ExceptionMsg;
import com.example.examsys.result.ResponseData;
import com.example.examsys.service.UserService;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    @Autowired
    private UserRepository  userRepository;
    /**
     * @param userMap 用户信息
     * @return
     */

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseData addUser(@RequestBody User userMap) {
        String id = userService.addUser(userMap);
        logger.warn("create student id: {} ", id);
        return new ResponseData(ExceptionMsg.CREATE_SUCCESS, id);
    }
    @RequestMapping(value = "/s", method = RequestMethod.POST)
    public User s(@RequestBody String id) {
        return userRepository.findByUserId(id);
    }
    @RequestMapping(value = "/sa", method = RequestMethod.POST)
    public User s() {
        User u  = new User();
        u.setUserId(new ObjectId().toString());
        return userRepository.save(u);
    }

}
