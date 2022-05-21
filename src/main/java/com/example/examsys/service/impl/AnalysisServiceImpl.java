package com.example.examsys.service.impl;

import com.example.examsys.entity.AnswerDetail;
import com.example.examsys.entity.AnswerSheet;
import com.example.examsys.form.ToView.statistics.ScoreVO;
import com.example.examsys.repository.AnswerSheetRepository;
import com.example.examsys.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
}
