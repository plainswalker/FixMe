package com.aplusstory.fixme;

import android.content.Context;

public class NotificationUserSettingManager implements SettingsDataManager {
    private Context context;
    private FileManager fm = null;

    public NotificationUserSettingManager(Context context){
        this.context = context;
        this.fm = new SettingsFileManager(this.context);
    }

    @Override
    public void setFileManager(FileManager f) {
        this.fm = f;
    }

    float getEnvironmentCondition(){
        if(this.fm != null){
            String setting;
            if((setting = this.fm.getData(NotificationDataManager.THRESHOLD_SETTING_KEY)) != null){
                int condCode = Integer.parseInt(setting);
                switch(condCode){
                    case SettingsUIManager.LIGHT_SENSITIVITY_LOW:
                        return SettingsUIManager.LIGHT_SENSETIVITY_VALUE_LOW;
                    case SettingsUIManager.LIGHT_SENSETIVITY_MIDDLE:
                        return SettingsUIManager.LIGHT_SENSETIVITY_VALUE_MIDDLE;
                    case SettingsUIManager.LIGHT_SENSETIVITY_HIGH:
                        return SettingsUIManager.LIGHT_SENSETIVITY_VALUE_HIGH;
                }
            }
        }
        return 100.0f; //default threshold
    }
    long getAdvisePeroiod(){
        if(this.fm != null){
            String setting;
            if((setting = this.fm.getData(NotificationDataManager.PERIOD_SETTING_KEY)) != null){
                int prCode = Integer.parseInt(setting);
                switch(prCode){
                    case SettingsUIManager.ALERT_PEROID_5MIN:
                        return SettingsUIManager.ALERT_PEROID_VALUE_5MIN;
                    case SettingsUIManager.ALERT_PEROID_10MIN:
                        return SettingsUIManager.ALERT_PEROID_VALUE_10MIN;
                    case SettingsUIManager.ALERT_PEROID_30MIN:
                        return SettingsUIManager.ALERT_PEROID_VALUE_30MIN;
                    case SettingsUIManager.ALERT_PEROID_1HOUR:
                        return SettingsUIManager.ALERT_PEROID_VALUE_1HOUR;
                    case SettingsUIManager.ALERT_PEROID_3HOUR:
                        return SettingsUIManager.ALERT_PEROID_VALUE_3HOUR;
                }
            }
        }
//        return 30 * 60 * 1000;//default value; 30 minutes
        return 10000; //default value for test; 10 seconds
    }
    int getTransparency(){
        if(this.fm != null){
            String setting;
            if((setting = this.fm.getData(NotificationUIManager.TRANSPARENCY_SETTING_KEY)) != null){
                return (255 - Integer.parseInt(setting) * 2);
            }
        }
        return 0xFF; //default value; not transparent
    }
    boolean getViberation(){
        if(this.fm != null){
            String setting;
            if((setting = this.fm.getData(NotificationUIManager.VIBRATE_SETTING_KEY)) != null){
                return Boolean.parseBoolean(setting);
            }
        }
        return false; //default value; not vibrates
    }

}
