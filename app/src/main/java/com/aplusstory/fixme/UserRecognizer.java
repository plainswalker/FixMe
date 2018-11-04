package com.aplusstory.fixme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jetbrains.annotations.Nullable;

public class UserRecognizer implements Recognizer{
    public static final long DELAY_FEWSEC = 3000;
    public static final long DELAY_SOMESEC = 10000;
    public static final long DELAY_FEWMIN = 180000;
    public static final long DELAY_SOMEMIN = 600000;
    public static final long DELAY_HALFHOUR = 1800000;
    public static final long DELAY_ANHOUR = 3600000;

    private long updl = UserRecognizer.DELAY_FEWSEC;

    private Context context;
    private Handler hd = null;
    private Thread thd = null;
    private IntentFilter intfl = null;
    private BroadcastReceiver br = null;
    private WinDetectService wdsInstance = null;
    private boolean isEnabled = true;
    private Object extra = null;

    private boolean cond;

    private long tScreenOn;
    private long dScreenOn;

    private long tUserRecog;
    private long dUserRecog;

    public UserRecognizer(Context context){
        this.initialize(context);
    }

    private void initialize(Context context){
        Log.d("UsrRcg", "initialize");
        long now = System.currentTimeMillis();
        this.context = context;
        this.tScreenOn = now;
        this.tUserRecog = now;
        this.dScreenOn = UserRecognizer.DELAY_FEWSEC;
        this.dUserRecog = UserRecognizer.DELAY_ANHOUR;

        if(this.hd == null) {
            this.hd = new UserRecognizingHandler();
        }

        if(this.intfl == null){
            this.intfl = new IntentFilter();
            this.intfl.addAction(Intent.ACTION_SCREEN_ON);
            this.intfl.addAction(Intent.ACTION_SCREEN_OFF);
        }

        if(this.br == null){
            this.br = new ScreenEventReceiver();
        }

        this.context.registerReceiver(this.br,this.intfl);

        if(this.thd == null || !this.thd.isAlive()){
            this.thd = new UserRecognizingThread();
            ((UserRecognizingThread)this.thd).setHandler(this.hd);
            this.thd.start();
        }
        WinDetectService wds = WinDetectService.getInstance();
        if(wds != null) {
            this.wdsInstance = wds;
            this.wdsInstance.setHandler(hd);
        }
    }

    public class UserRecognizingThread extends Thread{
        private Handler hd;

        public UserRecognizingThread(){
            super();
        }

        public void setHandler(Handler hd) {
            this.hd = hd;
        }

        @Override
        public void run() {
            UserRecognizer that = UserRecognizer.this;
            while(that.isEnabled()){
                long now = System.currentTimeMillis();
                if(that.wdsInstance != null) {
                    synchronized (that) {
                        if (that.tUserRecog > 0 && that.tScreenOn > 0 && that.dUserRecog > now - that.tUserRecog) {
                            that.cond = true;
                        } else {
                            that.cond = false;
                        }
                    }
                }
                else{
                    that.wdsInstance = WinDetectService.getInstance();
                    if(that.wdsInstance != null){
                        that.wdsInstance.setHandler(hd);
                    }
                }
                try {
                    Thread.sleep(that.updl);
                } catch(InterruptedException e){
                    return;
                }
            }
        }
    }

    public class UserRecognizingHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            UserRecognizer that = UserRecognizer.this;
            int what = msg.what;
            if(what != 0){
                String actname = msg.getData().getString("ActivityName");
                Log.d("UsrRcg", "Handler Message Received, data : "  + actname);
                that.extra = (Object)actname;
            }
            synchronized (that){
                that.tUserRecog = System.currentTimeMillis();
            }
        }
    }

    public class ScreenEventReceiver extends BroadcastReceiver{
        public ScreenEventReceiver(){
            super();
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            UserRecognizer that = UserRecognizer.this;
            String action = intent.getAction();
            if(action.equals(Intent.ACTION_SCREEN_ON)){
                synchronized (that){
                    that.tScreenOn = System.currentTimeMillis();
                }
            } else if(action.equals(Intent.ACTION_SCREEN_OFF)){
                synchronized (that){
                    long now = System.currentTimeMillis();
                    if(that.tScreenOn + that.dScreenOn < now) {
                        that.tScreenOn = -1;
                    }
                }
            }
        }
    }

    @Override
    public boolean checkCondition() {

        synchronized (this) {
            if(this.cond){
                Log.d("UsrRcg", "Check condition");
            }
            return this.cond;
        }
    }

    @Override
    public boolean isEnabled() {
        synchronized (this) {
            return this.isEnabled;
        }
    }

    @Nullable
    @Override
    public Object getExtraData() {
        return this.extra;
    }

    @Override
    public Class getExtraDataType() {
        return String.class;
    }

    @Override
    public void enable() {
        synchronized (this) {
            if (!this.isEnabled) {
                this.isEnabled = true;
            }
            if (this.thd == null || !this.thd.isAlive()) {
                this.thd = new UserRecognizingThread();
                this.thd.start();
            }
        }
    }

    @Override
    public void disable() {
        synchronized (this) {
            if (this.isEnabled) {
                this.isEnabled = false;
            }
        }
    }

    public void setDelayScreenOn(int delay){
        synchronized (this) {
            this.dScreenOn = delay;
        }
    }

    public void setDelayUserRecog(int delay){
        synchronized (this) {
            this.dUserRecog = delay;
        }
    }

    public void destroy(){
        if(this.thd != null && this.thd.isAlive()){
            this.thd.interrupt();
        }
        if(this.br != null) {
            this.context.unregisterReceiver(this.br);
        }
    }
}
