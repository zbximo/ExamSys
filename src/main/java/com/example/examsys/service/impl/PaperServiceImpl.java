package com.example.examsys.service.impl;

import com.example.examsys.entity.AnswerSheet;
import com.example.examsys.entity.Course;
import com.example.examsys.entity.Paper;
import com.example.examsys.entity.User;
import com.example.examsys.form.ToService.PaperDTO;
import com.example.examsys.repository.AnswerSheetRepository;
import com.example.examsys.repository.CourseRepository;
import com.example.examsys.repository.PaperRepository;
import com.example.examsys.service.PaperService;
import com.example.examsys.utils.Constants;
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

    public void addBlankAnswerSheet(Paper paper) {
        String courseId = paper.getCourse().getCourseId();
        Course course = courseRepository.findByCourseId(courseId);
        for (User user : course.getStudentList()) {
            AnswerSheet answerSheet = new AnswerSheet();
            answerSheet.setAnswerSheetId(new ObjectId().toString());
            answerSheet.setStatus(Constants.P_STATUS_NOT_START);
            answerSheet.setStudent(user);
            answerSheet.setPaper(paper);
            answerSheetRepository.save(answerSheet);
        }
    }

    @Override
    public String addPaper(PaperDTO paperDTO) {
        Paper paper = new Paper();
        BeanUtils.copyProperties(paperDTO, paper);
        String pid = new ObjectId().toString();
        paper.setPaperId(pid);
        paper.setCreateDate(new Date(System.currentTimeMillis()));
        paper.setStatus(Constants.P_STATUS_NOT_START);
        //TODO 定时任务 试卷开启 关闭
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
