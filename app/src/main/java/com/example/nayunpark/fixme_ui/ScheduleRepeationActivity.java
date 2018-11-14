package com.example.nayunpark.fixme_ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.Calendar;

public class ScheduleRepeationActivity extends AppCompatActivity
        implements ScheduleRepeatWeeklyFragment.OnFragmentInteractionListener {
    Toolbar toolbar;
    TextView textViewED, textViewRD;
    FragmentManager fragmentManager = null;
    String dates = "none";
    //non=0, daily=1, weekly=2, monthly=3, yearly=4
    int repeatState = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_repeation);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);

        final DatePickerDialog datePickerDialog = new DatePickerDialog(this, listener, year, month, day);

        textViewRD = (TextView) findViewById(R.id.repeatDay);
        textViewRD.setText("없음");

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

        Button noneButton = (Button) findViewById(R.id.noneButton);
        noneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewRD.setText("없음");
                repeatState = 0;
            }
        });

        Button dailyButton = (Button) findViewById(R.id.dailyButton);
        dailyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewRD.setText("매일");
                repeatState = 1;
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


        Button monthlyButton = (Button) findViewById(R.id.monthlyButton);
        monthlyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewRD.setText("매달");
                repeatState = 3;
            }
        });

        Button yearlyButton = (Button) findViewById(R.id.yearlyButton);
        yearlyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewRD.setText("매년");
                repeatState = 4;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.schedule_attribute_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean rt = super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.schedule_confirm) {
//            ScheduleFragment scheduleFragment = new ScheduleFragment();
//            ScheduleData scheduleData = new ScheduleData(dates, repeatState);
//            Bundle bundle = new Bundle();
//            bundle.putParcelable("scheduleData", scheduleData);
//            scheduleFragment.setArguments(bundle);

            Intent intent = new Intent();
            intent.putExtra("result", "value");
            setResult(RESULT_OK, intent);
            finish();
        }

        return rt;
    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            TextView textView = (TextView) findViewById(R.id.endRDate);
            textView.setText(year+"년 "+(monthOfYear + 1)+"월 "+dayOfMonth+"일");
        }
    };

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onCheckedDateSet(String dates) {
        this.dates = dates;
    }
}
