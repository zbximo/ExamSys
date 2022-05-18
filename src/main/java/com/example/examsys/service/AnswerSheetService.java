package com.example.examsys.service;

import com.example.examsys.entity.AnswerSheet;
import com.example.examsys.form.ToService.AnswerSheetDTO;
import com.example.examsys.form.ToView.AnswerSheetBasicInfoVO;
import com.example.examsys.form.ToView.PaperVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author:Benjamin
 * @Date:2022/5/17 10:02
 **/
@Service
public interface AnswerSheetService {

    String submitAnswerSheet(AnswerSheetDTO answerSheetDTO);

    String deleteAnswerSheet(String id);

    AnswerSheet findById(String id);

    List<AnswerSheetBasicInfoVO> teacherGetAnswerSheetsBasicInfo(String paperId);

    List<PaperVO> studentGetPapers(String studentId, String courseId);

    AnswerSheet getAnswerSheet(String studentId, String paperId);

    String startExam(String studentId, String paperId);

    String rectify(String answerSheetId, List<Double> scores);
//    CID -> List<Paper>( PaperVO 只获取试卷状态)   PaperId -> List<AnswerSheet> 老师
//    UID+CID -> List<AnswerSheet> ( PaperVO 获取试卷和答卷状态)  UID+PID -> AnswerSheet
}
