package com.example.examsys.controller;

import com.example.examsys.form.ToView.statistics.ScoreVO;
import com.example.examsys.result.ExceptionMsg;
import com.example.examsys.result.ResponseData;
import com.example.examsys.service.AnalysisService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: ximo
 * @date: 2022/5/21 22:31
 * @description:
 */
@RestController
@RequestMapping("analysis")
public class AnalysisController {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(AnalysisController.class);

    @Autowired
    private AnalysisService analysisService;

    @RequestMapping(value = "/get_score_rank/{paper_id}", method = RequestMethod.GET)
    public ResponseData getScoreRank(@PathVariable("paper_id") String paperId) {
        List<ScoreVO> scoreVOList = analysisService.getScoreRank(paperId);
        logger.warn("getScoreRank paperId: {} ", paperId);
        return new ResponseData(ExceptionMsg.QUERY_SUCCESS, scoreVOList);
    }
}
