package com.example.examsys.form.ToService;

import com.example.examsys.entity.Question;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author: Benjamin
 * @Date: 2022/5/17 9:17
 * @description: 发布考试
 **/
@Data
public class PaperDTO {

    private String paperTitle;

    private List<Question> questionList;

    private Date startDate;

    private Date endDate;
}
