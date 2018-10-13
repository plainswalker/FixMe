package com.aplusstory.fixme;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.Date;


public class NotificationUIService extends Service implements NotificationUIManager{
    private static String NOTIFICATION_CHANNEL_ID = "FixMe_Noti";
    private static int NOTIFICATION_ID = (int) new Date().getTime();


    private NotificationDataManager dataManager;
    private boolean isEnabled;

    private Context context;
    private NotificationManager notificationManager;
    private NotificationChannel notificationChannel;

    private Thread thd;
    private Handler hd;

//    private Notification notification;
//    private Toast ltmi;`

    public NotificationUIService(NotificationDataManager nm){
        this.context = this;
        this.setDataManager(nm);
        this.notificationManager = (NotificationManager) this.context.getSystemService(NotificationManager.class);

        CharSequence nchName = this.context.getString(R.string.notification_channel_name);
        String nchDescription = this.context.getString(R.string.notification_channel_description);

        String nchID = NotificationUIService.NOTIFICATION_CHANNEL_ID;
        int nchImportance = NotificationManager.IMPORTANCE_DEFAULT;

        this.notificationChannel = new NotificationChannel(nchID, nchName, nchImportance);
        this.notificationChannel.setDescription(nchDescription);
        this.notificationManager.createNotificationChannel(this.notificationChannel);

        this.thd = new NotificationUIService.ServiceThread(this.hd = new NotificationUIService.ServiceHandler());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.enable();
        this.thd.run();
        this.dataManager.run();

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

    public class ActionReceiver extends BroadcastReceiver{
        public final static String EXTRA_NAME = "notification_action";
        public final static int EXTRA_ENABLE = 0;
        public final static int EXTRA_DISABLE = 1;
        public final static int EXTRA_SETTINGS = 2;
        public final static int EXTRA_CANCEL = 3;
        @Override
        public void onReceive(Context context, Intent intent) {
            switch(intent.getIntExtra(ActionReceiver.EXTRA_NAME,-1)){
                case 0:
                    this.enableAction();
                    break;
                case 1:
                    this.disableAction();
                    break;
                case 2:
                    this.settingsAction();
                    break;
                case 3:
                    this.cancelAction();
                    break;
                default:
                    //something wrong
            }
        }

        private void enableAction(){
            NotificationUIService.this.enable();
        }
        private void disableAction(){
            NotificationUIService.this.disable();
        }
        private void settingsAction(){
            //TODO
        }
        private void cancelAction(){
            NotificationUIService.this.cancelNotification();
        }
    }
    public class ServiceThread extends Thread{
        Handler hd;

        public ServiceThread(Handler hd){
            super();
            this.hd = hd;
        }

        @Override
        public void run() {
            while(NotificationUIService.this.isEnabled){

            }
        }
    }
    public class ServiceHandler extends Handler{
//        public static final int MSG_

        @Override
        public void handleMessage(Message msg) {

        }
    }
    private Notification buildNotification(CharSequence contentText){
        CharSequence title = this.context.getString(R.string.app_name);
        CharSequence enableActText = this.isEnabled ?
                this.context.getString(R.string.notification_action_disable):
                this.context.getString(R.string.notification_action_enable);
        CharSequence settingsActText = this.context.getString(R.string.notification_action_settings);
        CharSequence cancelActText = this.context.getString(R.string.notification_action_cancel);

        Intent enableIntent = new Intent(this.context, ActionReceiver.class);
        enableIntent.putExtra(ActionReceiver.EXTRA_NAME, this.isEnabled? ActionReceiver.EXTRA_DISABLE:ActionReceiver.EXTRA_ENABLE);

        Intent settingsIntent = new Intent(this.context, ActionReceiver.class);
        settingsIntent.putExtra(ActionReceiver.EXTRA_NAME, ActionReceiver.EXTRA_SETTINGS);

        Intent cancelIntent = new Intent(this.context, ActionReceiver.class);
        cancelIntent.putExtra(ActionReceiver.EXTRA_NAME, ActionReceiver.EXTRA_CANCEL);

        PendingIntent enablePIntent = PendingIntent.getBroadcast(this.context, 0, enableIntent,0);
        PendingIntent settingsPIntent = PendingIntent.getBroadcast(this.context, 0, settingsIntent,0);
        PendingIntent cancelPIntent = PendingIntent.getBroadcast(this.context, 0, cancelIntent,0);

        Notification.Action enableAction = new Notification.Action.Builder(0, enableActText,enablePIntent).build();
        Notification.Action settingsAction = new Notification.Action.Builder(0, settingsActText, settingsPIntent).build();
        Notification.Action cancelAction = new Notification.Action.Builder(0, cancelActText, cancelPIntent).build();
        Notification.Builder ntb = new Notification.Builder(this.context, NotificationUIService.NOTIFICATION_CHANNEL_ID)
//                .setSmallIcon(R.drawable.FixMeIcon)
                .setContentTitle(title)
                .setContentText(contentText)
                .setVisibility(Notification.VISIBILITY_PRIVATE)
                .addAction(enableAction)
                .addAction(settingsAction)
                .addAction(cancelAction)
                .setOngoing(true)
                .setWhen(new Date().getTime())
                .setShowWhen(true);

        //setting options
        Notification nt = ntb.build();
        nt.flags |= Notification.FLAG_FOREGROUND_SERVICE;
        return nt;
    }
    public void updateNotification(String msg, long elapsedTimeInMilliSeconds){
        StringBuilder sb = new StringBuilder();

//        final long d = 24 * 60 * 60 * 1000;
        final long h = 60 * 60 * 1000;
        final long m = 60 * 1000;
//        final long s = 1000;

//        if(elapsedTimeInMilliSeconds % d > 0){
//            sb.append(elapsedTimeInMilliSeconds/d).append(this.context.getString(R.string.notification_msg_elapsed_time_second_postfix));
//        }

        if(elapsedTimeInMilliSeconds % h > 0){
            sb.append(elapsedTimeInMilliSeconds/h).append(this.context.getString(R.string.notification_msg_elapsed_time_hour_postfix));
        }

        if(elapsedTimeInMilliSeconds % m > 0){
            sb.append(elapsedTimeInMilliSeconds/m).append(this.context.getString(R.string.notification_msg_elapsed_time_minute_postfix));
        }

//        if(elapsedTimeInMilliSeconds % s  > 0){
//            sb.append(elapsedTimeInMilliSeconds/s).append(this.context.getString(R.string.notification_msg_elapsed_time_second_postfix));
//        }
        if(elapsedTimeInMilliSeconds < m){
            sb.append(this.context.getString(R.string.notification_mag_elapsed_time_under_minute));
        }
        sb.insert(0,msg);

        CharSequence contentText = (CharSequence) sb.toString();
        this.notificationManager.notify(NotificationUIService.NOTIFICATION_ID, this.buildNotification(contentText));
    }
    @Override
    public void enable(){
        if(!this.isEnabled) {
            synchronized (this){
                this.isEnabled = true;
            }

        }
        String msg = this.context.getString(R.string.notification_msg_enable);
        this.updateNotification(msg, this.dataManager.getElapsedTime());
    }
    @Override
    public void disable(){
        if(this.isEnabled) {
            synchronized (this) {
                this.isEnabled = false;
            }
        }
        String msg = this.context.getString(R.string.notification_msg_disable);
        this.updateNotification(msg, this.dataManager.getElapsedTime());
    }
    @Override
    public void advise(String msg) {
        if(this.isEnabled) {
            Toast tMsg = Toast.makeText(this.context, msg, Toast.LENGTH_LONG);

            tMsg.show();
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