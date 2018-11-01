package com.aplusstory.fixme;

public interface NotificationUIManager {
    public void setDataManager(NotificationDataManager nm);
    boolean isEnabled();
    public void enable();
    public void disable();
    public void advise(String msg);
}
