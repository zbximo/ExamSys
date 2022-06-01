package com.example.examsys.service.impl;

import com.example.examsys.entity.AnswerDetail;
import com.example.examsys.entity.AnswerSheet;
import com.example.examsys.entity.Paper;
import com.example.examsys.entity.Question;
import com.example.examsys.form.ToView.statistics.QuestionDetailVO;
import com.example.examsys.form.ToView.statistics.ScoreVO;
import com.example.examsys.repository.AnswerSheetRepository;
import com.example.examsys.repository.PaperRepository;
import com.example.examsys.service.AnalysisService;
import com.example.examsys.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author: ximo
 * @date: 2022/5/21 22:31
 * @description:
 */
@Service
public class AnalysisServiceImpl implements AnalysisService {
    @Autowired
    private AnswerSheetRepository answerSheetRepository;
    @Autowired
    private PaperRepository paperRepository;

    public Double getScore(AnswerSheet answerSheet) {
        double sumScore = 0;
        for (AnswerDetail answerDetail : answerSheet.getAnswerDetailList()) {
            sumScore += answerDetail.getScore();
        }
        return sumScore;
    }

    @Override
    public List<ScoreVO> getScoreRank(String paperId) {
        List<AnswerSheet> answerSheetList = answerSheetRepository.findByPaper_PaperId(paperId);
        List<ScoreVO> scoreVOList = new ArrayList<>();
        for (AnswerSheet answerSheet : answerSheetList) {
            ScoreVO scoreVO = new ScoreVO();
            if (scoreVO.getAttendance() == null) {
                scoreVO.setScore(0.);
            } else {
                scoreVO.setScore(getScore(answerSheet));
            }
            scoreVO.setUser(answerSheet.getStudent());
            scoreVO.setAttendance(answerSheet.getSubmitDate() != null);
            scoreVOList.add(scoreVO);

        }
        Collections.sort(scoreVOList);
        scoreVOList.get(0).setRank(1);
        for (int i = 0; i < scoreVOList.size(); i++) {
            if (i == 0 || !scoreVOList.get(i).getScore().equals(scoreVOList.get(i - 1).getScore())) {
                scoreVOList.get(i).setRank(i + 1);
            } else {
                scoreVOList.get(i).setRank(scoreVOList.get(i - 1).getRank());
            }

        }
        return scoreVOList;
    }

    /**
     * 获取每道题的情况 客观题:{"options":{"A":5,"B",2},"answer:{"correct":1 "wrong":10},"avgScore":1}; 主观题：{"avgScore":2}
     *
     * @param paperId
     * @return
     */
    @Override
    public List<QuestionDetailVO> getQuestionDetail(String paperId) {
        Paper paper = paperRepository.findByPaperId(paperId);
        List<AnswerSheet> answerSheetList = answerSheetRepository.findByPaper_PaperId(paperId);
        List<QuestionDetailVO> questionDetailVOList = new ArrayList<>();
        for (int i = 0; i < paper.getQuestionList().size(); i++) {
            Question question = paper.getQuestionList().get(i);
            QuestionDetailVO questionDetailVO = new QuestionDetailVO();
            questionDetailVO.setQuestion(question);
            Map<String, Object> analysisMap = new HashMap<>();
            Map<String, Integer> optionMap = new HashMap<>();
            Map<String, Integer> answerNumMap = new HashMap<>();
            answerNumMap.put("correct", 0);
            answerNumMap.put("wrong", 0);
            // 初始化map
            question.getOptions().forEach(
                    s -> {
                        optionMap.put(s, 0);
                    }
            );
            Integer[] temp = {i, 0};
            analysisMap.put("options", optionMap);
            analysisMap.put("answer", answerNumMap);
            analysisMap.put("avgScore", 0.);
            answerSheetList.forEach(
                    answerSheet -> {
                        if (answerSheet.getSubmitDate() == null) {
                            temp[1] += 1;
                        } else {
                            if (!question.getQuestionType().equals(Constants.Q_CATEGORY_SUBJECTIVE)) {
                                answerSheet.getAnswerDetailList().get(temp[0]).getAnswer().forEach(
                                        s -> {
                                            optionMap.put(s, optionMap.get(s) + 1);
                                        }
                                );
                                if (answerSheet.getAnswerDetailList().get(temp[0]).getScore() > 0) {
                                    answerNumMap.put("correct", (Integer) answerNumMap.get("correct") + 1);
                                } else {
                                    answerNumMap.put("wrong", (Integer) answerNumMap.get("wrong") + 1);
                                }
                            }
                            analysisMap.put("avgScore", (Double) analysisMap.get("avgScore")
                                    + answerSheet.getAnswerDetailList().get(temp[0]).getScore());
                        }
                    }
            );
            analysisMap.put("avgScore", (Double) analysisMap.get("avgScore") / (answerSheetList.size() - temp[1]));
            analysisMap.put("answer", answerNumMap);
            analysisMap.put("options", optionMap);
            questionDetailVO.setAnalysis(analysisMap);
            questionDetailVOList.add(questionDetailVO);
        }
        return questionDetailVOList;
    }
}
