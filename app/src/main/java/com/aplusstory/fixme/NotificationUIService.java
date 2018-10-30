package com.aplusstory.fixme;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;


public class NotificationUIService extends Service implements NotificationUIManager{
    private static String NOTIFICATION_CHANNEL_ID = "FixMe_Noti";
    private static int NOTIFICATION_ID = (int) new Date().getTime();

    public static final int UPDATE_DELAY = 1000;

    protected NotificationDataManager dataManager;
    private boolean isEnabled = false;

//    private Context context;
    private NotificationManager notificationManager = null;
    private NotificationChannel notificationChannel = null;
    private Notification.Builder notificationBuilder = null;

    private Thread thd = null;
    private Handler hd = null;

//    private Notification notification;
//    private Toast ltmi;`

    public NotificationUIService(){
        super();
        this.dataManager = new NotificationDataManager() {
            Thread thd;
            Recognizer r;
            int timer_d = 0;
            int timer_t = 0;
            @Override
            public void setRecognizer(Recognizer r) {
                this.r = r;
            }

            @Override
            public boolean checkCondition() {
                return true;
            }

            @Override
            public long getDelay() {
                synchronized (this) {
                    return this.timer_d += 1000;
                }
            }

            @Override
            public long getElapsedTime() {
                synchronized (this) {
                    return this.timer_t += 1000;
                }
            }

            @Override
            public int getConditionCode() {
                return NotificationDataManager.COND_TIME_OVERUSE;
            }

            @Override
            public void run() {
                this.thd = new Thread(){
                    @Override
                    public void run() {
//                        int i = 0;
//                        while(true){
//                            try {
//                                Thread.sleep(1000);
//                            } catch (Exception e){
//
//                            }
//                        }
                    }
                };
                this.thd.run();
            }
        };
    }

    public NotificationUIService(NotificationDataManager nm){
        super();
    }

