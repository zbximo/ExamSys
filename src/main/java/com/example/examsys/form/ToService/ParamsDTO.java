package com.example.examsys.form.ToService;

import lombok.Data;

/**
 * @author: ximo
 * @date: 2022/5/20 17:55
 * @description:
 */
@Data
public class ParamsDTO {
    private Integer start;

    private Integer pageSize;

    /**
     * 单选题数量
     */
    private Integer single;

    /**
     * 多选题数量
     */
    private Integer multi;

    /**
     * 判断题数量
     */
    private Integer judgment;

    /**
     * 主观题数量
     */
    private Integer subject;

    /**
     * 问题标题关键词
     */
    private String title;

    /**
     * 问题范围关键词
     */
    private String field;
}
