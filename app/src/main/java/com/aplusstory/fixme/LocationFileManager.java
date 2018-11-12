package com.aplusstory.fixme;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;

import org.jetbrains.annotations.Nullable;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LocationFileManager implements FileManager {
    public static final String FILENAME_CURRENT_LOCATION = "current_location";
    public static final String FILENAME_LOCATION_PREFIX = "location_";
    public static final String DATE_FORMAT_GMT_DATE = "yyyy-MM-dd";

    private SharedPreferences sp = null;
    private FileWriter fw = null;
    private FileReader fr = null;
    private DateFormat dfDate = null;

    Context context;

    public LocationFileManager(Context context){
        this.context = context;
        this.sp = context.getSharedPreferences(FILENAME_CURRENT_LOCATION, 0);
        this.dfDate = new SimpleDateFormat(DATE_FORMAT_GMT_DATE, Locale.US);
        Date now = new Date(System.currentTimeMillis());
        try {
//            this.fw = new FileWriter(context.getFilesDir() + (FILENAME_LOCATION_PREFIX + dfDate.format(now)), true);
//            this.fr = new FileReader(context.getFilesDir() + (FILENAME_LOCATION_PREFIX + dfDate.format(now)));
        } catch(Exception e){
            this.fw = null;
            this.fr = null;
            //error handle
        }
    }

    //TODO : implement
    @Nullable
    @Override
    public String getData(String key) {
        String rt = "";
        if(this.fr != null){

        }

        return rt;
    }

    public LocationDataManager.LocatonData getCurrentLocation(){
        LocationDataManager.LocatonData rt = null;
        if(this.sp != null){
            rt = new LocationDataManager.LocatonData(
                     this.sp.getString(LocationDataManager.LocatonData.KEY_DATETIME,"")
                    ,this.sp.getFloat(LocationDataManager.LocatonData.KEY_LATITUDE, 0.0F)
                    ,this.sp.getFloat(LocationDataManager.LocatonData.KEY_LONGTITUDE, 0.0F)
            );
        }

        return rt;
    }

    public boolean setCurrentLocation(LocationDataManager.LocatonData loca){
        boolean rt = false;
        DateFormat df = new SimpleDateFormat(LocationFileManager.DATE_FORMAT_GMT_DATE,Locale.US);
        if(this.sp != null){
            SharedPreferences.Editor ed = this.sp.edit();
            ed.putFloat(LocationDataManager.LocatonData.KEY_LATITUDE, loca.latitude);
            ed.putFloat(LocationDataManager.LocatonData.KEY_LONGTITUDE, loca.longtitude);
            Date dt = new Date(loca.datetime);
            String dtStr = df.format(dt);
            ed.putString(LocationDataManager.LocatonData.KEY_DATETIME, dtStr);
            rt = ed.commit();
        }
        return rt;
    }

    //TODO : implement
    @Override
    public boolean setData(String key, String value) {
        return false;
    }
}
