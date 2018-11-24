package com.aplusstory.fixme;

public interface NotificationUIManager {
    public static final String VIBRATE_SETTING_KEY = SettingsDataManager.VIBRATE_SETTING_KEY;
    public static final String TRANSPARENCY_SETTING_KEY = SettingsDataManager.TRANSPARENCY_SETTING_KEY;

    public void setDataManager(NotificationDataManager nm);
    boolean isEnabled();
    public void enable();
    public void disable();
    public void advise(String msg);
}
