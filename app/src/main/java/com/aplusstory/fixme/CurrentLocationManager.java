package com.aplusstory.fixme;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CurrentLocationManager extends Service implements LocationDataManager, LocationListener {
//    public static final long MIN_LOCA_UPDATE = 5 * 60 * 1000;
    public static final long MIN_LOCA_UPDATE = 10000;
    public static final long DELAY_THREAD_LOOP = 10000;

    private LocationFileManager fm = null;
    private Recognizer moRecog = null;
    private LocationManager lm = null;
    private Thread thd = null;
    private boolean isEnabled = false;
    private Handler hd = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.isEnabled = true;

        if(this.fm == null) {
            this.fm = new LocationFileManager(this);
        }
        if(this.lm == null) {
            this.lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        }
        if(this.moRecog == null){
            this.moRecog = new UserMovementRecognizer(this);
            this.moRecog.enable();

        }
        if(this.hd == null){
            this.hd = new ServiceHandler();
        }
        if(this.thd == null || !this.thd.isAlive()){
            this.thd = new ServiceThread(this.hd);
            this.thd.start();
        }

        Log.d(CurrentLocationManager.class.getName(), "Service started");
        Toast.makeText(this, "Service started", Toast.LENGTH_SHORT);

        return Service.START_NOT_STICKY;
    }

    @Override
    public void onLocationChanged(Location location) {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        DateFormat df = new SimpleDateFormat(LocatonData.DATE_FORMAT_GMT, Locale.US);
        String dStr = df.format(date);
        double latitude =  location.getLatitude();
        double longtitude = location.getLongitude();

        Log.d(CurrentLocationManager.class.getName(),
             "You're now on : "
                + Double.toString(latitude)
                + ", " + Double.toString(longtitude)
                + ", at : "
                + dStr
        );

        LocatonData loca = new LocatonData(now, latitude, longtitude);
        if(this.fm != null){
            this.fm.setCurrentLocation(loca);
        }
        this.lm.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(CurrentLocationManager.class.getName(), "Status changed, provider : " + provider + "status : " + Integer.toString(status));
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(CurrentLocationManager.class.getName(), provider + " enabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d(CurrentLocationManager.class.getName(), provider + " disabled");
    }

    public class ServiceHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            CurrentLocationManager that = CurrentLocationManager.this;
            if(that.lm != null){
                LocationProvider gps = that.lm.getProvider(LocationManager.GPS_PROVIDER);
                LocationProvider net = that.lm.getProvider(LocationManager.NETWORK_PROVIDER);
                LocationProvider passive = that.lm.getProvider(LocationManager.PASSIVE_PROVIDER);
                boolean coasePermission = that.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED;
                boolean finePermission = that.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED;
                if(passive != null && (finePermission)){
                    that.lm.requestLocationUpdates(
                            LocationManager.PASSIVE_PROVIDER
                            , CurrentLocationManager.MIN_LOCA_UPDATE
                            , 5
                            , that
                    );
                } else {
                    if (gps != null && finePermission) {
                        that.lm.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER
                                , CurrentLocationManager.MIN_LOCA_UPDATE
                                , 5
                                , that
                        );
                    }
                    if (net != null && coasePermission) {
                        that.lm.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER
                                , CurrentLocationManager.MIN_LOCA_UPDATE
                                , 5
                                , that
                        );
                    }
                }
            }
        }
    }

    public class ServiceThread extends Thread{
        Handler hd = null;

        public ServiceThread(Handler hd){
            this.hd = hd;
        }

        @Override
        public void run() {
            CurrentLocationManager that = CurrentLocationManager.this;
            while(that.isEnabled){
                if(that.moRecog == null || !that.moRecog.isEnabled()){
                    if(this.hd != null) {
                        this.hd.sendEmptyMessage(0);
                    }
                } else if(that.moRecog.checkCondition()){
                    if(this.hd != null){
                        this.hd.sendEmptyMessage(0);
                    }
                }
                try{
                    Thread.sleep(CurrentLocationManager.DELAY_THREAD_LOOP);
                } catch (InterruptedException e){
                    return;
                }
            }
        }
    }

    @Override
    public void setFileManager(FileManager fm) {
        if(fm instanceof LocationFileManager){
            this.fm = (LocationFileManager) fm;
        }
    }

    @Override
    public void onDestroy() {
        if(this.fm != null){
            this.fm.destroy();
        }

        if(this.lm != null){
            this.lm.removeUpdates(this);
        }

        if(this.moRecog != null){
            this.moRecog.destroy();
        }

        if(this.thd != null && this.thd.isAlive()){
            this.thd.interrupt();
        }

        super.onDestroy();
    }
}
