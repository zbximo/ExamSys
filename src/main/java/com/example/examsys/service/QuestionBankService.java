package com.example.examsys.service;

import com.example.examsys.entity.QuestionBank;
import com.example.examsys.form.ToService.ParamsDTO;
import com.example.examsys.form.ToService.QuestionBankDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuestionBankService {
    String add(QuestionBankDTO questionBankDTO);

    Page<QuestionBank> searchByPage(Integer start, Integer pageSize, String title, String field);

    List<QuestionBank> createPapersIntelligent(ParamsDTO paramsDTO);

    List<QuestionBank> getAll();
}
