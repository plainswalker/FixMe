package com.aplusstory.fixme;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;

public class ScheduleAlarmManager extends Service implements ScheduleDataManager {
    public static final String SCHEDULE_ALARM_START_ACTION = "com.aplusstory.fixme.action.ALARM_START";
    public static final String KEY_LOCATON = "location";
    public static final double RANGE_ALARM = 25.0;
    public static final Class ALARM_ACTIVITY = null;//set the alarm activity here

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LocationDataManager.LocatonData loca;
        loca = (LocationDataManager.LocatonData)intent.getBundleExtra(KEY_LOCATON).getSerializable(KEY_LOCATON);
        SharedPreferences sp = this.getSharedPreferences(LocationFileManager.FILENAME_CURRENT_LOCATION,0);
        long now = System.currentTimeMillis();
        double lat = Double.parseDouble(sp.getString(LocationDataManager.LocatonData.KEY_LATITUDE, "0.0"));
        double longt = Double.parseDouble(sp.getString(LocationDataManager.LocatonData.KEY_LONGTITUDE, "0.0"));
        LocationDataManager.LocatonData currentLoca = new LocationDataManager.LocatonData(now, lat, longt);
        if(loca != null && currentLoca != null && currentLoca.distanceTo(loca) > RANGE_ALARM) {
            Intent it = new Intent(this, ALARM_ACTIVITY);
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(it);
        }
        return START_NOT_STICKY;
    }

    public static void setAlarm(Context context, AlarmManager alm, long time, LocationDataManager.LocatonData loca){
        if(alm != null){
            Intent it = new Intent(context, ScheduleAlarmManager.class);
            it.setAction(ScheduleAlarmManager.SCHEDULE_ALARM_START_ACTION);
            Bundle bd = new Bundle();
            bd.putSerializable(KEY_LOCATON, loca);
            it.putExtra(KEY_LOCATON, bd);
            PendingIntent pit = PendingIntent.getService(context, 0,it,PendingIntent.FLAG_UPDATE_CURRENT);
            alm.set(AlarmManager.RTC_WAKEUP, time, pit);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void setFileManager(FileManager f) {

    }
}
