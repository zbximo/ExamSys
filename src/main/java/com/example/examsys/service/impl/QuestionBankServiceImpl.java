package com.example.examsys.service.impl;

import com.example.examsys.entity.QuestionBank;
import com.example.examsys.exception.BusinessException;
import com.example.examsys.form.ToService.ParamsDTO;
import com.example.examsys.form.ToService.QuestionBankDTO;
import com.example.examsys.repository.QuestionBankRepository;
import com.example.examsys.result.ExceptionMsg;
import com.example.examsys.service.QuestionBankService;
import org.bson.types.ObjectId;
import org.elasticsearch.index.query.BoolQueryBuilder;
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
    public boolean update(QuestionBank questionBank) {
        if (questionBank.getQuestionId() == null) {
            throw new BusinessException(500, "未传题目ID");
        }
        questionBankRepository.save(questionBank);
        return true;
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
        BoolQueryBuilder boolQueryBuilder = null;
        if (!field.equals("") && !title.equals("")) {
            boolQueryBuilder = QueryBuilders.boolQuery().filter(
                    QueryBuilders.boolQuery()
                            .must(QueryBuilders.matchQuery("questionTitle", title))
                            .must(QueryBuilders.matchQuery("questionField", field))
            );
        } else if (!title.equals("")) {
            boolQueryBuilder = QueryBuilders.boolQuery().filter(
                    QueryBuilders.boolQuery()
                            .must(QueryBuilders.matchQuery("questionTitle", title))
            );
        } else if (!field.equals("")) {
            boolQueryBuilder = QueryBuilders.boolQuery().filter(
                    QueryBuilders.boolQuery()
                            .must(QueryBuilders.matchQuery("questionField", field))
            );
        } else {
            boolQueryBuilder = QueryBuilders.boolQuery();
        }
        assert boolQueryBuilder != null;
        nativeSearchQueryBuilderQueryBuilder.withQuery(boolQueryBuilder);
        nativeSearchQueryBuilderQueryBuilder.withSort(SortBuilders.fieldSort("questionId.keyword").order(SortOrder.ASC));
        nativeSearchQueryBuilderQueryBuilder.withPageable(PageRequest.of(start, pageSize));
        Page<QuestionBank> questionBankPage = questionBankRepository.search(nativeSearchQueryBuilderQueryBuilder.build());
        // 总条数
        for (QuestionBank questionBank : questionBankPage) {
            System.out.println(questionBank);
        }
        return questionBankPage;
    }


    public Map<String, Object> getRandomList(List<QuestionBank> list, ParamsDTO paramsDTO) {
        System.out.println(1111);
        Map<String, Object> map = new HashMap<>();
        List<QuestionBank> olist = new ArrayList<>();
        Map<Integer, List<Integer>> indexMap = new HashMap<>();
        List<Integer> index = new ArrayList<>();
        Map<String, Integer> missMap = new HashMap<>();
        missMap.put("Single", paramsDTO.getSingle());
        missMap.put("Multi", paramsDTO.getMulti());
        missMap.put("Judgment", paramsDTO.getJudgment());
        missMap.put("Subject", paramsDTO.getSubject());
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
                    System.out.println(type);
                    System.out.println(l);
                    Collections.shuffle(l);
                    List<Integer> newList = new ArrayList<>();
                    switch (type) {
                        case 0: {
                            int x = l.size() >= paramsDTO.getSingle() ? 0 : paramsDTO.getSingle() - l.size();
                            missMap.put("Single", x);
                            if (x == 0) {
                                newList = l.subList(0, paramsDTO.getSingle());
                            } else {
                                newList = l.subList(0, l.size());
                            }
                            break;
                        }
                        case 1: {
                            int x = l.size() >= paramsDTO.getMulti() ? 0 : paramsDTO.getMulti() - l.size();
                            missMap.put("Multi", x);
                            if (x == 0) {
                                newList = l.subList(0, paramsDTO.getMulti());
                            } else {
                                newList = l.subList(0, l.size());
                            }
                            break;
                        }
                        case 2: {
                            int x = l.size() >= paramsDTO.getJudgment() ? 0 : paramsDTO.getJudgment() - l.size();
                            missMap.put("Judgment", x);
                            if (x == 0) {
                                newList = l.subList(0, paramsDTO.getJudgment());
                            } else {
                                newList = l.subList(0, l.size());
                            }
                            break;
                        }
                        case 3: {
                            int x = l.size() >= paramsDTO.getSubject() ? 0 : paramsDTO.getSubject() - l.size();
                            missMap.put("Subject", x);
                            if (x == 0) {
                                newList = l.subList(0, paramsDTO.getSubject());
                            } else {
                                newList = l.subList(0, l.size());
                            }
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
        map.put("questionList", olist);
        map.put("missInfo", missMap);
        return map;
    }

    @Override
    public Map<String, Object> createPapersIntelligent(ParamsDTO paramsDTO) {
        NativeSearchQueryBuilder nativeSearchQueryBuilderQueryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = null;
        String title = paramsDTO.getTitle();
        String field = paramsDTO.getField();
        if (!field.equals("") && !title.equals("")) {
            boolQueryBuilder = QueryBuilders.boolQuery().filter(
                    QueryBuilders.boolQuery()
                            .must(QueryBuilders.matchQuery("questionTitle", paramsDTO.getTitle()))
                            .must(QueryBuilders.matchQuery("questionField", paramsDTO.getField()))
            );
        } else if (!title.equals("")) {
            boolQueryBuilder = QueryBuilders.boolQuery().filter(
                    QueryBuilders.boolQuery()
                            .must(QueryBuilders.matchQuery("questionTitle", paramsDTO.getTitle()))
            );
        } else if (!field.equals("")) {
            boolQueryBuilder = QueryBuilders.boolQuery().filter(
                    QueryBuilders.boolQuery()
                            .must(QueryBuilders.matchQuery("questionField", paramsDTO.getField()))
            );
        } else {
            boolQueryBuilder = QueryBuilders.boolQuery();
        }
//        QueryBuilder query1 = QueryBuilders.multiMatchQuery(paramsDTO.getTitle(), "questionTitle");
//        QueryBuilder query2 = QueryBuilders.multiMatchQuery(paramsDTO.getField(), "questionField");
//        nativeSearchQueryBuilderQueryBuilder.withQuery(QueryBuilders.boolQuery().must(query1).must(query2));
        assert boolQueryBuilder != null;
        nativeSearchQueryBuilderQueryBuilder.withQuery(boolQueryBuilder);
        nativeSearchQueryBuilderQueryBuilder.withSort(SortBuilders.fieldSort("questionType").order(SortOrder.ASC));
        Page<QuestionBank> questionBankPage = questionBankRepository.search(nativeSearchQueryBuilderQueryBuilder.build());
        System.out.println(222);
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
