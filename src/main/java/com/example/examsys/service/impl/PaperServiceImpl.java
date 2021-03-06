package com.example.examsys.service.impl;

import com.example.examsys.entity.AnswerSheet;
import com.example.examsys.entity.Course;
import com.example.examsys.entity.Paper;
import com.example.examsys.entity.User;
import com.example.examsys.exception.BusinessException;
import com.example.examsys.form.ToService.PaperDTO;
import com.example.examsys.repository.AnswerSheetRepository;
import com.example.examsys.repository.CourseRepository;
import com.example.examsys.repository.PaperRepository;
import com.example.examsys.service.PaperService;
import com.example.examsys.utils.Constants;
import com.example.examsys.utils.DateFormatUtil;
import com.example.examsys.utils.DynamicTask;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author:Benjamin
 * @Date:2022/5/17 9:07
 **/
@Service
public class PaperServiceImpl implements PaperService {
    @Autowired
    private PaperRepository paperRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private AnswerSheetRepository answerSheetRepository;
    @Autowired
    private DynamicTask dynamicTask;

    public void addBlankAnswerSheet(Paper paper) {
        String courseId = paper.getCourse().getCourseId();
        Course course = courseRepository.findByCourseId(courseId);
        System.out.println(course);
        System.out.println(courseId);
        if (course.getStudentList().size() != 0) {
            for (User user : course.getStudentList()) {
                AnswerSheet answerSheet = new AnswerSheet();
                answerSheet.setAnswerSheetId(new ObjectId().toString());
                answerSheet.setStatus(Constants.P_STATUS_NOT_START);
                answerSheet.setStudent(user);
                answerSheet.setPaper(paper);
                answerSheetRepository.save(answerSheet);
            }
        }

    }

    @Override
    public String addPaper(PaperDTO paperDTO) {
        Paper paper = new Paper();
        BeanUtils.copyProperties(paperDTO, paper);
        String pid = new ObjectId().toString();
        paper.setPaperId(pid);
        paper.setCreateDate(new Date(System.currentTimeMillis()));
        // ????????????
        paper.getQuestionList().forEach(
                question -> {
                    if (question.getQuestionType() == null) {
                        throw new BusinessException(400, "?????????????????????");
                    }
                    if (question.getQuestionTitle() == null) {
                        throw new BusinessException(400, "?????????????????????");
                    }
                    if (question.getOptions() == null) {
                        throw new BusinessException(400, "?????????????????????");
                    }
                    if (question.getTrueAnswer() == null) {
                        throw new BusinessException(400, "???????????????????????????");
                    }
                    if (question.getScore() == null) {
                        throw new BusinessException(400, "??????????????????");
                    }

                }
        );
        try {
            paper.setStartDate(DateFormatUtil.str2date(paperDTO.getStartDate()));
            paper.setEndDate(DateFormatUtil.str2date(paperDTO.getEndDate()));
        } catch (Exception e) {
            throw new BusinessException(400, e.toString());
        }

        paper.setStatus(Constants.P_STATUS_NOT_START);
        // ???????????? ???????????? ??????
        dynamicTask.startCron(paper);
        addBlankAnswerSheet(paper);
        paperRepository.save(paper);
        return pid;
    }

    @Override
    public String deletePaper(String id) {
        paperRepository.deleteById(id);
        return id;
    }

    @Override
    public Paper findById(String id) {
        return paperRepository.findByPaperId(id);
    }

    @Override
    public List<Paper> findByCourseId(String courseId) {
        return paperRepository.findByCourse_CourseId(courseId);
    }
}
