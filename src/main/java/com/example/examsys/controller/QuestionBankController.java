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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Page<QuestionBank> searchByPage(Integer start, Integer pageSize, String title, String field) {
        return questionBankService.searchByPage(start, pageSize, title, field);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<QuestionBank> getAll() {
        return questionBankService.getAll();
    }

    @RequestMapping(value = "/createPaper", method = RequestMethod.POST)
    public List<QuestionBank> createPaperIntelligent(@RequestBody ParamsDTO paramsDTO) {
        return questionBankService.createPapersIntelligent(paramsDTO);
    }


}
