package com.aplusstory.fixme;

import android.os.Handler;

public class MyServiceThread extends Thread {
    Handler hd;
    boolean isRun = true;
    private int delay = 3000000;

    public MyServiceThread(Handler hd){
        this.hd = hd;
    }
    public void stopService(){
        synchronized (this){
            this.isRun = false;
        }
    }


    public void run(){
        while (isRun){
            this.hd.sendEmptyMessage(0);

            try{
                Thread.sleep(this.delay);
            }catch (Exception e){
                //msg
            }
        }
    }
}
