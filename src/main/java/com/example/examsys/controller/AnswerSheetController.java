package com.example.examsys.controller;

import com.example.examsys.entity.AnswerSheet;
import com.example.examsys.form.ToService.AnswerSheetDTO;
import com.example.examsys.form.ToView.AnswerSheetBasicInfoVO;
import com.example.examsys.form.ToView.PaperVO;
import com.example.examsys.repository.AnswerSheetRepository;
import com.example.examsys.result.ExceptionMsg;
import com.example.examsys.result.ResponseData;
import com.example.examsys.service.AnswerSheetService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author:Benjamin
 * @Date:2022/5/17 10:18
 **/
@RestController
@RequestMapping("/answerSheet")
public class AnswerSheetController {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(AnswerSheetController.class);
    @Autowired
    private AnswerSheetService answerSheetService;
    @Autowired
    private AnswerSheetRepository answerSheetRepository;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseData add(@RequestBody AnswerSheet answerSheet) {
        answerSheetRepository.save(answerSheet);
        return new ResponseData(ExceptionMsg.UPDATE_SUCCESS);
    }

    /**
     * 提交答卷
     *
     * @param answerSheetDTO 学生答卷信息
     * @return
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResponseData submitAnswerSheet(@RequestBody AnswerSheetDTO answerSheetDTO) {
        String id = answerSheetService.submitAnswerSheet(answerSheetDTO);
        logger.warn("create answerSheet id: {} ", id);
        return new ResponseData(ExceptionMsg.UPDATE_SUCCESS, id);
    }

    /**
     * 通过ID查看学生答题情况
     *
     * @param id 答卷ID
     * @return
     */
    @RequestMapping(value = "/viewById", method = RequestMethod.GET)
    public ResponseData viewAnswerSheetById(@RequestParam("id") String id) {
        AnswerSheet answerSheet = answerSheetService.findById(id);
        logger.warn("query answerSheet id: {}", id);
        return new ResponseData(ExceptionMsg.QUERY_SUCCESS, answerSheet);
    }

    /**
     * 可用于老师查看这次考试的所有答卷
     *
     * @param paperId 试卷ID
     * @return
     */
    @RequestMapping(value = "/teacherGetBasicInfo", method = RequestMethod.GET)
    public ResponseData teacherGetAnswerSheetsBasicInfo(@RequestParam("paperId") String paperId) {
        List<AnswerSheetBasicInfoVO> answerSheetList = answerSheetService.teacherGetAnswerSheetsBasicInfo(paperId);
        logger.warn("teacher id {} check answerSheetsBasicInfo of paperId: {} ", 1, paperId);
        return new ResponseData(ExceptionMsg.QUERY_SUCCESS, answerSheetList);
    }


    /**
     * 可用于学生查看自己试卷，老师查看学生试卷。
     *
     * @param studentId 学生Id
     * @param paperId   试卷Id
     * @return
     */
    @RequestMapping(value = "/getByStudentIdAndPaperId", method = RequestMethod.GET)
    public ResponseData getAnswerSheet(@RequestParam("studentId") String studentId, @RequestParam("paperId") String paperId) {
        AnswerSheet answerSheet = answerSheetService.getAnswerSheet(studentId, paperId);
        logger.warn("user id {} get answerSheet id: {} ", studentId, answerSheet.getAnswerSheetId());
        return new ResponseData(ExceptionMsg.QUERY_SUCCESS, answerSheet);
    }

    @RequestMapping(value = "/startExam", method = RequestMethod.GET)
    public ResponseData startExam(@RequestParam("studentId") String studentId, @RequestParam("paperId") String paperId) {
        String answerSheetId = answerSheetService.startExam(studentId, paperId);
        logger.warn("student id: {} startExam", studentId);
        return new ResponseData(ExceptionMsg.QUERY_SUCCESS, answerSheetId);
    }


    @RequestMapping(value = "/rectify", method = RequestMethod.GET)
    public ResponseData rectify(@RequestParam("answerSheetId") String answerSheetId, @RequestParam("scores") List<Double> scores) {
        String id = answerSheetService.rectify(answerSheetId, scores);
        logger.warn("rectify answerSheet id: {} ", id);
        return new ResponseData(ExceptionMsg.UPDATE_SUCCESS, id);
    }

}
