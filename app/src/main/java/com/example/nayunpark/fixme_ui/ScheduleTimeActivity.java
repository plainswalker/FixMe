package com.example.nayunpark.fixme_ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class ScheduleTimeActivity extends AppCompatActivity  {
    Toolbar toolbar2;
    TextView textViewST,textViewSD, textViewET, textViewED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_time);

        toolbar2 = (Toolbar) findViewById(R.id.stToolbar);
        setSupportActionBar(toolbar2);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_full_menu);

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);

        final TimePickerDialog dialog = new TimePickerDialog(this, listenerST, hour, minute,false);
        final DatePickerDialog datePickerDialog = new DatePickerDialog(this, listenerSD, year, month, day);
        final TimePickerDialog dialog2 = new TimePickerDialog(this, listenerET, hour, minute,false);
        final DatePickerDialog datePickerDialog2 = new DatePickerDialog(this, listenerED, year, month, day);

        textViewST = (TextView) findViewById(R.id.startTime);
        textViewST.setText(String.format("%02d", hour)+":"+String.format("%02d", minute));
        textViewSD = (TextView) findViewById(R.id.startDate);
        textViewSD.setText(year+"년 "+month+"월 "+day+"일");
        textViewET = (TextView) findViewById(R.id.endTime);
        textViewET.setText(String.format("%02d", hour)+":"+String.format("%02d", minute));
        textViewED = (TextView) findViewById(R.id.endDate);
        textViewED.setText(year+"년 "+month+"월 "+day+"일");

        textViewST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        textViewSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        textViewET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.show();
            }
        });

        textViewED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog2.show();
            }
        });
    }

    private TimePickerDialog.OnTimeSetListener listenerST = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            TextView textView = (TextView)findViewById(R.id.startTime);

            textView.setText(String.format("%02d", hourOfDay)+":"+String.format("%02d", minute));

        }
    };

    private DatePickerDialog.OnDateSetListener listenerSD = new DatePickerDialog.OnDateSetListener() {

        @Override

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            TextView textView = (TextView)findViewById(R.id.startDate);
            textView.setText(year+"년 "+monthOfYear+"월 "+dayOfMonth+"일");
        }

    };

    private TimePickerDialog.OnTimeSetListener listenerET = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            TextView textView = (TextView)findViewById(R.id.endTime);

            textView.setText(String.format("%02d", hourOfDay)+":"+String.format("%02d", minute));

        }
    };

    private DatePickerDialog.OnDateSetListener listenerED = new DatePickerDialog.OnDateSetListener() {

        @Override

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            TextView textView = (TextView)findViewById(R.id.endDate);
            textView.setText(year+"년 "+monthOfYear+"월 "+dayOfMonth+"일");
        }

    };



}
