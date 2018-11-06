package com.aplusstory.fixme;

//get settings data from UI, SettingsUIManager; save into file using SettingsFileManager

import android.content.Context;

public class UserSettingsManager implements SettingsDataManager {
    private Context context;
    FileManager fm;

    public UserSettingsManager(Context context){
        this.context = context;
        this.fm = new SettingsFileManager(this.context);
    }

    public void set(String key, String value){
        this.fm.setData(key, value);
    }

    @Override
    public void setFileManager(FileManager f) {
        this.fm = f;
    }
}
