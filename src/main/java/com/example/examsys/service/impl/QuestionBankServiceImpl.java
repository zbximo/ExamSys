package com.example.examsys.service.impl;

import com.example.examsys.entity.QuestionBank;
import com.example.examsys.form.ToService.ParamsDTO;
import com.example.examsys.form.ToService.QuestionBankDTO;
import com.example.examsys.repository.QuestionBankRepository;
import com.example.examsys.service.QuestionBankService;
import org.bson.types.ObjectId;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.*;

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
        BeanUtils.copyProperties(questionBankDTO, questionBank);
        String id = new ObjectId().toString();
        questionBank.setQuestionId(id);
        questionBankRepository.save(questionBank);
        return id;
    }

    @Override
    public Page<QuestionBank> searchByPage(Integer start, Integer pageSize, String title, String field) {
        // 分页：
        if (start == null) {
            start = 0;
        }
        System.out.println(start);
        System.out.println(title);
        NativeSearchQueryBuilder nativeSearchQueryBuilderQueryBuilder = new NativeSearchQueryBuilder();
        QueryBuilder query1 = QueryBuilders.multiMatchQuery(title, "questionTitle");
        QueryBuilder query2 = QueryBuilders.multiMatchQuery(field, "questionField");
        nativeSearchQueryBuilderQueryBuilder.withQuery(QueryBuilders.boolQuery().should(query1).should(query2));
        nativeSearchQueryBuilderQueryBuilder.withSort(SortBuilders.fieldSort("questionId.keyword").order(SortOrder.ASC));
        nativeSearchQueryBuilderQueryBuilder.withPageable(PageRequest.of(start, pageSize));
        Page<QuestionBank> questionBankPage = questionBankRepository.search(nativeSearchQueryBuilderQueryBuilder.build());
        // 总条数
        for (QuestionBank questionBank : questionBankPage) {
            System.out.println(questionBank);
        }
        return questionBankPage;
    }


    public List<QuestionBank> getRandomList(List<QuestionBank> list, ParamsDTO paramsDTO) {
        List<QuestionBank> olist = new ArrayList<>();
        Map<Integer, List<Integer>> indexMap = new HashMap<>();
        List<Integer> index = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Integer type = list.get(i).getQuestionType();
            List<Integer> tmpList = new ArrayList<>();
            if (indexMap.containsKey(type)) {
                tmpList = indexMap.get(type);
            }
            tmpList.add(i);
            indexMap.put(type, tmpList);
        }
        indexMap.forEach(
                (type, l) -> {
                    Collections.shuffle(l);
                    List<Integer> newList = new ArrayList<>();
                    switch (type) {
                        case 0: {
                            newList = l.subList(0, paramsDTO.getSingle());
                            break;
                        }
                        case 1: {
                            newList = l.subList(0, paramsDTO.getMulti());
                            break;
                        }
                        case 2: {
                            newList = l.subList(0, paramsDTO.getJudgment());
                            break;
                        }
                        case 3: {
                            newList = l.subList(0, paramsDTO.getSubject());
                            break;
                        }
                    }
                    indexMap.replace(type, newList);
                    index.addAll(newList);
                }
        );
        index.forEach(
                i -> {
                    olist.add(list.get(i));
                }
        );
        return olist;
    }

    @Override
    public List<QuestionBank> createPapersIntelligent(ParamsDTO paramsDTO) {
        NativeSearchQueryBuilder nativeSearchQueryBuilderQueryBuilder = new NativeSearchQueryBuilder();
        QueryBuilder query1 = QueryBuilders.multiMatchQuery(paramsDTO.getTitle(), "questionTitle");
        QueryBuilder query2 = QueryBuilders.multiMatchQuery(paramsDTO.getField(), "questionField");
        nativeSearchQueryBuilderQueryBuilder.withQuery(QueryBuilders.boolQuery().should(query1).should(query2));
        nativeSearchQueryBuilderQueryBuilder.withSort(SortBuilders.fieldSort("questionType").order(SortOrder.ASC));
        Page<QuestionBank> questionBankPage = questionBankRepository.search(nativeSearchQueryBuilderQueryBuilder.build());

        return getRandomList(questionBankPage.getContent(), paramsDTO);
    }


    @Override
    public List<QuestionBank> getAll() {
        Iterator<QuestionBank> it = questionBankRepository.findAll().iterator();
        List<QuestionBank> list = new ArrayList<>();
        while (it.hasNext()) {
            list.add(it.next());
        }
        return list;
    }


}
