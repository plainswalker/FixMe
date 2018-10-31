package com.aplusstory.fixme;

public interface NotificationDataManager extends Runnable{
    public static final int COND_NONE = 0;
    public static final int COND_TIME_OVERUSE = 1;
    public static final int COND_ENVIRON_LIGHT = 2;

    void setRecognizer(Recognizer r);
    //these methods must be thread safe
    boolean isEnabled();
    boolean checkCondition();
    void enable();
    void disable();
    int getConditionCode();
    long getDelay();
    long getElapsedTime();
}
