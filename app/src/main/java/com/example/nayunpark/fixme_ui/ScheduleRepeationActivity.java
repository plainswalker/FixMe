package com.example.nayunpark.fixme_ui;

import android.app.DatePickerDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.Calendar;

public class ScheduleRepeationActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView textViewED, textViewRD;
    FragmentManager fragmentManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_repeation);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_full_menu);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);

        final DatePickerDialog datePickerDialog = new DatePickerDialog(this, listener, year, month, day);

        textViewRD = (TextView) findViewById(R.id.repeatDay);
        textViewRD.setText("");

        textViewED = (TextView) findViewById(R.id.endRDate);
        textViewED.setText(year+"년 "+(month + 1)+"월 "+day+"일");

        if(this.fragmentManager == null){
            this.fragmentManager = this.getSupportFragmentManager();
        }

        textViewED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        Button weeklyButton = (Button) findViewById(R.id.weeklyButton);
        weeklyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentManager != null && !fragmentManager.isDestroyed()){
                    Fragment weeklyFragment = (Fragment) new ScheduleRepeatWeeklyFragment();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    final FragmentTransaction add = fragmentTransaction.add(R.id.fragment_blank, weeklyFragment);
//                    ft.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });

//        ScheduleRepeatWeeklyFragment scheduleRepeatWeeklyFragment = new ScheduleRepeatWeeklyFragment();
//        if(scheduleRepeatWeeklyFragment.getCheckDay() != "") {
//            Toast.makeText(getApplicationContext(), scheduleRepeatWeeklyFragment.getCheckDay(), Toast.LENGTH_SHORT).show();
//        }

    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            TextView textView = (TextView) findViewById(R.id.endRDate);
            textView.setText(year+"년 "+(monthOfYear + 1)+"월 "+dayOfMonth+"일");
        }
    };

}
