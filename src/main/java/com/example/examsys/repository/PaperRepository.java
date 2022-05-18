package com.example.examsys.repository;

import com.example.examsys.entity.Paper;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaperRepository extends MongoRepository<Paper, String> {

    Paper findByPaperId(String id);

    List<Paper> findByCourse_CourseId(String id);
}
