package com.aplusstory.fixme;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

class EnvironmentRecognizer implements Recognizer{
    public static final long DELAY_ASEC = 1000;
    public static final long DELAY_FEWSEC = 3000;
    public static final long DELAY_SOMESEC = 10000;
    public static final long DELAY_AMIN = 60000;
    public static final long DELAY_FEWMIN = 180000;

    public static final float LIGHT_DARK = 100.0f; //threshold of illumination; should be obtained by experiment

    private SensorManager sm = null;
    private SensorEventListener sel = null;
    private Context context;
    private boolean cond = false;
    private boolean isEnabled = false;
    private float sensorValue = 0.0f;
    private Thread thd = null;
    private long updl = EnvironmentRecognizer.DELAY_ASEC;
    private long tInDark = -1;
    private long dInDark;
    private float threshold = EnvironmentRecognizer.LIGHT_DARK;

    public EnvironmentRecognizer(Context context){
        this.initialize(context);
    }

    private void initialize(Context context){
        this.context = context;
        this.dInDark = EnvironmentRecognizer.DELAY_FEWSEC;
        if(!this.getSensorManager()){
            Log.d("EnvRcg", "getting sensor value failed.");
            //error handle
        }
        if(this.thd == null || !this.thd.isAlive()){
            this.thd = new EnvironmentRecognizingThread();
//            thd.start();
        }
    }

    private boolean getSensorManager(){
        if(this.sm == null){
            Sensor ls = null;
            this.sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            if (this.sel == null) {
                this.sel = new SensorEventListener() {
                    EnvironmentRecognizer that = EnvironmentRecognizer.this;

                    @Override
                    public void onSensorChanged(SensorEvent event) {
                        float[] sv = event.values;
                        switch (event.sensor.getType()) {
                            case Sensor.TYPE_LIGHT:
                                Log.d("EnvRcg", "light sensor value changed. value : " + Float.toString(sv[0]));
                                synchronized (that){
                                    that.sensorValue = sv[0];
                                }
                                break;
                        }
                    }

                    @Override
                    public void onAccuracyChanged(Sensor sensor, int accuracy) {
                        Log.d("EnvRcg", "light sensor accuracy changed. accuracy : " + Integer.toString(accuracy));
                    }
                };
            }
            try {
                ls = this.sm.getDefaultSensor(Sensor.TYPE_LIGHT);
                this.sm.registerListener(sel, ls, SensorManager.SENSOR_DELAY_FASTEST);
            } catch(NullPointerException e){
                this.sm = null;
            }
        }
        return this.sm != null;
    }

    public class EnvironmentRecognizingThread extends Thread{
        @Override
        public void run() {
            EnvironmentRecognizer that = EnvironmentRecognizer.this;
            while(that.isEnabled()){

                try {
                    Thread.sleep(that.updl);
                } catch(InterruptedException e){
                    return;
                }

                long now = System.currentTimeMillis();
                if(that.sm != null){
                    synchronized (that) {
                        if(that.sensorValue < that.threshold) {
                            Log.d("EnvRcg", "dark here, sensor value = " + Float.toString(that.sensorValue));
                            if (that.tInDark > 0){
                               if((now - that.tInDark) >= that.dInDark){
                                    that.cond = true;
                                }
                            } else {
                                Log.d("EnvRcg", "entering dark place");
                                that.tInDark = now;
                            }
                        }
                        else {
                            that.cond = false;
                            that.tInDark = -1;
                        }
                    }

                } else{
                    that.getSensorManager();
                }
            }
        }
    }

    public void setThreshold(float threshold){
        this.threshold = threshold;
    }

    @Override
    public boolean checkCondition() {
        synchronized (this) {
            Log.d("EnvRcg", "Check condition : " + Boolean.toString(this.cond));
            return this.cond;
        }
    }

    @Override
    public boolean isEnabled() {
        synchronized (this) {
            return this.isEnabled;
        }
    }

    @Override
    public Object getExtraData() {
        synchronized (this) {
            return (Object)this.sensorValue;
        }
    }

    @Override
    public Class getExtraDataType() {
        return float.class;
    }

    @Override
    public  void enable() {
        synchronized (this){
            if(!this.isEnabled){
                this.isEnabled = true;
                if(this.thd == null || !this.thd.isAlive()){
                    this.thd = new EnvironmentRecognizingThread();
                    this.thd.start();
                }
            }
        }
    }

    @Override
    public void disable() {
        synchronized (this){
            if(this.isEnabled){
                this.isEnabled = false;
                this.tInDark = -1;
            }
        }
    }

    @Override
    public void destroy() {
        if(this.sm != null && this.sel != null){
            this.sm.unregisterListener(this.sel);
        }
    }
}
