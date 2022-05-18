package com.example.examsys.repository;

import com.example.examsys.entity.Paper;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaperRepository extends MongoRepository<Paper, String> {
}
