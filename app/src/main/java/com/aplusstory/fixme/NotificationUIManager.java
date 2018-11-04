package com.aplusstory.fixme;

public interface NotificationUIManager {
    public static final String VIBERATE_SETTING_KEY = "viberation";
    public static final String TRANSPARENCY_SETTING_KEY = "transparency";

    public void setDataManager(NotificationDataManager nm);
    boolean isEnabled();
    public void enable();
    public void disable();
    public void advise(String msg);
}
