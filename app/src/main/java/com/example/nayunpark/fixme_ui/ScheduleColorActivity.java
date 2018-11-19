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
        colorInfoArrayList.add(new SettingsColorInfo(R.drawable.ic_red_circle_padding, "빨간색"));
        colorInfoArrayList.add(new SettingsColorInfo(R.drawable.ic_pink_circle, "분홍색"));
        colorInfoArrayList.add(new SettingsColorInfo(R.drawable.ic_yellow_circle, "노란색"));
        colorInfoArrayList.add(new SettingsColorInfo(R.drawable.ic_yellowgreen_circle, "연두색"));
        colorInfoArrayList.add(new SettingsColorInfo(R.drawable.ic_green_circle, "초록색"));
        colorInfoArrayList.add(new SettingsColorInfo(R.drawable.ic_mint_circle, "민트색"));
        colorInfoArrayList.add(new SettingsColorInfo(R.drawable.ic_skyblue_circle, "하늘색"));
        colorInfoArrayList.add(new SettingsColorInfo(R.drawable.ic_blue_circle, "파란색"));
        colorInfoArrayList.add(new SettingsColorInfo(R.drawable.ic_purple_circle, "보라색"));

        ColorRecyclerAdapter colorRecyclerAdapter = new ColorRecyclerAdapter(colorInfoArrayList);
        recyclerView.setAdapter(colorRecyclerAdapter);
    }
}
