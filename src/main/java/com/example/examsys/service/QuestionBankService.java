package com.example.examsys.service;

import com.example.examsys.form.ToService.QuestionBankDTO;
import org.springframework.stereotype.Service;

@Service
public interface QuestionBankService {
    String add(QuestionBankDTO questionBankDTO);
}
