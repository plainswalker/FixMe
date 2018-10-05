package com.aplusstory.fixme;

public interface MapUIManager extends ApplicationUIManager{
    @Override
    int setDataManager(UserDataManager m);
    int setDataManager(ScheduleDataManager m);
    int setDataManager(FootprintDataManager m);
    int setDataManager(SettingsDataManager m);

    int setLocationManger(LocationManager l);
}