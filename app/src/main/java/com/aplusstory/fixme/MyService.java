package com.aplusstory.fixme;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class MyService extends Service {
    NotificationManager ntm;
    NotificationChannel ntch;
    MyServiceThread thd = null;
    Notification nt;
    String msgdata = "";

    @Override
    public IBinder onBind(Intent intent) {
//         TODO: Return the communication channel to the service.
        return null;
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.ntm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        this.ntch = new NotificationChannel("MyServiceChannel00", "MyService",NotificationManager.IMPORTANCE_DEFAULT);
        this.ntch.setDescription("fewniufhbjewf");
        this.ntch.enableLights(false);
        this.ntch.enableVibration(false);
        this.ntm.createNotificationChannel(this.ntch);


        MyService.MyServiceHandler hd = new MyService.MyServiceHandler();
        if(this.thd == null || !this.thd.isAlive()) {
            this.thd = new MyServiceThread(hd);
            thd.start();
        }
        WinDetectService wds =WinDetectService.getInstance();
        if(wds != null){
            wds.setHandler(hd);
        }
//        Toast t = Toast.makeText(this,"aaaaa",Toast.LENGTH_SHORT);
//        t.setGravity(3,0,0);
//        t.show();
        return START_NOT_STICKY;
    }

    public void clearNotification(){
        this.ntm.cancelAll();
    }

    @Override
    public void onDestroy() {
        this.clearNotification();
        if(thd != null) {
            thd.stopService();
            thd = null;
        }

//        Toast t = Toast.makeText(this,"abe",Toast.LENGTH_SHORT);
//        t.setGravity(3,0,0);
//        t.show();
    }

    class MyServiceHandler extends Handler{
        @Override
            public void handleMessage(android.os.Message msg) {
            Intent intent = new Intent(MyService.this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(MyService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            if(msg.what == 1){
                MyService.this.msgdata = msg.obj.toString();
            }
            MyService.this.nt = new NotificationCompat.Builder(MyService.this)
                                .setContentTitle("title")
                                .setContentText(MyService.this.msgdata)
                                .setSmallIcon(R.drawable.example_picture)
                                .setTicker("tick")
                                .setContentIntent(pendingIntent)
                                .setOngoing(true)
                                .setChannelId("MyServiceChannel00")
                                .build();

            MyService.this.nt.flags = Notification.FLAG_FOREGROUND_SERVICE;
            MyService.this.ntm.notify(777, MyService.this.nt);
            startForeground(777,MyService.this.nt);
        }
    }

}
