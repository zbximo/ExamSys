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

    @Query(value = "{'paper.course.courseId':?0}")
    AnswerSheet teacherGetPapers(String courseId);

    AnswerSheet findByAnswerSheetId(String answerSheetId);

    @Query(value = "{'paper.paperId':?0}")
    List<AnswerSheet> teacherGetAnswerSheets(String paperId);

    @Query(value = "{'$and':[{'studentId':?0},{'paper.courseId':?1}]}")
    List<AnswerSheet> studentGetAnswerSheets(String studentId, String courseId);

    @Query(value = "{'$and':[{'studentId':?0},{'paper.paperId':?1}]}")
    AnswerSheet getAnswerSheet(String studentId, String paperId);
}
