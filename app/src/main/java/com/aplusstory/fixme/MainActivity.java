package com.aplusstory.fixme;

import android.app.Activity;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import java.io.Serializable;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent it = new Intent(this.getApplicationContext(), NotificationUIService.class);
        this.startService(it);

    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
