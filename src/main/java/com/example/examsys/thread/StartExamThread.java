package com.example.examsys.thread;


import com.example.examsys.entity.AnswerSheet;
import com.example.examsys.service.AnswerSheetService;
import com.example.examsys.utils.ApplicationContextProvider;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

/**
 * @author: ximo
 * @date: 2022/5/28 12:05
 * @description:
 */
@Component
@Scope("prototype")//spring 多例
public class StartExamThread implements Callable<AnswerSheet> {


    private String paperId;
    private String studentId;

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }


    public StartExamThread() {

    }

    public StartExamThread(String studentId, String paperId) {
        this.studentId = studentId;
        this.paperId = paperId;
    }


    @Override
    public AnswerSheet call() throws Exception {
        System.out.println("多线程处理 考生开始考试, 考生ID:" + studentId + " 试卷ID:" + paperId);
        AnswerSheet answerSheet = ApplicationContextProvider.getBean(AnswerSheetService.class).startExam(studentId, paperId);
        return answerSheet;
    }
}
