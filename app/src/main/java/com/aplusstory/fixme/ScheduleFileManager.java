package com.aplusstory.fixme;

import android.content.Context;
import android.content.SharedPreferences;

import org.jetbrains.annotations.Nullable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ScheduleFileManager implements FileManager {
    public static final String FILENAME_SCHEDULE = "schedules";

    private Context context;
    private SharedPreferences sp = null;

    public ScheduleFileManager(Context context){
        this.context = context;
        this.sp = context.getSharedPreferences(FILENAME_SCHEDULE, 0);
    }

    @Nullable
    @Override
    public String getData(String schName) {
        return null;
    }

    public List<String> getScheduleList(){
        ArrayList<String> schedules = null;

        return schedules;
    }
    @Override
    public boolean setData(String schName, String schString) {
        return false;
    }
}
