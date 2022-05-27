package com.example.examsys.utils;

import com.example.examsys.entity.AnswerSheet;
import com.example.examsys.entity.Paper;
import com.example.examsys.repository.PaperRepository;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.quartz.JobBuilder.newJob;

/**
 * @author: ximo
 * @date: 2022/5/19 17:52
 * @description:
 */
@Component
public class DynamicTask {
    private final Logger logger = LoggerFactory.getLogger(DynamicTask.class);
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    @Autowired
    private PaperRepository paperRepository;
    @Autowired
    private RedisUtil redisUtil;

    private ScheduledFuture future;
    public static ConcurrentHashMap<String, ScheduledFuture> map = new ConcurrentHashMap<String, ScheduledFuture>();

    public String getCron(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return second + " " + minute + " " + hour + " " + day + " " + month + " ? ";
    }

    public void startCron(Paper paper) {
        threadPoolTaskScheduler.setPoolSize(20);
        String pid = paper.getPaperId();
        Date start = paper.getStartDate();
        Date stop = paper.getEndDate();
        future = threadPoolTaskScheduler.schedule(new Start(pid),
                new CronTrigger(getCron(start)));
        logger.warn(getCron(start));
        assert future != null;
        DynamicTask.map.put(pid, future);
        future = threadPoolTaskScheduler.schedule(new Stop(pid),
                new CronTrigger(getCron(stop)));
        logger.warn(getCron(stop));
        assert future != null;
        DynamicTask.map.put(pid, future);
    }


    private class Start implements Runnable {
        String paperId;

        public Start(String paperId) {
            this.paperId = paperId;
        }

        @Override
        public void run() {
            Paper paper = paperRepository.findByPaperId(paperId);
            paper.setStatus(Constants.P_STATUS_START);
            paperRepository.save(paper);
            logger.info("paperId:{} exam start", this.paperId);
            future = DynamicTask.map.remove(paperId);
            future.cancel(true);
        }
    }

    private class Stop implements Runnable {
        String paperId;

        public Stop(String paperId) {
            this.paperId = paperId;
        }

        @Override
        public void run() {
            Paper paper = paperRepository.findByPaperId(paperId);
            paper.setStatus(Constants.P_STATUS_END);
            paperRepository.save(paper);
            logger.info("paperId:{} exam stop", this.paperId);
            DynamicTask.map.remove(this.paperId);
        }
    }
}
