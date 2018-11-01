package com.aplusstory.fixme;

public interface ScheduleUIManager extends ApplicationUIManager{
    @Override
    int setDataManager(UserDataManager m);
    int setDataManager(ScheduleDataManager m);
}