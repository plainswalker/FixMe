package com.aplusstory.fixme;

import android.app.admin.SystemUpdatePolicy;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

public class UserMovementRecognizer implements Recognizer, SensorEventListener{
    public static final long DELAY_ASEC = 1000;
    public static final long DELAY_FEWSEC = 3000;
    public static final long DELAY_SOMESEC = 10000;
    public static final long DELAY_AMIN = 60000;

    public static final String KEY_ACCELERATION_X = "x";
    public static final String KEY_ACCELERATION_Y = "y";
    public static final String KEY_ACCELERATION_Z = "z";
    public static final String KEY_DELTA_ACCELERATION_X = "dx";
    public static final String KEY_DELTA_ACCELERATION_Y = "dy";
    public static final String KEY_DELTA_ACCELERATION_Z = "dz";
    public static final String KEY_SPEED = "speed";
    public static final String KEY_PITCH = "p";
    public static final String KEY_ROLL = "r";
    public static final String KEY_YAW = "y";
    public static final String KEY_DELTA_PITCH = "dp";
    public static final String KEY_DELTA_ROLL = "dr";
    public static final String KEY_DELTA_YAW = "dy";

    private static final long GAP_OF_TIME = 100;
    private static final long SHAKE_THRESHOLD = 500;

    private Context context;
    private boolean cond = false;
    private boolean isEnabled = false;
    private Thread thd = null;
    private SensorManager sm = null;
    private long T = -1;
    private float[]  V = {0.0F, 0.0F, 0.0F};
    private float[] dV = {0.0F, 0.0F, 0.0F};
    private float A = 0.0F;

    private float[]  Th = {0.0F, 0.0F, 0.0F};
    private float[] dTh = {0.0F, 0.0F, 0.0F};

    public UserMovementRecognizer(Context context){
        this.initialize(context);
    }

    private void initialize(Context context){
        this.context = context;
        this.T = System.currentTimeMillis();
        if(this.thd == null || !this.thd.isAlive()){
            this.thd = new MovementRecognizingThread();
        }

        if(this.sm == null){
            if(!this.getSensorManager()){
                Log.d(this.getClass().getName(), "Failed to get sensor");
            }
        }

        this.enable();
    }
    private boolean getSensorManager(){
        this.sm = (SensorManager) this.context.getSystemService(Context.SENSOR_SERVICE);
        Sensor acc = this.sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor gyro = this.sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if(acc != null && gyro != null) {
            sm.registerListener(this, acc, SensorManager.SENSOR_DELAY_FASTEST);
            sm.registerListener(this, gyro, SensorManager.SENSOR_DELAY_FASTEST);
        } else {
            this.sm = null;
        }

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
        Bundle b = new Bundle();
        float[] V, dV, Th, dTh;
        float A;
        synchronized (this) {
            V = this.V.clone();
            dV = this.dV.clone();
            Th = this.Th.clone();
            dTh = this.dTh.clone();
            A = this.A;
        }

        b.putFloat(KEY_ACCELERATION_X, V[0]);
        b.putFloat(KEY_ACCELERATION_Y, V[1]);
        b.putFloat(KEY_ACCELERATION_Z, V[2]);
        b.putFloat(KEY_DELTA_ACCELERATION_X, dV[0]);
        b.putFloat(KEY_DELTA_ACCELERATION_Y, dV[1]);
        b.putFloat(KEY_DELTA_ACCELERATION_Z, dV[2]);
        b.putFloat(KEY_SPEED, A);

        b.putFloat(KEY_PITCH, Th[0]);
        b.putFloat(KEY_ROLL, Th[1]);
        b.putFloat(KEY_YAW, Th[2]);
        b.putFloat(KEY_DELTA_PITCH, dTh[0]);
        b.putFloat(KEY_DELTA_ROLL, dTh[1]);
        b.putFloat(KEY_DELTA_YAW, dTh[2]);

        return b;
    }

    @Override
    public Class getExtraDataType() {
        return Bundle.class;
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

    private float getDeltaV(){
        return Math.abs(this.dV[0] + this.dV[1] + this.dV[2]);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] sv = event.values;
        float x,y,z;
        long dT;
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                x = sv[0]; y = sv[1]; z = sv[2];
                Log.d(this.getClass().getName(),
                 "Accellometer value changed"
                    + " , x : " + Float.toString(x)
                    + " , y : " + Float.toString(y)
                    + " , z : " + Float.toString(z));
                dT = System.currentTimeMillis() - this.T;
                if(dT > 100) {
                    synchronized (this) {
                        this.dV[0] = x - this.V[0];
                        this.dV[1] = y - this.V[1];
                        this.dV[2] = z - this.V[2];
                        this. V[0] = x;
                        this. V[1] = y;
                        this. V[2] = z;
                        this.A = this.getDeltaV()/(float)(dT * 1000);
                    }
                }
                break;
            case Sensor.TYPE_GYROSCOPE:
                Log.d(this.getClass().getName(), "");
                synchronized (this){
                    x = sv[0]; y = sv[1]; z = sv[2];
                    Log.d(this.getClass().getName(),
                            "gyroscope value changed"
                                    + " , x : " + Float.toString(x)
                                    + " , y : " + Float.toString(y)
                                    + " , z : " + Float.toString(z));
                    dT = System.currentTimeMillis() - this.T;
                    if(dT > 100) {
                        synchronized (this) {
                            this.dTh[0] = x - this.V[0];
                            this.dTh[1] = y - this.V[1];
                            this.dTh[2] = z - this.V[2];
                            this. Th[0] = x;
                            this. Th[1] = y;
                            this. Th[2] = z;
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d(this.getClass().getName(), "Accuracy changed. accuracy : " + Integer.toString(accuracy));
    }
}
