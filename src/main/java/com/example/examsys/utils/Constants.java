package com.example.examsys.utils;

/**
 * @author: ximo
 * @date: 2022/4/17 14:38
 * @description:
 */
public class Constants {
    public static final Integer PARAM_ERROR = 100000;
    public static final Integer QUERY_EMPTY = 200000;
    public static final Integer COURSE_CLOSED = 1;
    public static final Integer COURSE_OPEN = 0;
    public static final Integer Q_CATEGORY_SINGLE_CHOICE = 0;
    public static final Integer Q_CATEGORY_MULTI_CHOICE = 1;
    public static final Integer Q_CATEGORY_SUBJECTIVE = 2;
    public static final String QUE_SIMPLE = "simple";
    public static final String QUE_WORK_QUEUE= "work-queue";
    public static final String QUE_RPC_QUEUE= "rpc-queue";
    public static final String QUE_SUB_QUEUE = "subscribe-queue";
    public static final String QUE_ROUTINE_QUEUE = "routine-queue";
    public static final String QUE_TOPIC_QUEUE = "topic-queue";
    public static final String EX_FANOUT = "ex-fanout";
    public static final String EX_DIRECT = "ex-direct";
    public static final String EX_TOPIC = "ex-topic";
    public static final String EX_RPC = "ex-rpc";

    public static final String KEY_FATAL = "fatal";
    public static final String KEY_WARN = "warn";
    public static final String KEY_INFO = "info";
    public static final String KEY_RPC = "rpc";

    public static final String TOPIC_TIME = "*.time.*";
    public static final String TOPIC_COLLEGE = "*.college.*";
}
