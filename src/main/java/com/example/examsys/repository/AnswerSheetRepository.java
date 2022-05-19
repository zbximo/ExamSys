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

    List<AnswerSheet> findByStudent_UserId(String studentId);

    AnswerSheet findByStudent_UserIdAndPaper_PaperId(String studentId, String paperId);

//    @Query(value = "{'paper.paperId':{ $in: ?0 }}")
//    @Query(value = "{'paper.paperId':'628488df917f907f9b8ad9d6'}")
//    List<AnswerSheet> findByPaper_Pa(List<String> paperId);
}
