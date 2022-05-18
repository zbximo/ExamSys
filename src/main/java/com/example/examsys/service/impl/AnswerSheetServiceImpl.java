package com.example.examsys.service.impl;

import com.example.examsys.entity.AnswerDetail;
import com.example.examsys.entity.AnswerSheet;
import com.example.examsys.entity.Question;
import com.example.examsys.exception.BusinessException;
import com.example.examsys.form.ToService.AnswerSheetDTO;
import com.example.examsys.form.ToView.AnswerSheetBasicInfoVO;
import com.example.examsys.form.ToView.PaperVO;
import com.example.examsys.repository.AnswerSheetRepository;
import com.example.examsys.service.AnswerSheetService;
import com.example.examsys.utils.Constants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author:Benjamin
 * @Date:2022/5/17 10:17
 **/
@Service
public class AnswerSheetServiceImpl implements AnswerSheetService {
    @Autowired
    private AnswerSheetRepository answerSheetRepository;

    @Override
    public String submitAnswerSheet(AnswerSheetDTO answerSheetDTO) {
        AnswerSheet answerSheet = answerSheetRepository.findByAnswerSheetId(answerSheetDTO.getAnswerSheetId());
        BeanUtils.copyProperties(answerSheetDTO, answerSheet);
        answerSheet.setSubmitDate(new Date(System.currentTimeMillis()));
        answerSheet.setAnswerDetailList(answerSheetDTO.getAnswerDetailList());
        answerSheet.setStatus(Constants.A_NOT_RECTIFY);
        System.out.println(answerSheet.toString());
        int len = answerSheet.getAnswerDetailList().size();
        List<Question> questionList = answerSheet.getPaper().getQuestionList();
        List<AnswerDetail> answerDetailList = answerSheetDTO.getAnswerDetailList();
        if (questionList.size() != answerDetailList.size()) {
            throw new BusinessException(Constants.PARAM_ERROR, "答案上传有误");
        }
        for (int i = 0; i < len; i++) {
            Question question = questionList.get(i);
            AnswerDetail answerDetail = answerDetailList.get(i);
            if (!question.getQuestionType().equals(Constants.Q_CATEGORY_SUBJECTIVE)) {
                if (question.getTrueAnswer().containsAll(answerDetail.getAnswer())) {
                    answerDetailList.get(i).setScore(question.getScore());
                }
            }
        }

        answerSheetRepository.save(answerSheet);
        return answerSheetDTO.getAnswerSheetId();
    }

    @Override
    public String deleteAnswerSheet(String id) {
        answerSheetRepository.deleteById(id);
        return id;
    }

    @Override
    public AnswerSheet findById(String id) {

        return answerSheetRepository.findByAnswerSheetId(id);
    }

    @Override
    public List<AnswerSheetBasicInfoVO> teacherGetAnswerSheetsBasicInfo(String paperId) {

        List<AnswerSheet> answerSheetList = answerSheetRepository.teacherGetAnswerSheets(paperId);
        List<AnswerSheetBasicInfoVO> answerSheetBasicInfoVOList = new ArrayList<>();
        for (AnswerSheet answerSheet : answerSheetList) {
            AnswerSheetBasicInfoVO answerSheetBasicInfoVO = new AnswerSheetBasicInfoVO();
            answerSheetBasicInfoVO.setAnswerSheetId(answerSheet.getAnswerSheetId());
            answerSheetBasicInfoVO.setStatus(answerSheet.getStatus() > answerSheet.getPaper().getStatus() ? answerSheet.getStatus() : answerSheet.getPaper().getStatus());
            answerSheetBasicInfoVO.setStudent(answerSheet.getStudent());
            answerSheetBasicInfoVOList.add(answerSheetBasicInfoVO);
        }
        return answerSheetBasicInfoVOList;
    }

    @Override
    public List<PaperVO> studentGetPapers(String studentId, String courseId) {

        List<AnswerSheet> answerSheetList = answerSheetRepository.studentGetAnswerSheets(studentId, courseId);
        List<PaperVO> paperVOList = new ArrayList<>();
        for (AnswerSheet answerSheet : answerSheetList) {
            PaperVO paperVO = new PaperVO();
            paperVO.setAnswerSheetId(answerSheet.getAnswerSheetId());
            paperVO.setPaperId(answerSheet.getPaper().getPaperId());
            paperVO.setPaperTitle(answerSheet.getPaper().getPaperTitle());
            paperVO.setStartDate(answerSheet.getPaper().getStartDate());
            paperVO.setEndDate(answerSheet.getPaper().getEndDate());
            paperVO.setStatus(answerSheet.getStatus() > answerSheet.getPaper().getStatus() ? answerSheet.getStatus() : answerSheet.getPaper().getStatus());
            paperVOList.add(paperVO);
        }
        return paperVOList;
    }

    @Override
    public AnswerSheet getAnswerSheet(String studentId, String paperId) {
        AnswerSheet answerSheet = answerSheetRepository.getAnswerSheet(studentId, paperId);
        return answerSheet;
    }

    @Override
    public String startExam(String studentId, String paperId) {
        AnswerSheet answerSheet = answerSheetRepository.getAnswerSheet(studentId, paperId);
        answerSheet.setStatus(Constants.A_EXAM_ING);
        return answerSheet.getAnswerSheetId();
    }

    @Override
    public String rectify(String answerSheetId, List<Double> scores) {
        AnswerSheet answerSheet = answerSheetRepository.findByAnswerSheetId(answerSheetId);
        List<AnswerDetail> answerDetailList = answerSheet.getAnswerDetailList();
        for (int i = 0; i < answerDetailList.size(); i++) {
            answerSheet.getAnswerDetailList().get(i).setScore(scores.get(i));
        }
        System.out.println(answerSheet);
        answerSheetRepository.save(answerSheet);
        return answerSheetId;
    }
}
