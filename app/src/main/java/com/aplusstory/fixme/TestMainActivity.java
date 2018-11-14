package com.aplusstory.fixme;

import android.app.AlarmManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONObject;

public class TestMainActivity extends AppCompatActivity implements View.OnClickListener{

    AlarmManager am = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main);
        if(this.am == null){
            this.am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }

        Button b = (Button)findViewById(R.id.alarmTest);
        b.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(this.am != null){
            long now = System.currentTimeMillis();
            LocationDataManager.LocatonData loca = new LocationDataManager.LocatonData(now, 0,0);
            ScheduleAlarmManager.setAlarm(this, this.am,now + 3000, loca);
        }
    }
}
