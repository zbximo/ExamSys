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
    AnswerSheet teacherGetPapers(String userId);

    AnswerSheet findByAnswerSheetId(String answerSheetId);

    @Query(value = "{'paper.paperId':?1}")
    AnswerSheet teacherGetAnswerSheets(Paper paper);
}
