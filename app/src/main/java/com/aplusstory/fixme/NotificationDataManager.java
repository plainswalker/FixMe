package com.aplusstory.fixme;

public interface NotificationDataManager extends Runnable{
    void setRecognizer(Recognizer r);

    //these methods must be thread safe
    boolean checkCondition();
    long getDelay();
    long getElapsedTime();
}
