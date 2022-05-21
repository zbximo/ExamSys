package com.example.examsys.service;

import com.example.examsys.form.ToView.statistics.ScoreVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: ximo
 * @date: 2022/5/21 22:31
 * @description:
 */
@Service
public interface AnalysisService {

    List<ScoreVO> getScoreRank(String paperId);
}
