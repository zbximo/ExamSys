package com.example.examsys.repository;

import com.example.examsys.entity.AnswerSheet;
import com.example.examsys.entity.Paper;
import com.example.examsys.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerSheetRepository extends MongoRepository<AnswerSheet, String> {
//    @Query(value = "{'$and':[{'answerSheetId':?0},{'paper.paperId':?1}]}")

    AnswerSheet findByPaper_Course_CourseId(String courseId);

    AnswerSheet findByAnswerSheetId(String answerSheetId);

    List<AnswerSheet> findByPaper_PaperId(String paperId);

    List<AnswerSheet> findByStudent_UserIdAndPaper_Course_CourseId(String studentId, String courseId);

    AnswerSheet findByStudent_UserIdAndPaper_PaperId(String studentId, String paperId);
}
