package com.aplusstory.fixme;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

class EnvironmentRecognizer implements Recognizer{
    private SensorManager sm = null;
    private SensorEventListener sel = null;
    private Context context;
    private boolean cond = false;
    private boolean isEnabled;
    private Object extra;


    public EnvironmentRecognizer(Context context){
        this.initialize(context);
    }

    private void initialize(Context context){
        this.context = context;
        if(!this.getSensorManager()){

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
                                that.extra = (Object) sv[0];
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

    @Override
    public boolean checkCondition() {
        return this.cond;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    @Override
    public Object getExtraData() {
        return this.extra;
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
            }
        }
    }

    @Override
    public void disable() {
        synchronized (this){
            if(this.isEnabled){
                this.isEnabled = false;
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
