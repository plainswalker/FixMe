package com.aplusstory.fixme;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TestLocationActivity extends Activity implements View.OnClickListener{
    final String start_service = "on";
    final String stop_service = "off";

    boolean service = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_location);
        Button serviceB = (Button) findViewById(R.id.toggleService);
        Button locaButton = (Button) findViewById(R.id.getLoca);
        Button importLocaButton = (Button) findViewById(R.id.importLocaData);
        serviceB.setOnClickListener(this);
        serviceB.setText(start_service);
        locaButton.setOnClickListener(this);
        importLocaButton.setOnClickListener(this);
        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
    }


    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        switch (b.getId()){
            case R.id.toggleService:
                Intent it = new Intent(this.getApplicationContext(), CurrentLocationManager.class);
                if(!this.service){
                    startForegroundService(it);
                    b.setText(this.stop_service);
                } else{
                    stopService(it);
                    b.setText(this.start_service);
                }
                this.service = !this.service;
                break;
            case R.id.getLoca:
                TextView dateText = findViewById(R.id.dateText);
                TextView latiText = findViewById(R.id.latiText);
                TextView longiText = findViewById(R.id.longtiText);
                SharedPreferences sp = getSharedPreferences(LocationFileManager.FILENAME_CURRENT_LOCATION, 0);
                dateText.setText(sp.getString(LocationDataManager.LocatonData.KEY_DATETIME,""));
                latiText.setText(sp.getString(LocationDataManager.LocatonData.KEY_LATITUDE,""));
                longiText.setText(sp.getString(LocationDataManager.LocatonData.KEY_LONGITUDE,""));
                break;
            case R.id.importLocaData:
                try {
                    LocationFileManager fm = new LocationFileManager(this, LocationFileManager.READ_ONLY);
                    TextView locaDataText = findViewById(R.id.textLocaData);
                    StringBuilder sb = new StringBuilder(fm.getData(fm.getFilenameForToday()));
                    locaDataText.setText(sb.toString());
                } catch (Exception e){
                    Log.d(TestLocationActivity.class.getName(), e.toString());
                }
                break;
        }
    }
}
