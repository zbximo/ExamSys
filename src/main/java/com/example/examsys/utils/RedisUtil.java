package com.example.examsys.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author: ximo
 * @date: 2022/4/19 19:20
 * @description:
 */
@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    /**
     * 开始考试，加Redis，防止反复开始
     *
     * @param studentID
     * @param paperId
     * @return
     */
    public String generateStartExamKey(String studentID, String paperId) {
        return Constants.REDIS_START_EXAM + "-" + studentID + "-" + paperId;
    }

    /**
     * 开始考试，加Redis，防止反复提交
     *
     * @param answerSheetId
     * @return
     */
    public String generateSubmitAsKey(String answerSheetId) {
        return Constants.REDIS_SUBMIT_EXAM + "-" + answerSheetId;
    }

    /**
     * 用户状态
     *
     * @param userId
     * @return
     */
    public String generateUserStatusKey(String userId) {
        return Constants.REDIS_USER_STATUS + "-" + userId;
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, String value) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 更新缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean update(String key, String value) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().getAndSet(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 删除缓存
     *
     * @param key
     * @return
     */
    public boolean delete(String key) {
        boolean result = false;
        try {
            redisTemplate.delete(key);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }
}
