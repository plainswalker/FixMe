package com.aplusstory.fixme;

public interface ScheduleUIManager extends ApplicationUIManager{
    @Override
    void setDataManager(UserDataManager m);
    void setDataManager(ScheduleDataManager m);
}