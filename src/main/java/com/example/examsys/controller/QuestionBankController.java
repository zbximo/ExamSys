package com.example.examsys.controller;

import com.example.examsys.entity.QuestionBank;
import com.example.examsys.form.ToService.ParamsDTO;
import com.example.examsys.form.ToService.QuestionBankDTO;
import com.example.examsys.repository.QuestionBankRepository;
import com.example.examsys.result.ExceptionMsg;
import com.example.examsys.result.ResponseData;
import com.example.examsys.service.QuestionBankService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author: ximo
 * @date: 2022/5/18 15:36
 * @description:
 */
@RestController
@RequestMapping("/questionBank")
public class QuestionBankController {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(QuestionBankController.class);
    @Autowired
    private QuestionBankService questionBankService;
    @Autowired
    private QuestionBankRepository questionBankRepository;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseData add(@RequestBody QuestionBankDTO questionBankDTO) {
        String id = questionBankService.add(questionBankDTO);
        logger.warn("add question to Bank id: {} ", id);
        return new ResponseData(ExceptionMsg.CREATE_SUCCESS, id);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseData update(@RequestBody QuestionBank questionBank) {
        questionBankService.update(questionBank);
        logger.warn("update question to Bank id: {} ", questionBank.getQuestionId());
        return new ResponseData(ExceptionMsg.CREATE_SUCCESS, questionBank.getQuestionId());
    }

    @RequestMapping(value = "/search/{start}/{page_size}/{title}/{field}", method = RequestMethod.GET)
    public ResponseData searchByPage(@PathVariable Integer start, @PathVariable("page_size") Integer pageSize,
                                     @PathVariable String title, @PathVariable String field) {
        return new ResponseData(ExceptionMsg.QUERY_SUCCESS, questionBankService.searchByPage(start, pageSize, title, field));
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseData getAll() {
        return new ResponseData(ExceptionMsg.QUERY_SUCCESS, questionBankService.getAll());
    }

    @RequestMapping(value = "/create_paper", method = RequestMethod.POST)
    public ResponseData createPaperIntelligent(@RequestBody ParamsDTO paramsDTO) {
        return new ResponseData(ExceptionMsg.QUERY_SUCCESS, questionBankService.createPapersIntelligent(paramsDTO));
    }


}
