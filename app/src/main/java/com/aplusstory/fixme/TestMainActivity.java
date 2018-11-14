package com.aplusstory.fixme;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONObject;

public class TestMainActivity extends AppCompatActivity implements View.OnClickListener{

    ScheduleAlarmManager am = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main);
        if(this.am == null){
            this.am = new ScheduleAlarmManager(this);
        }

        Button b = (Button)findViewById(R.id.alarmTest);
        b.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(this.am != null){
            SharedPreferences sp = getSharedPreferences(LocationFileManager.FILENAME_CURRENT_LOCATION, 0);
            long now = System.currentTimeMillis();
            double lat, longt;
            try {
                lat = Double.parseDouble(sp.getString(LocationDataManager.LocatonData.KEY_LATITUDE, ""));
                longt = Double.parseDouble(sp.getString(LocationDataManager.LocatonData.KEY_LONGTITUDE, ""));
                LocationDataManager.LocatonData loca = new LocationDataManager.LocatonData(now, lat, longt);
                this.am.setAlarm( now + 5500, loca);
            } catch(Exception e){
                Log.d(this.getClass().getName(), e.toString());
            }

        }
    }
}
