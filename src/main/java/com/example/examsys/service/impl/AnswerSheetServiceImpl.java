package com.example.examsys.service.impl;

import com.example.examsys.entity.AnswerDetail;
import com.example.examsys.entity.AnswerSheet;
import com.example.examsys.entity.Question;
import com.example.examsys.exception.BusinessException;
import com.example.examsys.form.ToService.AnswerSheetDTO;
import com.example.examsys.form.ToView.AnswerSheetBasicInfoVO;
import com.example.examsys.form.ToView.PaperVO;
import com.example.examsys.repository.AnswerSheetRepository;
import com.example.examsys.repository.PaperRepository;
import com.example.examsys.service.AnswerSheetService;
import com.example.examsys.thread.SubmitThreadPool;
import com.example.examsys.utils.Constants;
import com.example.examsys.utils.RedisUtil;
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
    @Autowired
    private PaperRepository paperRepository;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    SubmitThreadPool submitThreadPool;

    @Override
    public String submitAnswerSheet(AnswerSheetDTO answerSheetDTO) {
        AnswerSheet answerSheet = answerSheetRepository.findByAnswerSheetId(answerSheetDTO.getAnswerSheetId());
//        BeanUtils.copyProperties(answerSheetDTO, answerSheet);
        answerSheet.setSubmitDate(new Date(System.currentTimeMillis()));
        answerSheet.setAnswerDetailList(answerSheetDTO.getAnswerDetailList());
        boolean hasSubject = false;
        int len = answerSheet.getAnswerDetailList().size();
        List<Question> questionList = answerSheet.getPaper().getQuestionList();
        List<AnswerDetail> answerDetailList = answerSheetDTO.getAnswerDetailList();
        System.out.println(questionList.size());
        System.out.println(answerDetailList.size());
        if (questionList.size() != answerDetailList.size()) {
            throw new BusinessException(Constants.PARAM_ERROR, "答案上传有误");
        }
        for (int i = 0; i < len; i++) {
            Question question = questionList.get(i);
            AnswerDetail answerDetail = answerDetailList.get(i);
            if (!question.getQuestionType().equals(Constants.Q_CATEGORY_SUBJECTIVE)) {
                if (question.getTrueAnswer().containsAll(answerDetail.getAnswer()) &&
                        answerDetail.getAnswer().containsAll(question.getTrueAnswer())) {
                    answerDetailList.get(i).setScore(question.getScore());
                }else {
                    answerDetailList.get(i).setScore(0.);
                }
            } else {
                answerDetailList.get(i).setScore(0.);
                hasSubject = true;
            }
        }
        if (hasSubject) {
            answerSheet.setStatus(Constants.A_NOT_RECTIFY);
        } else {
            answerSheet.setStatus(Constants.A_RECTIFIED);
        }
        answerSheet.setAnswerDetailList(answerDetailList);
        answerSheetRepository.save(answerSheet);
        // 考试结束去除考生考试状态redis，设置考生用户状态为在线
        redisUtil.delete(redisUtil.generateStartExamKey(answerSheet.getStudent().getUserId(), answerSheet.getPaper().getPaperId()));
        redisUtil.set(redisUtil.generateUserStatusKey(answerSheet.getStudent().getUserId()), Constants.U_STATUS_ONLINE);
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

        List<AnswerSheet> answerSheetList = answerSheetRepository.findByPaper_PaperId(paperId);
        List<AnswerSheetBasicInfoVO> answerSheetBasicInfoVOList = new ArrayList<>();
        for (AnswerSheet answerSheet : answerSheetList) {
            AnswerSheetBasicInfoVO answerSheetBasicInfoVO = new AnswerSheetBasicInfoVO();
            answerSheetBasicInfoVO.setAnswerSheetId(answerSheet.getAnswerSheetId());
            // 考试结束 且 没交卷 状态为缺考 否则取状态值更大者
            if (answerSheet.getPaper().getStatus().equals(Constants.P_STATUS_END)) {
                if (answerSheet.getSubmitDate() == null) {
                    answerSheetBasicInfoVO.setStatus(Constants.A_NOT_ATTENDANCE);
                }
            } else {
                answerSheetBasicInfoVO.setStatus(answerSheet.getStatus() > answerSheet.getPaper().getStatus() ? answerSheet.getStatus() : answerSheet.getPaper().getStatus());
            }
            answerSheetBasicInfoVO.setStudent(answerSheet.getStudent());
            answerSheetBasicInfoVOList.add(answerSheetBasicInfoVO);
        }
        return answerSheetBasicInfoVOList;
    }

    @Override
    public List<PaperVO> studentGetPapers(String studentId, String courseId) {
        List<AnswerSheet> answerSheetList = answerSheetRepository.findByStudent_UserId(studentId);

        List<PaperVO> paperVOList = new ArrayList<>();
        for (AnswerSheet answerSheet : answerSheetList) {
            if (answerSheet.getPaper().getCourse().getCourseId().equals(courseId)) {
                PaperVO paperVO = new PaperVO();
                paperVO.setAnswerSheetId(answerSheet.getAnswerSheetId());
                paperVO.setPaperId(answerSheet.getPaper().getPaperId());
                paperVO.setPaperTitle(answerSheet.getPaper().getPaperTitle());
                paperVO.setStartDate(answerSheet.getPaper().getStartDate());
                paperVO.setEndDate(answerSheet.getPaper().getEndDate());
                // 考试结束 且 没交卷 状态为缺考 否则取状态值更大者
                if (answerSheet.getPaper().getStatus().equals(Constants.P_STATUS_END)) {
                    if (answerSheet.getSubmitDate() == null) {
                        paperVO.setStatus(Constants.A_NOT_ATTENDANCE);
                    }
                } else {
                    paperVO.setStatus(answerSheet.getStatus() > answerSheet.getPaper().getStatus() ? answerSheet.getStatus() : answerSheet.getPaper().getStatus());
                }

                paperVOList.add(paperVO);
            }
        }
        return paperVOList;
    }

    @Override
    public AnswerSheet getAnswerSheet(String studentId, String paperId) {
        AnswerSheet answerSheet = answerSheetRepository.findByStudent_UserIdAndPaper_PaperId(studentId, paperId);
        return answerSheet;
    }

    @Override
    public AnswerSheet startExam(String studentId, String paperId) {
        AnswerSheet answerSheet = answerSheetRepository.findByStudent_UserIdAndPaper_PaperId(studentId, paperId);
        answerSheet.setStatus(Constants.A_EXAM_ING);
        redisUtil.update(redisUtil.generateStartExamKey(studentId, paperId), Constants.U_STATUS_EXAMING);
        redisUtil.update(redisUtil.generateUserStatusKey(studentId), Constants.U_STATUS_EXAMING);
        answerSheetRepository.save(answerSheet);
        return answerSheet;

    }

    @Override
    public String rectify(String answerSheetId, List<Double> scores) {
        AnswerSheet answerSheet = answerSheetRepository.findByAnswerSheetId(answerSheetId);
        List<AnswerDetail> answerDetailList = answerSheet.getAnswerDetailList();
        for (int i = 0; i < answerDetailList.size(); i++) {
            answerSheet.getAnswerDetailList().get(i).setScore(scores.get(i));
        }
        answerSheet.setStatus(Constants.A_RECTIFIED);
        answerSheetRepository.save(answerSheet);
        return answerSheetId;
    }
}
