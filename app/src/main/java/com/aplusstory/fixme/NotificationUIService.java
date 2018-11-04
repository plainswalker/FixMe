package com.aplusstory.fixme;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

public class NotificationUIService extends Service implements NotificationUIManager{
    private static String NOTIFICATION_CHANNEL_ID = "FixMe_Noti";
    private static int NOTIFICATION_ID = (int) new Date().getTime();

    public static final int UPDATE_DELAY = 5000;

    private NotificationDataManager dataManager;
    private boolean isEnabled = false;

    private boolean isViberates = false;
    private int advTstTrsp = (byte)0xFF;

    private NotificationManager notificationManager = null;
    private NotificationChannel notificationChannel = null;
    private Notification.Builder notificationBuilder = null;

    private Thread thd = null;
    private Handler hd = null;
    private Binder bd = null;
    private IntentFilter intfl = null;
    private BroadcastReceiver br = null;
    private Vibrator vtor = null;

    public NotificationUIService(){
        super();
    }

    private void initialize(){
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

        if(this.dataManager == null){
            //test code
            this.dataManager = new RecognizerManager(this);
        }
        this.dataManager.addRecognizer(new UserRecognizer(this));
        this.dataManager.addRecognizer(new EnvironmentRecognizer(this));
        this.dataManager.enable();
        String setting;
        if((setting = this.dataManager.getUISetting(NotificationUIManager.VIBRATE_SETTING_KEY)) != null){
            this.isViberates = Boolean.parseBoolean(setting);
        }
        if((setting = this.dataManager.getUISetting(NotificationUIManager.TRANSPARENCY_SETTING_KEY)) != null){
            this.advTstTrsp = Integer.parseInt(setting);
        }

        if(this.intfl == null){
            this.intfl = new IntentFilter();
            this.intfl.addAction(NotificationActionReceiver.NOTIFICATION_ACTION_ENABLE);
            this.intfl.addAction(NotificationActionReceiver.NOTIFICATION_ACTION_DISABLE);
            this.intfl.addAction(NotificationActionReceiver.NOTIFICATION_ACTION_SETTINGS);
            this.intfl.addAction(NotificationActionReceiver.NOTIFICATION_ACTION_QUIT);
        }

        if(this.br == null){
            this.br = new NotificationActionReceiver();
            this.registerReceiver(this.br, this.intfl);
        }

        if(this.hd == null) {
            this.hd = new NotificationUIService.ServiceHandler(this);
        }
        if(this.thd == null || !this.thd.isAlive()) {
            this.thd = new NotificationUIService.ServiceThread(this.hd);
        }
        if(this.bd == null){
            this.bd = new ServiceBinder();
        }
        if(this.vtor == null){
            this.vtor = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return this.bd;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.initialize();
        this.enable();
        if(this.dataManager != null) {
            this.dataManager.enable();
        }
        if(this.thd != null && !this.thd.isAlive()) {
            this.thd.start();
        }
        this.notificationManager = (NotificationManager) this.getSystemService(NotificationManager.class);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public synchronized void setDataManager(NotificationDataManager dataManager) {
        this.dataManager = dataManager;
    }

    public class ServiceThread extends Thread{
        Handler hd;

        public ServiceThread(Handler hd){
            super();
            this.hd = hd;
        }

        @Override
        public void run() {
            NotificationUIService that = NotificationUIService.this;
            while(true){
                that.updateNotification(
                        ((that.isEnabled) ?
                                that.getString(R.string.notification_msg_enable)
                                : that.getString(R.string.notification_msg_disable)),
                        that.dataManager.getElapsedTime()
                );
                synchronized (that.dataManager) {
                    if (that.isEnabled && that.dataManager.checkCondition()) {
                        int cond = that.dataManager.getConditionCode();
                        Log.d("NotiUIService", "Thread CheckCond, cond : " + Integer.toString(cond));
                        this.hd.sendEmptyMessage(0);
                        StringBuilder msg = new StringBuilder("");
                        if ((cond & NotificationDataManager.COND_TIME_OVERUSE) != 0) {
                            msg.append(that.getString(R.string.advice_msg_posture));
                        }

                        if ((cond & NotificationDataManager.COND_ENVIRON_LIGHT) != 0) {
                            if (msg.length() > 1) {
                                msg.append("\n");
                            }
                            msg.append(that.getString(R.string.advise_msg_light));
                        }
                        if (msg.length() > 0) {
                            that.advise(msg.toString());
                        }
                    }
                }

                try {
                    Thread.sleep(NotificationUIService.UPDATE_DELAY);
                } catch (InterruptedException e){
                    return;
                }
            }
        }
    }
    public class ServiceHandler extends Handler{
        Context context;

        public ServiceHandler(Context context){
            super();
            this.context = context;
            Log.d("NotiUIService","CreateHandler");
        }

        @Override
        public void handleMessage(Message msg) {
            NotificationUIService that = NotificationUIService.this;
            Log.d("NotiUIService", "HandleMsg");
            Bundle bd = msg.getData();
            String sntMsg = (String)bd.getCharSequence("msg");
            if(sntMsg != null){
                Toast tst = Toast.makeText(context, sntMsg, Toast.LENGTH_SHORT);
                tst.getView().getBackground().setAlpha(that.advTstTrsp);
                tst.show();
                if(that.isViberates) {
                    that.vtor.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                }
            }
        }
    }

    public class NotificationActionReceiver extends BroadcastReceiver{
        public final static String NOTIFICATION_ACTION_ENABLE = "com.aplusstory.fixme.action.NOTIFICATION_ACTION_ENABLE";
        public final static String NOTIFICATION_ACTION_DISABLE = "com.aplusstory.fixme.action.NOTIFICATION_ACTION_DISABLE";
        public final static String NOTIFICATION_ACTION_SETTINGS = "com.aplusstory.fixme.action.NOTIFICATION_ACTION_SETTINGS";
        public final static String NOTIFICATION_ACTION_QUIT = "com.aplusstory.fixme.action.NOTIFICATION_ACTION_QUIT";

        public final static String EXTRA_NAME = "notification_action";

        public final static int EXTRA_ENABLE = 0;
        public final static int EXTRA_DISABLE = 1;
        public final static int EXTRA_SETTINGS = 2;
        public final static int EXTRA_QUIT = 3;

        @Override
        public void onReceive(Context context, Intent intent) {
            int actCode =intent.getIntExtra(NotificationActionReceiver.EXTRA_NAME, -1);
            Log.d("NotiActRecev", "onReceive, intent extra integer value : " + Integer.toString(actCode));
            switch(actCode){
                case 0:
                    this.enableAction(context);
                    break;
                case 1:
                    this.disableAction(context);
                    break;
                case 2:
                    this.settingsAction(context);
                    break;
                case 3:
                    this.quitAction(context);
                    break;
                default:
                    //something wrong
            }
        }

        private void enableAction(Context context){
            Log.d("NotiActRecev","enable action received");
            NotificationUIService.this.enable();
//            Toast.makeText(context,"enable", Toast.LENGTH_SHORT).show();
        }
        private void disableAction(Context context){
            Log.d("NotiActRecev","disable action received");
            NotificationUIService.this.disable();
//            Toast.makeText(context,"disable", Toast.LENGTH_SHORT).show();
        }
        private void settingsAction(Context context){
            Log.d("NotiActRecev","settings action received");
            //change window to settings
//            Toast.makeText(context,"setting", Toast.LENGTH_SHORT).show();
        }
        private void quitAction(Context context){
            Log.d("NotiActRecev","quit action received");
            NotificationUIService.this.quit();
//            Toast.makeText(context,"quit", Toast.LENGTH_SHORT).show();
        }
    }

    public class ServiceBinder extends Binder{
        public NotificationUIService getInstance(){
            return NotificationUIService.this;
        }
    }

    private Notification buildNotification(CharSequence contentText){
        if(this.notificationBuilder == null) {
            CharSequence title = this.getString(R.string.app_name);

            Intent ntIntent = new Intent(this, NotificationUIService.class);
            PendingIntent ntPIntent = PendingIntent.getActivity(this,0, ntIntent, 0);

            this.notificationBuilder = new Notification.Builder(this, NotificationUIService.NOTIFICATION_CHANNEL_ID)
//                .setSmallIcon(R.drawable.FixMeIcon)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle(title)
//                .setContentText(contentText)
                    .setVisibility(Notification.VISIBILITY_PRIVATE)
                    .setOngoing(true)
                    .setWhen(new Date().getTime())
                    .setShowWhen(true)
                    .setOnlyAlertOnce(true)
                    .setContentIntent(ntPIntent);
            //setting options
        }
        this.notificationBuilder.setContentText(contentText);

        Intent enableIntent = new Intent(
                    this.isEnabled ?
                        NotificationActionReceiver.NOTIFICATION_ACTION_DISABLE :
                        NotificationActionReceiver.NOTIFICATION_ACTION_ENABLE
        );
        enableIntent.putExtra(
                    NotificationActionReceiver.EXTRA_NAME,
                    this.isEnabled ?
                        NotificationUIService.NotificationActionReceiver.EXTRA_DISABLE :
                        NotificationUIService.NotificationActionReceiver.EXTRA_ENABLE
        );

        Intent settingsIntent = new Intent(NotificationActionReceiver.NOTIFICATION_ACTION_SETTINGS);
        settingsIntent.putExtra(NotificationActionReceiver.EXTRA_NAME, NotificationUIService.NotificationActionReceiver.EXTRA_SETTINGS);
        Intent quitIntent = new Intent(NotificationActionReceiver.NOTIFICATION_ACTION_QUIT);
        quitIntent.putExtra(NotificationActionReceiver.EXTRA_NAME, NotificationUIService.NotificationActionReceiver.EXTRA_QUIT);

        PendingIntent enablePIntent = PendingIntent.getBroadcast(this, 0, enableIntent, 0);
        PendingIntent settingsPIntent = PendingIntent.getBroadcast(this, 0, settingsIntent, 0);
        PendingIntent quitPIntent = PendingIntent.getBroadcast(this, 0, quitIntent, 0);

        CharSequence enableActText = this.isEnabled ?
                this.getString(R.string.notification_action_disable) :
                this.getString(R.string.notification_action_enable);
        CharSequence settingsActText = this.getString(R.string.notification_action_settings);
        CharSequence quitActText = this.getString(R.string.notification_action_quit);

        Notification.Action enableAction = new Notification.Action.Builder(R.drawable.ic_launcher_background, enableActText, enablePIntent).build();
        Notification.Action settingsAction = new Notification.Action.Builder(R.drawable.ic_launcher_background, settingsActText, settingsPIntent).build();
        Notification.Action quitAction = new Notification.Action.Builder(R.drawable.ic_launcher_background, quitActText, quitPIntent).build();

        this.notificationBuilder.setActions(enableAction, settingsAction, quitAction);

        Notification nt = this.notificationBuilder.build();
        nt.flags |= Notification.FLAG_FOREGROUND_SERVICE;
        return nt;
    }

    public void updateNotification(String msg, long elapsedTimeInMilliSeconds){
        if(this.dataManager != null && this.dataManager.isEnabled()) {
            StringBuilder sb = new StringBuilder(msg);

            sb.append("   ").append(this.getString(R.string.notification_msg_elapsed_time_prefix)).append(" ");
            if(elapsedTimeInMilliSeconds > 0) {
//        final long d = 24 * 60 * 60 * 1000;
                final long h = 60 * 60 * 1000;
                final long m = 60 * 1000;
                final long s = 1000;

//        if(elapsedTimeInMilliSeconds / d > 0){
//            sb.append(elapsedTimeInMilliSeconds/d).append(this.getString(R.string.notification_msg_elapsed_time_second_postfix));
//        }

                if (elapsedTimeInMilliSeconds / h > 0) {
                    sb.append(elapsedTimeInMilliSeconds / h).append(this.getString(R.string.notification_msg_elapsed_time_hour_postfix));
                }

                if (elapsedTimeInMilliSeconds / m > 0) {
                    sb.append(elapsedTimeInMilliSeconds / m).append(this.getString(R.string.notification_msg_elapsed_time_minute_postfix));
                }

//            if (elapsedTimeInMilliSeconds / s > 0) {
//                sb.append(elapsedTimeInMilliSeconds / s).append(this.getString(R.string.notification_msg_elapsed_time_second_postfix));
//            }

                if (elapsedTimeInMilliSeconds < m){
                    sb.append(this.getString(R.string.notification_msg_elapsed_time_under_minute));
                }
            }
            CharSequence contentText = (CharSequence) sb.toString();
            Notification nt = this.buildNotification(contentText);
            this.startForeground(NotificationUIService.NOTIFICATION_ID, nt);
            this.notificationManager.notify(NotificationUIService.NOTIFICATION_ID, nt);
        }
    }

    @Override
    public synchronized void enable(){
        if(!this.isEnabled) {
            this.isEnabled = true;
        }
        String msg;
        long elapsed;
        if(this.dataManager != null){
            elapsed = this.dataManager.getElapsedTime();
            msg = this.getString(R.string.notification_msg_enable);
        }
        else {
            elapsed = -1;
            msg = this.getString(R.string.notification_msg_disable);
        }
        this.updateNotification(msg, elapsed);
    }

    @Override
    public synchronized void disable(){
        if(this.isEnabled) {
            this.isEnabled = false;
        }
        String msg = this.getString(R.string.notification_msg_disable);
        long elapsed;
        if(this.dataManager != null){
            elapsed = this.dataManager.getElapsedTime();
        }
        else {
            elapsed = -1;
        }
        this.updateNotification(msg, elapsed);
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

    public synchronized boolean isEnabled() {
        return isEnabled;
    }

    public synchronized void quit(){
        this.disable();
        if(this.dataManager != null) {
            this.dataManager.disable();
            this.dataManager.destroy();
        }
        this.notificationManager.cancel(NotificationUIService.NOTIFICATION_ID);
        this.thd.interrupt();
        this.unregisterReceiver(this.br);
        this.stopSelf();
    }

    public NotificationManager getNotificationManager() {
        return this.notificationManager;
    }
    public NotificationChannel getNotificationChannel() {
        return this.notificationChannel;
    }
    public NotificationDataManager getDataManager() {
        return dataManager;
    }
}