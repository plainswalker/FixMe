package com.aplusstory.fixme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class ScheduleColorActivity extends AppCompatActivity{
    public static final String EXTRA_NAME_ARGUMENT = "color_argument";

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
        colorInfoArrayList.add(new SettingsColorInfo(R.drawable.ic_info_black_24dp, ScheduleDataManager.TableColor.BLACK));
        colorInfoArrayList.add(new SettingsColorInfo(R.drawable.ic_info_black_24dp, ScheduleDataManager.TableColor.RED));
        colorInfoArrayList.add(new SettingsColorInfo(R.drawable.ic_info_black_24dp, ScheduleDataManager.TableColor.BLUE));
        colorInfoArrayList.add(new SettingsColorInfo(R.drawable.ic_info_black_24dp, ScheduleDataManager.TableColor.GREEN));
        colorInfoArrayList.add(new SettingsColorInfo(R.drawable.ic_info_black_24dp, ScheduleDataManager.TableColor.YELLOW));
        colorInfoArrayList.add(new SettingsColorInfo(R.drawable.ic_info_black_24dp, ScheduleDataManager.TableColor.SKYBLUE));
        colorInfoArrayList.add(new SettingsColorInfo(R.drawable.ic_info_black_24dp, ScheduleDataManager.TableColor.PINK));
        colorInfoArrayList.add(new SettingsColorInfo(R.drawable.ic_info_black_24dp, ScheduleDataManager.TableColor.LIGHTGREEN));
        colorInfoArrayList.add(new SettingsColorInfo(R.drawable.ic_info_black_24dp, ScheduleDataManager.TableColor.PURPLE));
        colorInfoArrayList.add(new SettingsColorInfo(R.drawable.ic_info_black_24dp, ScheduleDataManager.TableColor.CYAN));

        ScheduleColorRecyclerAdapter colorRecyclerAdapter = new ScheduleColorRecyclerAdapter(colorInfoArrayList);
        recyclerView.setAdapter(colorRecyclerAdapter);
    }
}
