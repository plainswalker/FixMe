package com.aplusstory.fixme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;

import java.util.Timer;

public class UserRecognizer implements Recognizer{
    public static final long DELAY_FEWSEC = 3000;
    public static final long DELAY_SOMESEC = 10000;
    public static final long DELAY_FEWMIN = 180000;
    public static final long DELAY_SOMEMIN = 600000;
    public static final long DELAY_HALFHOUR = 1800000;
    public static final long DELAY_ANHOUR = 3600000;

    private Context context;
    private Handler hd = null;
    private Thread thd = null;
    private IntentFilter intfl = null;
    private BroadcastReceiver br = null;
    private boolean isEnabled = true;

    private long tScreenOn;
    private long dScreenOn;

    private long tUserRecog;
    private long dUserRecog;

    public UserRecognizer(Context context){
        this.initialize(context);
    }

    private void initialize(Context context){
        this.context = context;
        this.tScreenOn = -1;
        this.tUserRecog = -1;
        this.dScreenOn = UserRecognizer.DELAY_FEWSEC;
        this.dUserRecog = UserRecognizer.DELAY_SOMESEC;

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
            this.context.registerReceiver(this.br,this.intfl);
        }

        if(this.thd == null || !this.thd.isAlive()){
            this.thd = new UserRecognizingThread();
            ((UserRecognizingThread)this.thd).setHandler(this.hd);
            this.thd.start();
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

                long dS = dScreenOn;
                long dU = dUserRecog;
                long delay = (dS > dU) ? dS : dU;
                try {
                    Thread.sleep(delay);
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
            if(msg.what != 0){
                synchronized (that){
                    that.tUserRecog = System.currentTimeMillis();
                }
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
                    that.tScreenOn = -1;
                }
            }
        }
    }

    @Override
    public boolean checkCondition() {
        return false;
    }

    @Override
    public synchronized boolean isEnabled() {
        return this.isEnabled;
    }

    @Override
    public synchronized void enable() {
        if(!this.isEnabled){
            this.isEnabled = true;
        }
        if(this.thd == null || !this.thd.isAlive() ){
            this.thd = new UserRecognizingThread();
            this.thd.start();
        }
    }

    @Override
    public void disable() {
        if(this.isEnabled){
            this.isEnabled = false;
        }
    }

    public synchronized void setDelayScreenOn(int delay){
        this.dScreenOn = delay;
    }

    public synchronized void setDelayUserRecog(int delay){
        this.dUserRecog = delay;
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
