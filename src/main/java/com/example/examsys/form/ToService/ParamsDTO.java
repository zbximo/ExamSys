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

    private Integer single;

    private Integer multi;

    private Integer judgment;

    private Integer subject;

    private String title;

    private String field;
}
