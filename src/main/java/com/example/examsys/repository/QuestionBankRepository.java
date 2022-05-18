package com.example.examsys.repository;

import com.example.examsys.entity.Question;
import com.example.examsys.entity.QuestionBank;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: ximo
 * @date: 2022/5/18 11:11
 * @description:
 */
@Repository
public interface QuestionBankRepository extends ElasticsearchRepository<QuestionBank, String> {

}
