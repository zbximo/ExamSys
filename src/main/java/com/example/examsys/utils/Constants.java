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
    public static final Integer Q_CATEGORY_Judgment = 2;
    public static final Integer Q_CATEGORY_SUBJECTIVE = 3;
    public static final Integer U_CATEGORY_MANAGER = 2;
    public static final Integer U_CATEGORY_TEACHER = 1;
    public static final Integer U_CATEGORY_STUDENT = 0;

    public static final Integer P_STATUS_NOT_START = 0; // 考试时间没到
    public static final Integer P_STATUS_START = 1; // 考试开始
    public static final Integer A_EXAM_ING = 2; // 考生考试中
    public static final Integer P_STATUS_END = 3; // 考试结束
    public static final Integer A_NOT_RECTIFY = 4; // 待批阅
    public static final Integer A_RECTIFIED = 5; // 已批阅

    public static final String U_STATUS_ONLINE = "online";
    public static final String U_STATUS_EXAMING = "examing";

    public static final String REDIS_USER_STATUS = "userStatus";
    public static final String REDIS_EXAM_STATUS = "examStatus";


}
