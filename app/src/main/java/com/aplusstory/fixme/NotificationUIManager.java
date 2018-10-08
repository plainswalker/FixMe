package com.aplusstory.fixme;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.widget.Toast;


public abstract class NotificationUIManager {
    private NotificationDataManager fmnm;
    private NotificationManager nm;
    private NotificationChannel nch;
    private Notification ni;
    private Toast ltmi;

    public int setDataManager(NotificationDataManager m){
        try {
            this.fmnm = m;
        }
        catch(Exception e){
            return 1;
        }

        return 0;
    }

    abstract public int setNotification(Notification nt);

    abstract public int updateNotification(String msg, long elt);


}