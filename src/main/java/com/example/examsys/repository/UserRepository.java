package com.example.examsys.repository;

import com.example.examsys.entity.User;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: ximo
 * @date: 2022/5/14 16:59
 * @description:
 */

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByUserId(String id);

    void deleteByUserId(String userId);

    List<User> findByName(String userName);

}
