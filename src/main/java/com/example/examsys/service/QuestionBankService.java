package com.example.examsys.service;

import com.example.examsys.entity.QuestionBank;
import com.example.examsys.form.ToService.ParamsDTO;
import com.example.examsys.form.ToService.QuestionBankDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface QuestionBankService {
    String add(QuestionBankDTO questionBankDTO);

    boolean update(QuestionBank questionBank);

    Page<QuestionBank> searchByPage(Integer start, Integer pageSize, String title, String field);

    Map<String, Object> createPapersIntelligent(ParamsDTO paramsDTO);

    List<QuestionBank> getAll();
}
