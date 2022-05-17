//package com.example.examsys.controller;
//
//import com.example.examsys.entity.Paper;
//import com.example.examsys.form.ToService.PaperDTO;
//import com.example.examsys.form.ToService.UserDTO;
//import com.example.examsys.result.ExceptionMsg;
//import com.example.examsys.result.Response;
//import com.example.examsys.result.ResponseData;
//import com.example.examsys.service.PaperService;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
///**
// * @author:Benjamin
// * @Date:2022/5/17 9:02
// **/
//@RestController
//@RequestMapping("/paper")
//public class PaperController {
//    private final org.slf4j.Logger logger = LoggerFactory.getLogger(PaperController.class);
//    @Autowired
//    private PaperService paperService;
//
//    /**
//     * @param paperDTO 试卷内容
//     * @return
//     */
//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    public ResponseData addPaper(@RequestBody PaperDTO paperDTO) {
//        String id = paperService.addPaper(paperDTO);
//        logger.warn("create paper id: {} ", id);
//        return new ResponseData(ExceptionMsg.CREATE_SUCCESS, id);
//    }
//
//    /**
//     * @param id 试卷ID
//     * @return
//     */
//    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
//    public Response deletePaper(@RequestParam("id") String id) {
//        String pid = paperService.deletePaper(id);
//        logger.warn("delete paper id: {} ", pid);
//        return new Response(ExceptionMsg.DELETE_SUCCESS);
//    }
//
//    /**
//     * 通过试卷ID获取试卷
//     * @param id 试卷ID
//     * @return
//     */
//    @RequestMapping(value = "/viewPaperById", method = RequestMethod.GET)
//    public ResponseData viewPaperById(@RequestParam("id") String id) {
//        Paper paper = paperService.findById(id);
//        logger.warn("query paper id: {}", id);
//        return new ResponseData(ExceptionMsg.QUERY_SUCCESS, paper);
//    }
//}
