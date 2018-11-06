package com.aplusstory.fixme;

//get settings data from UI, SettingsUIManager; save into file using SettingsFileManager

import android.content.Context;

import org.jetbrains.annotations.Nullable;

public class UserSettingsManager implements SettingsDataManager {
    private Context context;
    FileManager fm;

    public UserSettingsManager(Context context){
        this.context = context;
        this.fm = new SettingsFileManager(this.context);
    }

    @Nullable
    public String get(String key){
        return this.fm.getData(key);
    }
    public int getTransparency(){
        return Integer.parseInt(this.fm.getData(SettingsDataManager.TRANSPARENCY_SETTING_KEY));
    }
    public float getSensitivity(){
        return Float.parseFloat(this.fm.getData(SettingsDataManager.THRESHOLD_SETTING_KEY));
    }
    public long getPeriod(){
        return Long.parseLong(this.fm.getData(SettingsDataManager.DURATION_SETTING_KEY));
    }
    public boolean getVibation(){
        return Boolean.parseBoolean(this.fm.getData(SettingsDataManager.VIBRATE_SETTING_KEY));
    }
    public long getDuration(){
        return Long.parseLong(this.fm.getData(SettingsDataManager.DURATION_SETTING_KEY));
    }

    public void set(String key, String value){
        this.fm.setData(key, value);
    }
    public void setTransparency(int value){
        this.fm.setData(SettingsDataManager.TRANSPARENCY_SETTING_KEY, Integer.toString(value));
    }
    public void setSensitivity(float value){
        this.fm.setData(SettingsDataManager.THRESHOLD_SETTING_KEY, Float.toString(value));
    }
    public void setPeriod(long value){
        this.fm.setData(SettingsDataManager.PERIOD_SETTING_KEY, Long.toString(value));
    }
    public void setViberation(boolean value){
        this.fm.setData(SettingsDataManager.VIBRATE_SETTING_KEY, Boolean.toString(value));
    }
    public void setDuration(long value){
        this.fm.setData(SettingsDataManager.DURATION_SETTING_KEY, Long.toString(value));
    }

    @Override
    public void setFileManager(FileManager f) {
        this.fm = f;
    }
}
