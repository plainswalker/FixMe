package com.aplusstory.fixme;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Stream;

public class TodayFootPrintDataManager implements FootprintDataManager {
    public static final double DISTANCE_THRESHOLD = CurrentLocationManager.DISTANCE_THRESHOLD;
    public static final long INTERVAL_THRESHOLD = 5 * 60 * 1000;

    private LocationFileManager fm = null;
    private Context context;
    private Calendar today;
    private ArrayList<FootPrintData> dataArr = null;

    TodayFootPrintDataManager(Context context){
        this(context, new Date(System.currentTimeMillis()));
    }

    TodayFootPrintDataManager(Context context, Date today){
        this.context = context;
        this.fm = new LocationFileManager(context, LocationFileManager.READ_ONLY);
        this.today = Calendar.getInstance();
        this.today.setTime(today);
    }

    private void getDataForToday() throws NullPointerException{
        if(this.dataArr == null){
            this.dataArr = new ArrayList<>();
        } else{
            this.dataArr.clear();
        }

        LocationDataManager.LocationData[] arr = this.fm.getLocationList(this.today.getTime()).locaArr;
        LocationDataManager.LocationData lastLoca = null;
        ArrayList<LocationDataManager.LocationData> bufPath = null;
        FootPrintData data = null;

        long tBegin = 0, tEnd = 0;

        for(LocationDataManager.LocationData loca : arr){
            if(lastLoca == null && bufPath == null){
                lastLoca = loca;
                tBegin = loca.datetime;
                tEnd = loca.datetime;
            } else if(bufPath == null){
                if(loca.distanceTo(lastLoca) > DISTANCE_THRESHOLD){
                    tEnd = loca.datetime;
                    data = new FootPrintData(tBegin, tEnd, lastLoca);
                    this.dataArr.add(data);
                    bufPath = new ArrayList<>();
                    tBegin = tEnd;
                    bufPath.add(loca);
                    lastLoca = loca;
                }
            } else {
                if(loca.datetime - lastLoca.datetime < INTERVAL_THRESHOLD
                || loca.distanceTo(lastLoca) >= DISTANCE_THRESHOLD){
                    tEnd = loca.datetime;
                    bufPath.add(loca);
                    lastLoca = loca;
                } else{
                    LocationDataManager.PathData path = new LocationDataManager.PathData(bufPath);
                    data = new FootPrintData(path);
                    this.dataArr.add(data);
                    bufPath = null;
                    lastLoca = loca;
                    tBegin = loca.datetime;
                    tEnd = loca.datetime;
                }
            }
        }
    }
    private LocationDataManager.LocationData getLastLocation(){
        if(this.dataArr != null) {
            Serializable lastData = this.dataArr.get(this.dataArr.size() - 1).locaData;
            if(lastData instanceof LocationDataManager.LocationData){
                return (LocationDataManager.LocationData)lastData;
            } else if(lastData instanceof LocationDataManager.PathData){
                LocationDataManager.LocationData[] arr = ((LocationDataManager.PathData)lastData).locaArr;
                if(arr != null) {
                    return arr[arr.length - 1];
                }
            }
        }

        return null;
    }

    public ArrayList<FootPrintData> getData(){
        Calendar calNow = Calendar.getInstance();
        calNow.setTime(new Date(System.currentTimeMillis()));
        if((this.dataArr == null)
        || (this.today.get(Calendar.MONTH) != calNow.get(Calendar.MONTH))
        || (this.today.get(Calendar.DAY_OF_MONTH) != calNow.get(Calendar.DAY_OF_MONTH))
        || this.getLastLocation().distanceTo(this.fm.getCurrentLocation()) < DISTANCE_THRESHOLD){
            try {
                this.getDataForToday();
            }catch(Exception e){
                Log.d(this.getClass().getName(), e.toString());
            }
        }
        return new ArrayList<FootPrintData>(this.dataArr);
    }


    @Override
    public void setFileManager(FileManager f) {
        if(f instanceof LocationFileManager){
            this.fm = (LocationFileManager)f;
        }
    }


}
