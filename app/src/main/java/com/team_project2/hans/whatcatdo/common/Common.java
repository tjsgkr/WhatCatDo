package com.team_project2.hans.whatcatdo.common;


import android.os.Environment;

/**
 * 프로젝트에서 계속 사용되는 상수값을 저장해 두는 파일입니다.
* */
public class Common {
    /*tensorflow*/
    public static final String  EMOTION_MODEL_PATH   = "emotion_finder.tflite";
    public static final String  EMOTION_LABEL_PATH   = "emotion_labels.txt";
    public static final String  INCEPTION_MODEL_PATH = "cat_finder.tflite";
    public static final String  INCEPTION_LABEL_PATH = "cat_labels.txt";
    public static final int     INPUT_SIZE           = 299;
    public static final String  IMAGE_PATH           = Environment.getExternalStorageDirectory().toString()+"/whatcatdo/";
    public static final int     IMAGE_QUALITY        = 100;

    public static final int     RECORD_TIME          = 3500;

    //bitmap
    public static final int     THUMBSIZE            = 64;

    /*database*/
    public static final int     DATABASE_VERSION     = 1;
    public static final String  DATABASE_NAME        = "logManager";

    //Log Table name
    public static final String  TABLE_LOGS           = "logs";

    //Log Table Columns names
    public static final String  LOG_ID               = "_id";
    public static final String  LOG_TIMESTAMP        = "timestamp";
    public static final String  LOG_PATH             = "path";
    public static final String  LOG_COMMENT          = "comment";

    //Emotion Table name
    public static final String  TABLE_EMOTIONS       = "emotions";

    //Emotion Table Columns names
    public static final String  EMOTION_ID           = "_id";
    public static final String  EMOTION_TIMESTAMP    = "timestamp";
    public static final String  EMOTION_TITLE        = "title";
    public static final String  EMOTION_PERCENT      = "percent";
}