    private void initialize(NotificationDataManager nm){
        this.setDataManager(nm);
        if(this.notificationManager == null) {
            this.notificationManager = (NotificationManager) this.getSystemService(NotificationManager.class);
        }
        CharSequence nchName = this.getString(R.string.notification_channel_name);
        String nchDescription = this.getString(R.string.notification_channel_description);

        String nchID = NotificationUIService.NOTIFICATION_CHANNEL_ID;
        int nchImportance = NotificationManager.IMPORTANCE_DEFAULT;
        if(this.notificationChannel == null
                || this.notificationManager.getNotificationChannel(NotificationUIService.NOTIFICATION_CHANNEL_ID) != this.notificationChannel) {
            this.notificationChannel = new NotificationChannel(nchID, nchName, nchImportance);
        }
        this.notificationChannel.setDescription(nchDescription);
        this.notificationChannel.setImportance(NotificationManager.IMPORTANCE_LOW);
        this.notificationManager.createNotificationChannel(this.notificationChannel);

        this.hd = new NotificationUIService.ServiceHandler();

        if(this.thd == null || !this.thd.isAlive()) {
            this.thd = new NotificationUIService.ServiceThread(this.hd);
//            hd.post(this.thd);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.initialize(this.dataManager);
        this.enable();
        if(this.thd != null && !this.thd.isAlive()) {
            this.thd.start();
        }
        this.dataManager.run();
        this.notificationManager = (NotificationManager) this.getSystemService(NotificationManager.class);

//        Toast.makeText(this, "Notification start", Toast.LENGTH_SHORT).show();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        this.cancelNotification();
        super.onDestroy();
    }

    @Override
    public void setDataManager(NotificationDataManager dataManager) {
        this.dataManager = dataManager;
    }
    //    public int setDataManager(NotificationDataManager m){
//        try {
//            this.dataManager = m;
//        }
//        catch(Exception e){
//            return 1;
//        }
//
//        return 0;
//    }

    public class ServiceThread extends Thread{
        Handler hd;

        public ServiceThread(Handler hd){
            super();
            this.hd = hd;
        }

        @Override
        public void run() {
            while(true){
                try {
                    Thread.sleep(NotificationUIService.UPDATE_DELAY);
                } catch (Exception e){

                }
                NotificationUIService.this.updateNotification(
                        ((NotificationUIService.this.isEnabled) ?
                                NotificationUIService.this.getString(R.string.notification_msg_enable)
                                :NotificationUIService.this.getString(R.string.notification_msg_disable)),
                        NotificationUIService.this.dataManager.getElapsedTime()
                );
                if(NotificationUIService.this.isEnabled && NotificationUIService.this.dataManager.checkCondition()){
                    int cond = NotificationUIService.this.dataManager.getConditionCode();
                    Log.d("NotiUIService", "Thread CheckCond, cond : " + new Integer(cond).toString());
                    this.hd.sendEmptyMessage(0);
                    if(cond == NotificationDataManager.COND_TIME_OVERUSE){
                        NotificationUIService.this.advise(NotificationUIService.this.getString(R.string.advice_msg_posture));
                    }

                    if(cond == NotificationDataManager.COND_ENVIRON_LIGHT){
                        NotificationUIService.this.advise(NotificationUIService.this.getString(R.string.advise_msg_light));
                    }
                }
            }
        }
    }
    public class ServiceHandler extends Handler{
//        Context context;
//
//        public ServiceHandler(Context context){
//            super(NotificationUIService.this.getMainLooper());
//
//            this.context = context;
//            Log.d("NotiUIService","CreateHandler");
//            Toast.makeText(NotificationUIService.this.getApplicationContext(), "create handler", Toast.LENGTH_SHORT).show();
//        }

        @Override
        public void handleMessage(Message msg) {
            Log.d("NotiUIService", "HandleMsg");
            Bundle bd = msg.getData();
            String sntMsg = (String)bd.getCharSequence("msg");
            if(sntMsg != null){
                Toast.makeText(NotificationUIService.this, sntMsg, Toast.LENGTH_SHORT).show();
            }
        }
    }
    private Notification buildNotification(CharSequence contentText){
        if(this.notificationBuilder == null) {
            CharSequence title = this.getString(R.string.app_name);
            CharSequence enableActText = this.isEnabled ?
                    this.getString(R.string.notification_action_disable) :
                    this.getString(R.string.notification_action_enable);
            CharSequence settingsActText = this.getString(R.string.notification_action_settings);
            CharSequence cancelActText = this.getString(R.string.notification_action_cancel);

            Intent enableIntent = new Intent(this, NotificationActionReceiver.class);
            enableIntent.putExtra(NotificationActionReceiver.EXTRA_NAME, this.isEnabled ? NotificationActionReceiver.EXTRA_DISABLE : NotificationActionReceiver.EXTRA_ENABLE);

            Intent settingsIntent = new Intent(this, NotificationActionReceiver.class);
            settingsIntent.putExtra(NotificationActionReceiver.EXTRA_NAME, NotificationActionReceiver.EXTRA_SETTINGS);

            Intent cancelIntent = new Intent(this, NotificationActionReceiver.class);
            cancelIntent.putExtra(NotificationActionReceiver.EXTRA_NAME, NotificationActionReceiver.EXTRA_CANCEL);

            PendingIntent enablePIntent = PendingIntent.getBroadcast(this, 0, enableIntent, 0);
            PendingIntent settingsPIntent = PendingIntent.getBroadcast(this, 0, settingsIntent, 0);
            PendingIntent cancelPIntent = PendingIntent.getBroadcast(this, 0, cancelIntent, 0);

            Intent ntIntent = new Intent(this, NotificationUIService.class);
            PendingIntent ntPIntent = PendingIntent.getActivity(this,0, ntIntent, 0);

            Notification.Action enableAction = new Notification.Action.Builder(R.drawable.ic_launcher_background, enableActText, enablePIntent).build();
            Notification.Action settingsAction = new Notification.Action.Builder(R.drawable.ic_launcher_background, settingsActText, settingsPIntent).build();
            Notification.Action cancelAction = new Notification.Action.Builder(R.drawable.ic_launcher_background, cancelActText, cancelPIntent).build();
            Notification.Builder ntb = new Notification.Builder(this, NotificationUIService.NOTIFICATION_CHANNEL_ID)
//                .setSmallIcon(R.drawable.FixMeIcon)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle(title)
//                .setContentText(contentText)
                    .setVisibility(Notification.VISIBILITY_PRIVATE)
                    .addAction(enableAction)
                    .addAction(settingsAction)
                    .addAction(cancelAction)
                    .setOngoing(true)
                    .setWhen(new Date().getTime())
                    .setShowWhen(true)
                    .setOnlyAlertOnce(true)
                    .setContentIntent(ntPIntent);
            //setting options
            this.notificationBuilder =ntb;
        }
        this.notificationBuilder.setContentText(contentText);
        Notification nt = this.notificationBuilder.build();
        nt.flags |= Notification.FLAG_FOREGROUND_SERVICE;
        return nt;
    }
    public void updateNotification(String msg, long elapsedTimeInMilliSeconds){
        StringBuilder sb = new StringBuilder(msg);

        sb.append("   ").append(this.getString(R.string.notification_msg_elapsed_time_prefix));

//        final long d = 24 * 60 * 60 * 1000;
        final long h = 60 * 60 * 1000;
        final long m = 60 * 1000;
        final long s = 1000;

//        if(elapsedTimeInMilliSeconds / d > 0){
//            sb.append(elapsedTimeInMilliSeconds/d).append(this.getString(R.string.notification_msg_elapsed_time_second_postfix));
//        }

        if(elapsedTimeInMilliSeconds / h > 0){
            sb.append(elapsedTimeInMilliSeconds/h).append(this.getString(R.string.notification_msg_elapsed_time_hour_postfix));
        }

        if(elapsedTimeInMilliSeconds / m > 0){
            sb.append(elapsedTimeInMilliSeconds/m).append(this.getString(R.string.notification_msg_elapsed_time_minute_postfix));
        }

        if(elapsedTimeInMilliSeconds / s  > 0){
            sb.append(elapsedTimeInMilliSeconds/s).append(this.getString(R.string.notification_msg_elapsed_time_second_postfix));
        }

        if(elapsedTimeInMilliSeconds < s){//m){
            sb.append(this.getString(R.string.notification_msg_elapsed_time_under_minute));
        }
//        sb.insert(0,msg);

        CharSequence contentText = (CharSequence) sb.toString();
        this.notificationManager.cancelAll();
        Notification nt = this.buildNotification(contentText);
        this.startForeground(NotificationUIService.NOTIFICATION_ID, nt);
        this.notificationManager.notify(NotificationUIService.NOTIFICATION_ID, nt);
    }
    @Override
    public synchronized void enable(){
        if(!this.isEnabled) {
            this.isEnabled = true;
        }
        String msg = this.getString(R.string.notification_msg_enable);

        this.updateNotification(msg, this.dataManager.getElapsedTime());
    }
    @Override
    public synchronized void disable(){
        if(this.isEnabled) {
            this.isEnabled = false;
        }
        String msg = this.getString(R.string.notification_msg_disable);
        this.updateNotification(msg, this.dataManager.getElapsedTime());
    }
    @Override
    public void advise(String msg) {
        if(this.isEnabled && this.hd != null) {
            Log.d("NotiUIService", "advise, msg : " + msg);
            Message hdMsg = new Message();
            Bundle bd = new Bundle();
            bd.putString("msg",msg);
            hdMsg.setData(bd);
            this.hd.sendMessage(hdMsg);
        }
    }

    public void cancelNotification(){
        this.disable();
        this.notificationManager.cancel(NotificationUIService.NOTIFICATION_ID);
    }
//    public Notification getNotification() {
//        return this.notification;
//    }
    public NotificationManager getNotificationManager() {
        return this.notificationManager;
    }
    public NotificationChannel getNotificationChannel() {
        return this.notificationChannel;
    }


}