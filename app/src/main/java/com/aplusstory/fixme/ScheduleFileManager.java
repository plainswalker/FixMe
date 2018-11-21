package com.aplusstory.fixme;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleFileManager implements FileManager {
    public static final String FILENAME_SCHEDULE_PREFIX = "schedule_";
    public static final String FILENAME_SCHEDULE_LIST = FILENAME_SCHEDULE_PREFIX + "list";

    private Context context;
    private SharedPreferences sp = null;
    private Map<String, String> listSch;

    public ScheduleFileManager(Context context){
        this.context = context;
        this.sp = context.getSharedPreferences(FILENAME_SCHEDULE_LIST, 0);
        Map<String, ?> listSch = this.sp.getAll();
        this.listSch = new HashMap<>();
        for(String s : listSch.keySet()){
            this.listSch.put(s, listSch.get(s).toString());
        }
    }

    public List<String> getScheduleList(){
        return new ArrayList<>(this.listSch.keySet());
    }

    @Nullable
    public ScheduleDataManager.ScheduleData getSchedule(String name){
        ScheduleDataManager.ScheduleData sch = null;
        if(listSch.containsKey(name)){
            try {
               sch = ScheduleDataManager.ScheduleData.parseJSON(new JSONObject(this.getData(name)));
            }catch(Exception e){
                Log.d(this.getClass().getName(), e.toString());
            }
        }

        return sch;
    }
    public boolean putSchedule(ScheduleDataManager.ScheduleData sch){
        boolean rt = false;
        FileWriter fw = null;
        String filepath;

        if(this.listSch.containsKey(sch.name)){
            filepath = this.listSch.get(sch.name);
        } else{
            filepath = context.getFilesDir() + "/" + FILENAME_SCHEDULE_PREFIX + sch.name;
        }

        try{
            fw = new FileWriter(filepath, false);
            fw.write(sch.toString());
            rt = true;
            fw.flush();
            this.sp.edit().putString(sch.name, filepath).apply();
        } catch(IOException e){
            Log.d(this.getClass().getName(), e.toString());
        }

        return rt;
    }

    @Nullable
    @Override
    public String getData(String schName) {
        FileReader fr = null;
        StringBuilder sb = new StringBuilder();

        try {
            fr = new FileReader(this.listSch.get(schName));
            CharBuffer buf = CharBuffer.allocate(255);
            int i;
            while (fr.ready()) {
                i = fr.read(buf);
                sb.append(buf);
            }
        }catch (Exception e){
            Log.d(this.getClass().getName(), e.toString());
        }

        return sb.toString();
    }

    @Override
    public boolean setData(String schName, String jsonSch) {
        ScheduleDataManager.ScheduleData sch;
        try {
            if ((sch = ScheduleDataManager.ScheduleData.parseJSON(new JSONObject(jsonSch))) != null)
                return this.putSchedule(sch);
            else
                return false;
        } catch(JSONException e){
            return false;
        }
    }
}