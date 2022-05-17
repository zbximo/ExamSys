//package com.example.examsys.controller;
//
//import com.example.examsys.entity.AnswerSheet;
//import com.example.examsys.form.ToService.AnswerSheetDTO;
//import com.example.examsys.result.ExceptionMsg;
//import com.example.examsys.result.Response;
//import com.example.examsys.result.ResponseData;
//import com.example.examsys.service.AnswerSheetService;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
///**
// * @author:Benjamin
// * @Date:2022/5/17 10:18
// **/
//@RestController
//@RequestMapping("/answer")
//public class AnswerSheetController {
//    private final org.slf4j.Logger logger = LoggerFactory.getLogger(AnswerSheetController.class);
//    @Autowired
//    private AnswerSheetService answerSheetService;
//
//    /**
//     * @param answerSheetDTO 学生答卷信息
//     * @return
//     */
//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    public ResponseData addAnswerSheet(@RequestBody AnswerSheetDTO answerSheetDTO) {
//        String id = answerSheetService.addAnswerSheet(answerSheetDTO);
//        logger.warn("create answerSheet id: {} ", id);
//        return new ResponseData(ExceptionMsg.CREATE_SUCCESS, id);
//    }
//
//    /**
//     * 通过ID查看学生答题情况
//     * @param id 学生答卷ID
//     * @return
//     */
//    @RequestMapping(value = "/viewAnswerById", method = RequestMethod.GET)
//    public ResponseData viewAnswerById(@RequestParam("id") String id) {
//        AnswerSheet answerSheet = answerSheetService.findById(id);
//        logger.warn("query answerSheet id: {}", id);
//        return new ResponseData(ExceptionMsg.QUERY_SUCCESS, answerSheet);
//    }
//
//    /**
//     * 通过ID删除
//     * @param id 学生问卷ID
//     * @return
//     */
//    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
//    public Response deleteAnswerSheet(@RequestParam("id") String id) {
//        String aid = answerSheetService.deleteAnswerSheet(id);
//        logger.warn("delete answerSheet id: {} ", aid);
//        return new Response(ExceptionMsg.DELETE_SUCCESS);
//    }
//}
