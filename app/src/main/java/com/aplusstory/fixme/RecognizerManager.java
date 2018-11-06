package com.aplusstory.fixme;

import android.content.Context;
import android.util.Log;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RecognizerManager implements NotificationDataManager {
    private List<Recognizer> recognizers = null;
    private long dAdvDelay = 0;
    private long tElpsedBegin;
    private long tCondBegin;
    private boolean isEnabled = false;
    private int condCode = 0;
    private Context context;
    private NotificationUserSettingManager sm = null;

    public RecognizerManager(Context context){
        this.context = context;
        this.tElpsedBegin = System.currentTimeMillis();
        this.tCondBegin = -1;

        this.sm = new NotificationUserSettingManager(this.context);
//        this.sm.setFileManager(new SettingsFileManager());
        this.dAdvDelay = this.sm.getAdvisePeroiod();
    }

    @Override
    public void addRecognizer(Recognizer rcg) {
        if(this.recognizers == null) {
            this.recognizers = new ArrayList<>();
        }
        this.recognizers.add(rcg);

        for(Recognizer r : this.recognizers){
            Log.d("RcgMan", "recognizer : " + r.getClass().toString());
            if(r.getClass() == EnvironmentRecognizer.class){
                ((EnvironmentRecognizer)r).setThreshold(this.sm.getEnvironmentCondition());
            }
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
                for (Recognizer r : this.recognizers) {
                    r.enable();
                }
            }
        }
    }

    @Override
    public void disable() {
        synchronized (this) {
            if (this.isEnabled) {
                this.isEnabled = false;
                this.tCondBegin = -1;
                this.condCode = 0;
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
            synchronized (r) {
                if (r.checkCondition()) {
                    if (r.getClass() == UserRecognizer.class) {
                        if (this.tCondBegin > 0) {
                            boolean usageCond = (now - this.tCondBegin) < this.dAdvDelay;
                            if (usageCond) {
                                Log.d("RcgMan", "Usage Timer : " + Long.toString((now - this.tCondBegin) / 1000) + "sec");

                            } else {
                                Log.d("RcgMan", "Usage Timer end");
                                this.condCode |= NotificationDataManager.COND_TIME_OVERUSE;
                                this.tCondBegin = -1;
                            }
                            rt = rt || !usageCond;
                        } else {
                            this.tCondBegin = now;
                            this.condCode &= ~NotificationDataManager.COND_TIME_OVERUSE;
                            Log.d("RcgMan", "Usage Timer begin");
                        }
                    } else if (r.getClass() == EnvironmentRecognizer.class) {
                        Log.d("RcgMan", "dark outside");
                        rt = true;
                        this.condCode |= NotificationDataManager.COND_ENVIRON_LIGHT;
                    }
                } else if (r.getClass() == UserRecognizer.class) {
                    Log.d("RcgMan", "Not Using");
                    this.tElpsedBegin = -1;
                    this.condCode = 0;
                    return false;
                } else if(r.getClass() == EnvironmentRecognizer.class){
                    this.condCode &= ~NotificationDataManager.COND_ENVIRON_LIGHT;
                }
            }
        }

        Log.d("RcgMan", "checking time : " + Long.toString(System.currentTimeMillis() - now));
        return rt;
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

    @Nullable
    @Override
    public String getUISetting(String key) {
        if(key.equals(NotificationUIManager.VIBRATE_SETTING_KEY)){
            return Boolean.toString(this.sm.getViberation());
        } else if(key.equals(NotificationUIManager.TRANSPARENCY_SETTING_KEY)){
            return Integer.toString(this.sm.getTransparency());
        } else{
            return null;
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
