package com.example.examsys.thread;

import com.example.examsys.entity.AnswerSheet;
import com.example.examsys.exception.BusinessException;
import com.example.examsys.utils.Constants;
import com.example.examsys.utils.RedisUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.*;

/**
 * @author: ximo
 * @date: 2022/5/28 12:05
 * @description:
 */
@Component
public class StartExamThreadPool implements BeanFactoryAware {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(StartExamThreadPool.class);
    private static RedisUtil redisUtil;

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        StartExamThreadPool.redisUtil = redisUtil;
    }

    //用于从IOC里取对象
    private BeanFactory factory;

    // 线程池维护线程的最少数量
    private final static int CORE_POOL_SIZE = 2;
    // 线程池维护线程的最大数量
    private final static int MAX_POOL_SIZE = 10;
    // 线程池维护线程所允许的空闲时间
    private final static int KEEP_ALIVE_TIME = 0;
    // 线程池所使用的缓冲队列大小
    private final static int WORK_QUEUE_SIZE = 50;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        factory = beanFactory;
    }


    /**
     * 缓冲队列,当线程池满了，则将 答卷 存入到此缓冲队列
     */
    Queue<Object> msgQueue = new LinkedBlockingQueue<Object>();


    /**
     * 当线程池的容量满了，执行下面代码，存入到缓冲队列
     */
    final RejectedExecutionHandler handler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            //加入到缓冲队列
            String pid = ((StartExamThread) r).getPaperId();
            String sid = ((StartExamThread) r).getStudentId();
            msgQueue.offer(redisUtil.generateStartExamKey(sid, pid));
            System.out.println("系统繁忙, 考试放入调度线程池，考生ID:" + sid);
        }
    };


    /**
     * 创建线程池
     */
    final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, new ArrayBlockingQueue(WORK_QUEUE_SIZE), this.handler);


    /**
     * 将 提交答卷 任务加入线程池
     */
    public AnswerSheet startExam(String studentId, String paperId) {
        // 验证当前进入的考生是否已经存在提交记录
        System.out.println("此考生准备添加到线程池，考生ID：" + studentId);
        String id = redisUtil.generateStartExamKey(studentId, paperId);
        if (!redisUtil.exists(id)) {
            StartExamThread StartExamThread = new StartExamThread(studentId, paperId);
            Future<AnswerSheet> future = threadPool.submit(StartExamThread);
            try {
                return future.get();
            } catch (Exception e) {
                String[] s = e.toString().split(":");
                throw new BusinessException(500, s[s.length - 1]);
            }
        } else {
            throw new BusinessException(500, "已经开始考试");
        }
    }

    /**
     * 调度线程池
     */
    final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);


    /**
     * 检查(调度线程池)，每秒执行一次，查看考卷的缓冲队列是否有 提交记录，没有则重新加入到线程池
     */
    final ScheduledFuture scheduledFuture = scheduler.scheduleAtFixedRate(new Runnable() {
        @Override
        public void run() {
            //判断缓冲队列是否存在记录
            if (!msgQueue.isEmpty()) {
                //当线程池的队列容量少于WORK_QUEUE_SIZE，则开始把缓冲队列的考试 加入到 线程池
                if (threadPool.getQueue().size() < WORK_QUEUE_SIZE) {
                    String id = (String) msgQueue.poll();
                    assert id != null;
                    String[] s = id.split("-");
                    startExam(s[0], s[1]);
                    logger.warn("缓冲队列出现开始考试业务，重新添加到线程池，考生ID: {}", s[0]);
                }
            }
        }
    }, 0, 1, TimeUnit.SECONDS);


    /**
     * 获取消息缓冲队列
     */
    public Queue<Object> getMsgQueue() {
        return msgQueue;
    }

    /**
     * 终止线程池+调度线程池
     */
    public void shutdown() {
        //true表示如果定时任务在执行，立即中止，false则等待任务结束后再停止
        System.out.println("终止线程池+调度线程池：" + scheduledFuture.cancel(false));
        scheduler.shutdown();
        threadPool.shutdown();
    }
}
