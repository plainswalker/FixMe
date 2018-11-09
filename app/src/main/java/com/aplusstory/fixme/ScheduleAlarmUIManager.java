package com.aplusstory.fixme;

public interface ScheduleAlarmUIManager extends ApplicationUIManager{
    @Override
    void setDataManager(UserDataManager m);
    int setDataManager(ScheduleDataManager m);
}
