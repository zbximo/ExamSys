package com.example.examsys.service;

import com.example.examsys.entity.AnswerSheet;
import com.example.examsys.form.ToService.AnswerSheetDTO;
import org.springframework.stereotype.Service;

/**
 * @author:Benjamin
 * @Date:2022/5/17 10:02
 **/
@Service
public interface AnswerSheetService {
    String addAnswerSheet(AnswerSheetDTO answerSheetDTO);

    String deleteAnswerSheet(String id);

    AnswerSheet findById(String id);

//    CID -> List<Paper>( PaperVO 只获取试卷状态)   PaperId -> List<AnswerSheet> 老师
//    UID+CID -> List<AnswerSheet> ( PaperVO 获取试卷和答卷状态)  UID+PID -> AnswerSheet
}
