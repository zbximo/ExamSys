package com.example.examsys.utils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

/**
 * @author: ximo
 * @date: 2022/5/14 16:57
 * @description:
 */
public class ExamSystemUtils {
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * bean 转换成 json
     *
     * @param bean
     * @return
     */
    public static String beanToJson(Object bean) {
        if (bean == null) {
            return "";
        }
        try {
            return mapper.writeValueAsString(bean);
        } catch (JsonGenerationException | JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
