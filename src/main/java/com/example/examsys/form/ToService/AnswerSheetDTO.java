package com.example.examsys.form.ToService;

import com.example.examsys.entity.AnswerDetail;
import lombok.Data;

import java.util.List;

/**
 * @author: ximo
 * @date: 2022/5/15 21:03
 * @description: 用于考生提交答卷
 */
@Data
public class AnswerSheetDTO {

    private String answerSheetId;

    private List<AnswerDetail> answerDetailList;

}
