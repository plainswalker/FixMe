package com.aplusstory.fixme;

public class NotificationUserSettingManager implements SettingsDataManager {
    private FileManager fm = null;
    @Override
    public void setFileManager(FileManager f) {
        this.fm = f;
    }

    float getEnvironmentCondition(){
        if(this.fm != null){
            String setting;
            if((setting = this.fm.getData(NotificationDataManager.THRESHOLD_SETTING_KEY)) != null){
                return Float.parseFloat(setting);
            }
        }
        return 100.0f; //default threshold
    }

    long getAdvisePeroiod(){
        if(this.fm != null){
            String setting;
            if((setting = this.fm.getData(NotificationDataManager.PERIOD_SETTING_KEY)) != null){
                return Long.parseLong(setting);
            }
        }
//        return 30 * 60 * 1000;//default value; 30 minutes
        return 10000; //default value for test; 10 seconds
    }
    int getTransparency(){
        if(this.fm != null){
            String setting;
            if((setting = this.fm.getData(NotificationUIManager.TRANSPARENCY_SETTING_KEY)) != null){
                return Integer.parseInt(setting);
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
