package com.aplusstory.fixme;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import org.jetbrains.annotations.Nullable;

public class WinDetectService extends AccessibilityService {
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
        config.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        config.feedbackType = AccessibilityServiceInfo.DEFAULT;
        config.notificationTimeout = 100;
        config.flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS;

        setServiceInfo(config);

        WinDetectService.wdsInstance = this;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            if (event.getPackageName() != null && event.getClassName() != null) {
                ComponentName componentName = new ComponentName(
                        event.getPackageName().toString(),
                        event.getClassName().toString()
                );

                ActivityInfo activityInfo = tryGetActivity(componentName);
                boolean isActivity = activityInfo != null;
                if (isActivity) {
                    Log.i("CurrentActivity", componentName.flattenToShortString());
                    if(this.hd != null) {
                        Message msg = this.hd.obtainMessage();
                        msg.what = 1;
                        msg.obj = (Object) new String(componentName.flattenToString());
                        this.hd.sendMessage(msg);
                    }
                }
            }
        }
    }

    private ActivityInfo tryGetActivity(ComponentName componentName) {
        try {
            return getPackageManager().getActivityInfo(componentName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    @Override
    public void onInterrupt() {}

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
