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
    private static WinDetectService wdsInstance;
    private Handler hd = null;

    @Nullable
    public static WinDetectService getInstance(){
        return WinDetectService.wdsInstance;
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();

        AccessibilityServiceInfo config = new AccessibilityServiceInfo();
        config.eventTypes =
          AccessibilityEvent.TYPE_WINDOWS_CHANGED
        | AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
        | AccessibilityEvent.TYPE_VIEW_FOCUSED
        | AccessibilityEvent.TYPE_VIEW_CLICKED
        | AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START
        | AccessibilityEvent.TYPE_TOUCH_INTERACTION_START
        | AccessibilityEvent.TYPE_GESTURE_DETECTION_START
        ;
        config.feedbackType = AccessibilityServiceInfo.DEFAULT;
        config.notificationTimeout = 100;
        config.flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        ComponentName componentName = new ComponentName(
                event.getPackageName().toString(),
                event.getClassName().toString()
        );

        ActivityInfo actinfo;
        try{
            actinfo = getPackageManager().getActivityInfo(componentName, 0);
        } catch(PackageManager.NameNotFoundException e){
            actinfo = null;
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

        Log.d("WinDetectService", "Accessibility Event, activity name : " + ((msgtxt.length() > 0) ? msgtxt : "NONE"));

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
