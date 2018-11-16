package com.aplusstory.fixme;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class UserMovementRecognizer implements Recognizer, SensorEventListener{
    public static final long DELAY_ASEC = 1000;
    public static final long DELAY_FEWSEC = 3000;
    public static final long DELAY_SOMESEC = 10000;
    public static final long DELAY_AMIN = 60000;
    public static final long DELAY_FEWMIN = 180000;


    private boolean cond = false;
    private boolean isEnabled = false;
    private Thread thd = null;
    private SensorManager sm = null;

    public UserMovementRecognizer(Context context){

    }

    private void initialize(Context context){
        if(this.thd == null || !this.thd.isAlive()){
            this.thd = new MovementRecognizingThread();
        }

        if(this.sm == null){
            this.getSensorManager();
        }

        this.enable();
    }
    private boolean getSensorManager(){
        //get sensor manager
        return this.sm != null;
    }

    private class MovementRecognizingThread extends Thread{
        @Override
        public void run() {
            UserMovementRecognizer that = UserMovementRecognizer.this;
            while(that.isEnabled){
                //loop
                if(that.sm != null){

                }
                try{
                    Thread.sleep(UserMovementRecognizer.DELAY_FEWSEC);
                } catch (InterruptedException e){
                    return;
                }
            }
        }
    }

    @Override
    public boolean checkCondition() {
        synchronized (this) {
            return cond;
        }
    }

    @Override
    public boolean isEnabled() {
        synchronized (this) {
            return false;
        }
    }

    @Override
    public Object getExtraData() {
        return null;
    }

    @Override
    public Class getExtraDataType() {
        return null;
    }

    @Override
    public void enable() {
        if(!this.isEnabled) {
            synchronized (this) {
                this.isEnabled = true;
                if(this.thd == null || !this.thd.isAlive()){
                    this.thd = new MovementRecognizingThread();
                    this.thd.start();
                }
            }
        }
    }

    @Override
    public void disable() {
        if(this.isEnabled) {
            synchronized (this) {
                this.isEnabled = false;
            }
        }
    }

    @Override
    public void destroy() {
        this.thd.interrupt();
        this.disable();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] sv = event.values;
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                Log.d(this.getClass().getName(), "");
                synchronized (this){

                }
                break;
            case Sensor.TYPE_GYROSCOPE:
                Log.d(this.getClass().getName(), "");
                synchronized (this){

                }
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d(this.getClass().getName(), "");
    }
}
