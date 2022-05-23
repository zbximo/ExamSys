package com.example.examsys.service.impl;

import com.example.examsys.entity.AnswerDetail;
import com.example.examsys.entity.AnswerSheet;
import com.example.examsys.entity.Paper;
import com.example.examsys.form.ToView.statistics.QuestionDetailVO;
import com.example.examsys.form.ToView.statistics.ScoreVO;
import com.example.examsys.repository.AnswerSheetRepository;
import com.example.examsys.repository.PaperRepository;
import com.example.examsys.service.AnalysisService;
import com.example.examsys.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
            scoreVO.setScore(getScore(answerSheet));
            scoreVO.setUser(answerSheet.getStudent());
            scoreVOList.add(scoreVO);
            scoreVO.setAttendance(answerSheet.getSubmitDate() != null);
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
     * @param paperId
     * @return
     */
    @Override
    public List<QuestionDetailVO> getQuestionDetail(String paperId) {
        Paper paper = paperRepository.findByPaperId(paperId);

        List<AnswerSheet> answerSheetList = answerSheetRepository.findByPaper_PaperId(paperId);
        for (int i = 0; i < paper.getQuestionList().size(); i++) {
            QuestionDetailVO questionDetailVO = new QuestionDetailVO();
            questionDetailVO.setQuestion(paper.getQuestionList().get(i));
            final Double[] objective = new Double[4];
            final Integer[] options = new Integer[3];
            final List<Double> objectiveList = Arrays.asList(0., 0., 0., 0.); // correct wrong noAnswer sumScore
//            final List<Integer> options =
            // 客观题
            if (!paper.getQuestionList().get(i).getQuestionType().equals(Constants.Q_CATEGORY_SUBJECTIVE)) {
//                long correct = answerSheetList.stream().filter(
//                        answerSheet -> {
//                            answerSheet.getAnswerDetailList().get()
//                        }
//                ).count()
//                answerSheetList.stream().forEach(
//                        answerSheet -> {
//                            if (answerSheet.getSubmitDate() == null) {
//                                objectiveList.set(2, objectiveList.get(2) + 1);
//                            } else {
//                                answerSheet.getAnswerDetailList().stream().forEach(
//                                        answerDetail -> {
//
//                                        }
//                                );
//                            }
//                        }
//                );
            } else {

            }

        }


        return null;
    }
}
