package com.aplusstory.fixme;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class RecognizerManager implements NotificationDataManager {
    private List<Recognizer> recognizers = null;
    private long delay = 0;
    private long tElpsedBegin;
    private long tCondBegin;
    private boolean isEnabled = true;
    private int condCode = 0;
    private Context context;



    public RecognizerManager(Context context){
        this.context = context;
        this.tElpsedBegin = System.currentTimeMillis();
        this.tCondBegin = -1;
//        this.delay = 30 * 60 * 1000; //TODO : load settings
        this.delay = 10000;
    }

    @Override
    public void addRecognizer(Recognizer rcg) {
        if(this.recognizers == null) {
            this.recognizers = new ArrayList<>();
        }
        this.recognizers.add(rcg);

        for(Recognizer r : this.recognizers){
            Log.d("RcgMan", "recognizer : " + r.getClass().toString());
        }
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    @Override
    public void enable() {
        synchronized (this) {
            if (!this.isEnabled) {
                this.isEnabled = true;
            }
            for (Recognizer r : this.recognizers) {
                r.enable();
            }
        }
    }

    @Override
    public void disable() {
        synchronized (this) {
            if (this.isEnabled) {
                this.isEnabled = false;
            }
            for (Recognizer r : this.recognizers) {
                r.disable();
            }
        }
    }

    @Override
    public boolean checkCondition() {
        long now = System.currentTimeMillis();
        boolean rt = false;
        for(Recognizer r : this.recognizers){
            if(r.checkCondition()){
                if(r.getClass() == UserRecognizer.class) {
                    if(this.tCondBegin > 0) {
                        boolean usageCond = (now - this.tCondBegin) < this.delay;
                        if(usageCond){
                            Log.d("RcgMan", "Usage Timer : " + Long.toString((now - this.tCondBegin) / 1000) + "sec");

                        } else {
                            Log.d("RcgMan", "Usage Timer end");
                            this.condCode |= NotificationDataManager.COND_TIME_OVERUSE;
                            this.tCondBegin = -1;
                        }
                        rt = rt || !usageCond;
                    } else {
                        this.tCondBegin = now;
                        this.condCode ^= NotificationDataManager.COND_TIME_OVERUSE;
                        Log.d("RcgMan", "Usage Timer begin");
                    }
                } else if(r.getClass() == EnvironmentRecognizer.class) {
                    Log.d("RcgMan", "dark outside");
                    rt = true;
                    this.condCode |= NotificationDataManager.COND_ENVIRON_LIGHT;
                }
            } else if(r.getClass() == UserRecognizer.class){
                Log.d("RcgMan","Not Using");
                this.tElpsedBegin = -1;
                this.condCode = 0;
                return false;
            }
        }
        return rt;
    }

    @Override
    public long getDelay() {
        synchronized (this) {
            return this.delay;
        }
    }

    @Override
    public long getElapsedTime() {
        long now = System.currentTimeMillis();
        synchronized (this) {
            if(this.tElpsedBegin > 0) {
                return now - this.tElpsedBegin;
            } else {
                this.tElpsedBegin = now;
                return 0;
            }
        }
    }

    @Override
    public int getConditionCode() {
        return this.condCode;
    }

    @Override
    public void destroy() {
        for(Recognizer r : this.recognizers){
            r.destroy();
        }
    }
}
