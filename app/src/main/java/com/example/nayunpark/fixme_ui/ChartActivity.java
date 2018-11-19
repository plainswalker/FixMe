package com.example.nayunpark.fixme_ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChartActivity extends AppCompatActivity {
    Toolbar toolbar;
    Fragment fragment;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        fragment = new PieChartFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.footprint_fragment, fragment);
        fragmentTransaction.commit();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean rt = super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.ic_footprint_calendar:
//                TODO: Replace fragment
//                Fragment fragment2 = new YearlyCalendarFragment();
//
//                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.yearly_calendar, fragment2);
//                fragmentTransaction.commit();
                Toast.makeText(this, "Yearly Calendar", Toast.LENGTH_SHORT).show();

                rt = true;
                break;
            case android.R.id.home:
                Toast.makeText(this, "Menu", Toast.LENGTH_SHORT).show();
                rt = true;
                break;
            default:
                //error
        }
        return rt;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.chart_menu, menu);
        return true;
    }
}
