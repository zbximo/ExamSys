package com.example.examsys.controller;

import com.example.examsys.entity.Paper;
import com.example.examsys.form.ToService.PaperDTO;
import com.example.examsys.form.ToService.UserDTO;
import com.example.examsys.form.ToView.PaperVO;
import com.example.examsys.result.ExceptionMsg;
import com.example.examsys.result.Response;
import com.example.examsys.result.ResponseData;
import com.example.examsys.service.AnswerSheetService;
import com.example.examsys.service.PaperService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author:Benjamin
 * @Date:2022/5/17 9:02
 **/
@RestController
@RequestMapping("/paper")
public class PaperController {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(PaperController.class);
    @Autowired
    private PaperService paperService;
    @Autowired
    private AnswerSheetService answerSheetService;

    /**
     * @param paperDTO 试卷内容
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseData addPaper(@RequestBody PaperDTO paperDTO) {
        String id = paperService.addPaper(paperDTO);
        logger.warn("create paper id: {} ", id);
        return new ResponseData(ExceptionMsg.CREATE_SUCCESS, id);
    }

    /**
     * @param id 试卷ID
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Response deletePaper(@RequestParam("id") String id) {
        String pid = paperService.deletePaper(id);
        logger.warn("delete paper id: {} ", pid);
        return new Response(ExceptionMsg.DELETE_SUCCESS);
    }

    /**
     * 通过试卷ID获取试卷
     *
     * @param id 试卷ID
     * @return
     */
    @RequestMapping(value = "/viewPaperById", method = RequestMethod.GET)
    public ResponseData viewPaperById(@RequestParam("id") String id) {
        Paper paper = paperService.findById(id);
        logger.warn("query paper id: {}", id);
        return new ResponseData(ExceptionMsg.QUERY_SUCCESS, paper);
    }

    /**
     * 老师获取一门课程的试卷信息
     *
     * @param courseId 课程Id
     * @return
     */
    @RequestMapping(value = "/teacherGetPapers", method = RequestMethod.GET)
    public ResponseData teacherGetPapers(@RequestParam("courseId") String courseId) {
        List<Paper> papers = paperService.findByCourseId(courseId);
        logger.warn("query papers of course id{}", courseId);
        return new ResponseData(ExceptionMsg.QUERY_SUCCESS, papers);
    }

    /**
     * 学生获取一门课程的试卷信息
     *
     * @param studentId 学生Id
     * @param courseId  课程Id
     * @return
     */
    @RequestMapping(value = "/studentGetPapers", method = RequestMethod.DELETE)
    public ResponseData studentGetPapers(@RequestParam("studentId") String studentId, @RequestParam("courseId") String courseId) {
        List<PaperVO> answerSheetBasicInfoVOList = answerSheetService.studentGetPapers(studentId, courseId);
        logger.warn("student id {} get course Id {}answerSheets  ", studentId, courseId);
        return new ResponseData(ExceptionMsg.DELETE_SUCCESS, answerSheetBasicInfoVOList);
    }
}
