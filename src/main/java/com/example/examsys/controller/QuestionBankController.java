package com.example.examsys.controller;

import com.example.examsys.entity.QuestionBank;
import com.example.examsys.form.ToService.QuestionBankDTO;
import com.example.examsys.form.ToService.UserDTO;
import com.example.examsys.repository.QuestionBankRepository;
import com.example.examsys.result.ExceptionMsg;
import com.example.examsys.result.ResponseData;
import com.example.examsys.service.QuestionBankService;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.web.bind.annotation.*;

/**
 * @author: ximo
 * @date: 2022/5/18 15:36
 * @description:
 */
@RestController
@RequestMapping("questionBank")
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
    public Page<QuestionBank> searchByPageAndSort(Integer start, String key, String key2) {
        // 分页：
        if (start == null) {
            start = 0;
        }
        int size = 2;//每页文档数
        System.out.println(start);
        System.out.println(key);
        NativeSearchQueryBuilder nativeSearchQueryBuilderQueryBuilder = new NativeSearchQueryBuilder();
         nativeSearchQueryBuilderQueryBuilder.withQuery(QueryBuilders.matchQuery("name", key));
//        nativeSearchQueryBuilderQueryBuilder.withQuery(QueryBuilders.multiMatchQuery(key, "questionField"));
        QueryBuilder query1 = QueryBuilders.multiMatchQuery(key, "questionTitle");
        QueryBuilder query2 = QueryBuilders.multiMatchQuery(key2, "questionField");
        nativeSearchQueryBuilderQueryBuilder.withQuery(QueryBuilders.boolQuery().should(query1).should(query2));

        //nativeSearchQueryBuilderQueryBuilder.withHighlightFields(new HighlightBuilder.Field("name").preTags("<span style='background-color: #FFFF00'>").postTags("</span>"));
        nativeSearchQueryBuilderQueryBuilder.withSort(SortBuilders.fieldSort("questionId.keyword").order(SortOrder.ASC));
        nativeSearchQueryBuilderQueryBuilder.withPageable(PageRequest.of(start, size));
        Page<QuestionBank> questionBankPage = questionBankRepository.search(nativeSearchQueryBuilderQueryBuilder.build());
        // 总条数
        for (QuestionBank questionBank : questionBankPage) {
            System.out.println(questionBank);
        }
        return questionBankPage;
    }
}
