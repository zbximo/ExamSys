package com.example.examsys.controller;

import cn.yueshutong.springbootstartercurrentlimiting.annotation.CurrentLimiter;
import com.example.examsys.entity.AnswerSheet;
import com.example.examsys.form.ToService.AnswerSheetDTO;
import com.example.examsys.form.ToView.AnswerSheetBasicInfoVO;
import com.example.examsys.repository.AnswerSheetRepository;
import com.example.examsys.result.ExceptionMsg;
import com.example.examsys.result.Response;
import com.example.examsys.result.ResponseData;
import com.example.examsys.service.AnswerSheetService;
import com.example.examsys.utils.LocalUser;
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

    /**
     * 测试用
     *
     * @param answerSheet 问卷
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseData add(@RequestBody AnswerSheet answerSheet) {
        answerSheetRepository.save(answerSheet);
        return new ResponseData(ExceptionMsg.UPDATE_SUCCESS);
    }

    /**
     * 可用于考生提交答卷
     *
     * @param answerSheetDTO 学生答卷信息
     * @return
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResponseData submitAnswerSheet(@RequestBody AnswerSheetDTO answerSheetDTO) {
        String id = answerSheetService.submitAnswerSheet(answerSheetDTO);
        logger.warn("create answerSheet id: {} ", id);
        return new ResponseData(ExceptionMsg.SUBMIT_SUCCESS, id);
    }

    /**
     * 可用于通过答卷ID查看学生答题情况
     *
     * @param id 答卷ID
     * @return
     */
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ResponseData viewAnswerSheetById(@PathVariable("id") String id) {
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
    @RequestMapping(value = "/teacher_get_basic_info/{paper_id}", method = RequestMethod.GET)
    public ResponseData teacherGetAnswerSheetsBasicInfo(@PathVariable("paper_id") String paperId) {
        List<AnswerSheetBasicInfoVO> answerSheetList = answerSheetService.teacherGetAnswerSheetsBasicInfo(paperId);
        logger.warn("teacher id {} check answerSheetsBasicInfo of paperId: {} ", 1, paperId);
        return new ResponseData(ExceptionMsg.QUERY_SUCCESS, answerSheetList);
    }


    /**
     * 可用于老师查看学生试卷。
     *
     * @param studentId 学生Id
     * @param paperId   试卷Id
     * @return
     */
    @RequestMapping(value = "/get/{student_id}/{paper_id}", method = RequestMethod.GET)
    public ResponseData getAnswerSheet(@PathVariable("student_id") String studentId, @PathVariable("paper_id") String paperId) {
        AnswerSheet answerSheet = answerSheetService.getAnswerSheet(studentId, paperId);
        logger.warn("user id {} get answerSheet id: {} ", studentId, answerSheet.getAnswerSheetId());
        return new ResponseData(ExceptionMsg.QUERY_SUCCESS, answerSheet);
    }

    /**
     * 可用于学生查看自己试卷。
     *
     * @param paperId 试卷Id
     * @return
     */
    @RequestMapping(value = "/get/{paper_id}", method = RequestMethod.GET)
    public ResponseData getAnswerSheet(@PathVariable("paper_id") String paperId) {
        AnswerSheet answerSheet = answerSheetService.getAnswerSheet(LocalUser.USER.get().getUserId(), paperId);
        logger.warn("user id {} get answerSheet id: {} ", LocalUser.USER.get().getUserId(), answerSheet.getAnswerSheetId());
        return new ResponseData(ExceptionMsg.QUERY_SUCCESS, answerSheet);
    }

    /**
     * 用于考生开始考试
     *
     * @param paperId 问卷Id
     * @return
     */
    @RequestMapping(value = "/start_exam", method = RequestMethod.GET)
    @CurrentLimiter(QPS = 5)
    public ResponseData startExam(@RequestParam("paperId") String paperId) {
        AnswerSheet answerSheet = answerSheetService.startExam(LocalUser.USER.get().getUserId(), paperId);
        logger.warn("student id: {} startExam", LocalUser.USER.get().getUserId());
        return new ResponseData(ExceptionMsg.UPDATE_SUCCESS, answerSheet);
    }


    /**
     * 可用于老师批完试卷提交分数
     *
     * @param answerSheetId 答卷ID
     * @param scores        所有题目分数
     * @return
     */
    @RequestMapping(value = "/rectify", method = RequestMethod.GET)
    public Response rectify(@RequestParam("answerSheetId") String answerSheetId, @RequestParam("scores") List<Double> scores) {
        String id = answerSheetService.rectify(answerSheetId, scores);
        logger.warn("rectify answerSheet id: {} ", id);
        return new Response(ExceptionMsg.UPDATE_SUCCESS);
    }

}
