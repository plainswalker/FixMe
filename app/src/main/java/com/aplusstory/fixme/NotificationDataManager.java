package com.aplusstory.fixme;

import android.content.Context;

public interface NotificationDataManager{
    public static final int COND_NONE = 0;
    public static final int COND_TIME_OVERUSE = 1;
    public static final int COND_ENVIRON_LIGHT = 2;

    void addRecognizer(Recognizer r);

    boolean isEnabled();
    void enable();
    void disable();

    boolean checkCondition();
    int getConditionCode();
    long getElapsedTime();

    String getUISetting(String key);

    void destroy();
}
