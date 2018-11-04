package com.aplusstory.fixme;

public class NotificationUserSettingManager implements SettingsDataManager {

    @Override
    public void setFileManager(FileManager f) {

    }

    float getEnvironmentCondition(){
        return 100.0f;
    }

    long getAdvisePeroiod(){
        return 30 * 60 * 1000;
    }
    int getTransparency(){
        return 0xFF;
    }
    boolean getViberation(){
        return false;
    }

}
