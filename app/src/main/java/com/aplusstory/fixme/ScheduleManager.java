package com.aplusstory.fixme;


import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleManager implements ScheduleDataManager {
    private Context context;
    private ScheduleFileManager fm = null;
    private Map<String, ScheduleData> schBuf = null;
    private Map<Integer, String> schMonthMap = null;
    private List<String> schList = null;

    public ScheduleManager(Context context){
        this.context = context;
        this.fm = new ScheduleFileManager(this.context);
        this.schBuf = new HashMap<>();
        this.schList = this.fm.getScheduleList();
        for(String s : schList){
            schBuf.put(s, this.fm.getSchedule(s));
        }
    }

    public List<String> getScheduleList(){
        ArrayList<String> rt = new ArrayList<>(this.schList.size());
        Collections.copy(rt, this.schList);
        return rt;
    }

    public ScheduleData getData(){
        return null;
    }

    @Override
    public void setFileManager(FileManager f) {
        if(f instanceof ScheduleFileManager){
            this.fm = (ScheduleFileManager) f;
        }
    }
}
