package com.example.examsys.thread;

import com.example.examsys.entity.AnswerSheet;
import com.example.examsys.exception.BusinessException;
import com.example.examsys.form.ToService.AnswerSheetDTO;
import com.example.examsys.repository.AnswerSheetRepository;
import com.example.examsys.service.AnswerSheetService;
import com.example.examsys.utils.ApplicationContextProvider;
import com.example.examsys.utils.Constants;
import com.example.examsys.utils.RedisUtil;
import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author: ximo
 * @date: 2022/5/27 18:43
 * @description:
 */

@Component
@Scope("prototype")//spring 多例
public class BusinessThread implements Runnable {

    private AnswerSheetDTO answerSheetDTO;

    public AnswerSheetDTO getAnswerSheetDTO() {
        return answerSheetDTO;
    }

    public void setAnswerSheetDTO(AnswerSheetDTO answerSheetDTO) {
        this.answerSheetDTO = answerSheetDTO;
    }

    public BusinessThread() {

    }

    public BusinessThread(AnswerSheetDTO answerSheetDTO) {
        this.answerSheetDTO = answerSheetDTO;
    }


    @Override
    public void run() {
        System.out.println("多线程处理 考生提交答卷, 答卷ID:" + answerSheetDTO.getAnswerSheetId());
        String answerSheetId = ApplicationContextProvider.getBean(AnswerSheetService.class).submitAnswerSheet(answerSheetDTO);
        System.out.println("提交成功");
    }


}
