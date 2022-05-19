package com.example.examsys.controller;

import com.example.examsys.entity.AnswerSheet;
import com.example.examsys.entity.Course;
import com.example.examsys.repository.AnswerSheetRepository;
import com.example.examsys.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: ximo
 * @date: 2022/5/18 11:27
 * @description:
 */
@RestController
public class TestController {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private AnswerSheetRepository answerSheetRepository;
//    @RequestMapping("/test")
//    public List<AnswerSheet> test(String id,String id1){
//        List<String> list = new ArrayList<>();
//        list.add(id1);
//        list.add("111");
//        return answerSheetRepository.findByStudent_UserIdAndPaper_PaperIdIn(id,list);
//    }
//    @RequestMapping("/t")
//    public List<AnswerSheet> t(String id){
//        List<String> list = new ArrayList<>();
//        list.add(id);
//        return answerSheetRepository.findByAnswerSheetIdIn(list);
//    }

}
