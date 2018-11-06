package com.aplusstory.fixme;

public interface ScheduleUIManager extends ApplicationUIManager{
    @Override
    void setDataManager(UserDataManager m);
    int setDataManager(ScheduleDataManager m);
}