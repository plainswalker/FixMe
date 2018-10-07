package com.aplusstory.fixme;

public interface SettingsUIManager extends ApplicationUIManager{
    @Override
    int setDataManager(UserDataManager m);
    int setDataManager(SettingsDataManager m);
}