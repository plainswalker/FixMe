package com.aplusstory.fixme;

import android.app.Activity;
import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import java.io.Serializable;

public class MainActivity extends Activity {
    Thread testloop = null;

    class TestLoop extends Thread{
        Context context;
        Intent serviceIntent;

        TestLoop(Context context, Intent intent){
            super();
            this.context = context;
            this.serviceIntent = intent;
        }

        @Override
        public void run() {
            while (true) {
                if (WinDetectService.getInstance() != null) {
                    MainActivity.this.startService(this.serviceIntent);
                    return;
                }

                try {
                    Thread.sleep(1250);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent it = new Intent(this.getApplicationContext(), NotificationUIService.class);
        if(this.testloop == null || !this.testloop.isAlive()){
            this.testloop = new TestLoop(this, it);
            this.testloop.start();
        }
//        this.startService(it);

    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
