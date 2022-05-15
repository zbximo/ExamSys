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
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 读取缓存
     * @param key
     * @return
     */
    public String get(final String key){
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 写入缓存
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key,String value){
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key,value);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 更新缓存
     * @param key
     * @param value
     * @return
     */
    public boolean update(final String key,String value){
        boolean result = false;
        try{
            redisTemplate.opsForValue().getAndSet(key, value);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 删除缓存
     * @param key
     * @return
     */
    public boolean delete(final String key){
        boolean result = false;
        try{
            redisTemplate.delete(key);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
