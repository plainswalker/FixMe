package com.aplusstory.fixme;

public interface SettingsUIManager extends ApplicationUIManager{
    @Override
    void setDataManager(UserDataManager m);
    void setDataManager(SettingsDataManager m);
}