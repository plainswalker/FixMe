package com.aplusstory.fixme;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import java.io.Serializable;

public class ScheduleAlarmManager implements ScheduleDataManager {
    public static final String SCHEDULE_ALARM_START_ACTION = "com.aplusstory.fixme.action.ALARM_START";
    public static final String KEY_TIME = "time";
    public static final String KEY_LOCATON = "location";

    private Context context;
    private IntentFilter intfl;
    private BroadcastReceiver alr;
    private AlarmManager alm;


    public ScheduleAlarmManager(Context context){
        this.context = context;
        if(this.intfl == null){
            this.intfl = new IntentFilter();
            intfl.addAction(ScheduleAlarmManager.SCHEDULE_ALARM_START_ACTION);
        }

        if(this.alr == null){
            this.alr = new AlarmReceiver();
            this.context.registerReceiver(this.alr, this.intfl);
        }

        if(this.alm == null){
            this.alm = (AlarmManager)this.context.getSystemService(Context.ALARM_SERVICE);
        }
    }

    public void setAlarm(long time, LocationDataManager.LocatonData loca){
        if(this.alr == null){
            if(this.intfl == null){
                this.intfl = new IntentFilter();
                intfl.addAction(ScheduleAlarmManager.SCHEDULE_ALARM_START_ACTION);
            }
            this.alr = new AlarmReceiver();
            this.context.registerReceiver(this.alr, this.intfl);
        }
        if(this.alm != null && this.alr != null) {
            Intent it = new Intent();
            it.setAction(ScheduleAlarmManager.SCHEDULE_ALARM_START_ACTION);
            it.putExtra(KEY_LOCATON, loca);
            PendingIntent pit = PendingIntent.getBroadcast(this.context, 0,it,PendingIntent.FLAG_UPDATE_CURRENT);
            this.alm.set(AlarmManager.RTC_WAKEUP, time, pit);
        }
    }

    public class AlarmReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(ScheduleAlarmManager.class.getName(),
                    "alarm received, from : "
                    + context.getPackageCodePath()
                    + ", location : \n"
                    + ((LocationDataManager.LocatonData)intent.getSerializableExtra(ScheduleAlarmManager.KEY_LOCATON)).toString());
        }
    }

    @Override
    public void setFileManager(FileManager f) {

    }
}
