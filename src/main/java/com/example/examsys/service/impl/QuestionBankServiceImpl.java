package com.example.examsys.service.impl;

import com.example.examsys.entity.QuestionBank;
import com.example.examsys.form.ToService.QuestionBankDTO;
import com.example.examsys.repository.QuestionBankRepository;
import com.example.examsys.service.QuestionBankService;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: ximo
 * @date: 2022/5/18 11:52
 * @description:
 */
@Service
public class QuestionBankServiceImpl implements QuestionBankService {
    @Autowired
    private QuestionBankRepository questionBankRepository;
    @Override
    public String add(QuestionBankDTO questionBankDTO) {
        QuestionBank questionBank = new QuestionBank();
        BeanUtils.copyProperties(questionBankDTO,questionBank);
        String id = new ObjectId().toString();
        questionBank.setQuestionId(id);
        questionBankRepository.save(questionBank);
        return id;
    }
}
