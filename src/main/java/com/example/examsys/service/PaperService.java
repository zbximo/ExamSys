package com.example.examsys.service;

import com.example.examsys.entity.Paper;
import com.example.examsys.form.ToService.PaperDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author:Benjamin
 * @Date:2022/5/17 9:02
 **/
@Service
public interface PaperService {

    String addPaper(PaperDTO paperDTO);

    String deletePaper(String id);

    Paper findById(String id);

    List<Paper> findByCourseId(String id);
}
