package com.aplusstory.fixme;

//get settings data from UI, SettingsUIManager; save into file using SettingsFileManager
//TODO : implement
public class UserSettingsManager implements SettingsDataManager {
    FileManager fm;

    public UserSettingsManager(){
        this.fm = new SettingsFileManager();
    }

    @Override
    public void setFileManager(FileManager f) {
        this.fm = f;
    }
}
