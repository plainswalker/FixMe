package com.aplusstory.fixme;

//get settings data from UI, SettingsUIManager; save into file using SettingsFileManager

import android.content.Context;
import android.util.Log;

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
        String setting = this.fm.getData(SettingsDataManager.TRANSPARENCY_SETTING_KEY);
        Log.d(this.getClass().getName(), "value : " + setting);
        if(setting != null) {
            return Integer.parseInt(setting);
        } else {
            return 0;
        }
    }
    public int getSensitivity(){
        String setting = this.fm.getData(SettingsDataManager.THRESHOLD_SETTING_KEY);
        Log.d(this.getClass().getName(), "value : " + setting);
        if(setting != null) {
            return Integer.parseInt(setting);
        } else {
            return 0;
        }
    }
    public int getPeriod(){
        String setting = this.fm.getData(SettingsDataManager.PERIOD_SETTING_KEY);
        Log.d(this.getClass().getName(), "value : " + setting);
        if(setting != null) {
            return Integer.parseInt(setting);
        } else {
            return 0;
        }
    }
    public boolean getVibation(){
        String setting = this.fm.getData(SettingsDataManager.VIBRATE_SETTING_KEY);
        Log.d(this.getClass().getName(), "value : " + setting);
        if(setting != null) {
            return Boolean.parseBoolean(setting);
        } else {
            return false;
        }
    }
    public int getDuration(){
        String setting = this.fm.getData(SettingsDataManager.DURATION_SETTING_KEY);
        Log.d(this.getClass().getName(), "value : " + setting);
        if(setting != null) {
            return Integer.parseInt(setting);
        } else {
            return 0;
        }
    }

    public void set(String key, String value){
        this.fm.setData(key, value);
    }
    public void setTransparency(int value){
        this.fm.setData(SettingsDataManager.TRANSPARENCY_SETTING_KEY, Integer.toString(value));
    }
    public void setSensitivity(int value){
        this.fm.setData(SettingsDataManager.THRESHOLD_SETTING_KEY, Integer.toString(value));
    }
    public void setPeriod(int value){
        this.fm.setData(SettingsDataManager.PERIOD_SETTING_KEY, Integer.toString(value));
    }
    public void setViberation(boolean value){
        this.fm.setData(SettingsDataManager.VIBRATE_SETTING_KEY, Boolean.toString(value));
    }
    public void setDuration(int value){
        this.fm.setData(SettingsDataManager.DURATION_SETTING_KEY, Integer.toString(value));
    }

    @Override
    public void setFileManager(FileManager f) {
        this.fm = f;
    }
}
