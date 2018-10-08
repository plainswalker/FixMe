package com.aplusstory.fixme;

public interface ScheduleAlarmUIManager extends ApplicationUIManager{
    @Override
    int setDataManager(UserDataManager m);
    int setDataManager(ScheduleDataManager m);
}
