package com.example.nayunpark.fixme_ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class ScheduleColorActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_color);

        recyclerView = (RecyclerView) findViewById(R.id.colorRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<SettingsColorInfo> colorInfoArrayList = new ArrayList<>();
        colorInfoArrayList.add(new SettingsColorInfo(R.drawable.ic_info_black_24dp, "black"));
        colorInfoArrayList.add(new SettingsColorInfo(R.drawable.ic_info_black_24dp, "red"));

        ColorRecyclerAdapter colorRecyclerAdapter = new ColorRecyclerAdapter(colorInfoArrayList);
        recyclerView.setAdapter(colorRecyclerAdapter);
    }
}
