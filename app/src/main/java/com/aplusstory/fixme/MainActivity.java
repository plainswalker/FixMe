package com.aplusstory.fixme;

import android.app.Activity;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aplusstory.fixme.ScheduleActivity;
import com.aplusstory.fixme.SettingsActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button scheduleB = (Button) findViewById(R.id.ScheduleButton);
        scheduleB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ScheduleActivity.class);
                startActivity(intent);
            }
        });

        Button settingsB = (Button) findViewById(R.id.SettingsButton);
        settingsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}



//
//import android.app.Activity;
//import android.app.Notification;
//import android.app.Service;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.content.ServiceConnection;
//import android.os.Bundle;
//import android.os.IBinder;
//import android.util.Log;
//
//import java.io.Serializable;
//
//public class MainActivity extends Activity {
//    Thread testloop = null;
//
//    class TestLoop extends Thread{
//        Context context;
//        Intent serviceIntent;
//
//        TestLoop(Context context, Intent intent){
//            super();
//            this.context = context;
//            this.serviceIntent = intent;
//        }
//
//        @Override
//        public void run() {
//            while (true) {
//                if (WinDetectService.getInstance() != null) {
//                    MainActivity.this.startService(this.serviceIntent);
//                    return;
//                } else{
//                    Log.d("MainActivity", "waiting for accessibility service...");
//                }
//                try {
//                    Thread.sleep(1250);
//                } catch (InterruptedException e) {
//                    return;
//                }
//            }
//        }
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Intent it = new Intent(this.getApplicationContext(), NotificationUIService.class);
//        if(this.testloop == null || !this.testloop.isAlive()){
//            this.testloop = new TestLoop(this, it);
//            this.testloop.start();
//        }
////        this.startService(it);
//
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//    }
//}
