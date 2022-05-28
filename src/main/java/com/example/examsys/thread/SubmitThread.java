package com.example.examsys.thread;

import com.example.examsys.form.ToService.AnswerSheetDTO;
import com.example.examsys.service.AnswerSheetService;
import com.example.examsys.utils.ApplicationContextProvider;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

/**
 * @author: ximo
 * @date: 2022/5/27 18:43
 * @description:
 */

@Component
@Scope("prototype")//spring 多例
public class SubmitThread implements Callable<String> {

    private AnswerSheetDTO answerSheetDTO;

    public AnswerSheetDTO getAnswerSheetDTO() {
        return answerSheetDTO;
    }

    public void setAnswerSheetDTO(AnswerSheetDTO answerSheetDTO) {
        this.answerSheetDTO = answerSheetDTO;
    }

    public SubmitThread() {

    }

    public SubmitThread(AnswerSheetDTO answerSheetDTO) {
        this.answerSheetDTO = answerSheetDTO;
    }


    @Override
    public String call() throws Exception {
        System.out.println("多线程处理 考生提交答卷, 答卷ID:" + answerSheetDTO.getAnswerSheetId());
        String answerSheetId = ApplicationContextProvider.getBean(AnswerSheetService.class).submitAnswerSheet(answerSheetDTO);
        System.out.println("提交成功");
        return answerSheetId;
    }
}
