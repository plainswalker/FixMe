package com.aplusstory.fixme;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aplusstory.fixme.ScheduleActivity;
import com.aplusstory.fixme.SettingsActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button scheduleB = (Button) findViewById(R.id.ScheduleButton);
        scheduleB.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ScheduleActivity.class);
//                    Intent intent = new Intent(getApplicationContext(), NewCalendarActivity.class);
                    startActivity(intent);
                }
            }
        );

        Button todayFootprintButton = (Button) findViewById(R.id.FootprintButton);
        todayFootprintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChartActivity.class);
                startActivity(intent);
            }
        });

        Button settingsB = (Button) findViewById(R.id.SettingsButton);
        settingsB.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                    startActivity(intent);
                }
            }
        );
    }
}
