package com.aplusstory.fixme;


import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleManager implements ScheduleDataManager {
    private Context context;
    private ScheduleFileManager fm = null;
    private Map<String, ScheduleData> schBuf = null;
    private List<String> schList = null;

    public ScheduleManager(Context context){
        this.schBuf = new HashMap<>();
        this.schList = this.fm.getScheduleList();
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
