package com.example.examsys.form.ToView.statistics;

import com.example.examsys.entity.User;
import lombok.Data;

/**
 * @author: ximo
 * @date: 2022/5/21 22:29
 * @description: 全班成绩排名
 */
@Data
public class ScoreVO implements Comparable<ScoreVO> {

    private User user;

    private Integer rank;

    private Double score;

    private Boolean attendance;

    @Override
    public int compareTo(ScoreVO o) {
        int r = 0;
        if (this.score > o.getScore()) {
            r = -1;
        } else if (this.score < o.getScore()) {
            r = 1;
        } else {
            r = this.user.getUserId().compareTo(o.getUser().getUserId()) > 0 ? 1 : -1;
        }
        return r;
    }
}
