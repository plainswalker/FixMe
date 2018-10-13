package com.aplusstory.fixme;

public interface NotificationUIManager {
    public void setDataManager(NotificationDataManager nm);
    public void enable();
    public void disable();
    public void advise(String msg);
}
