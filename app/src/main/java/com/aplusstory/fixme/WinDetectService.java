package com.aplusstory.fixme;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Message;
import android.os.Handler;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import org.jetbrains.annotations.Nullable;

public class WinDetectService extends AccessibilityService{
    private static WinDetectService wdsInstance  =null;
    private Handler hd = null;
    private int eventcnt = 0;

    @Nullable
    public static WinDetectService getInstance(){
        return WinDetectService.wdsInstance;
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.d("WinDetectService", "Service ocnnected");
        AccessibilityServiceInfo config = new AccessibilityServiceInfo();
//        config.eventTypes =
//          AccessibilityEvent.TYPE_WINDOWS_CHANGED
//        | AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
//        | AccessibilityEvent.TYPE_VIEW_FOCUSED
//        | AccessibilityEvent.TYPE_VIEW_CLICKED
//        | AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START
//        | AccessibilityEvent.TYPE_TOUCH_INTERACTION_START
//        | AccessibilityEvent.TYPE_GESTURE_DETECTION_START
//        ;
        config.eventTypes = AccessibilityEvent.TYPES_ALL_MASK
        ^ 2048
        ^ 64
        ;
        config.feedbackType = AccessibilityServiceInfo.DEFAULT;
        config.notificationTimeout = 100;
        config.flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS;
        this.setServiceInfo(config);

        WinDetectService.wdsInstance = this;

    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        String pkname = null;
        String clsname = null;
        ComponentName componentName = null;
        ActivityInfo actinfo = null;
        try{
            pkname = event.getPackageName().toString();
            clsname = event.getClassName().toString();
        } catch(NullPointerException e){
            //
        }
        if(pkname != null && clsname != null) {
             componentName = new ComponentName(
                    event.getPackageName().toString(),
                    event.getClassName().toString()
            );

            try {
                actinfo = getPackageManager().getActivityInfo(componentName, 0);
            } catch (PackageManager.NameNotFoundException e) {
//                actinfo = null;
            }
        }

        String msgtxt;
        int what;
        if(actinfo != null){
            msgtxt = componentName.flattenToString();
            what = 1;
        }
        else {
            msgtxt = "";
            what = 0;
        }

        Log.d("WinDetectService", "Accessibility Event " + Integer.toString(this.eventcnt++) +", action code : " + Integer.toString(event.getEventType())
         +      " package name : " + pkname + ", class name : " + clsname + "\nactivity name : " + ((msgtxt.length() > 0) ? msgtxt : "NONE"));

        if(this.hd != null){
            Message msg = this.hd.obtainMessage();
            msg.what = what;
            Bundle bd = new Bundle();
            bd.putString("ActivityName", msgtxt);
            msg.setData(bd);
            this.hd.sendMessage(msg);
        }
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public boolean onUnbind(Intent intent) {
        boolean rt = super.onUnbind(intent);
        WinDetectService.wdsInstance = null;
        return rt;
    }

    public void setHandler(Handler hd){
        this.hd = hd;
    }
}
