package com.aplusstory.fixme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotificationActionReceiver extends BroadcastReceiver {
    public final static String EXTRA_NAME = "notification_action";
    public final static int EXTRA_ENABLE = 0;
    public final static int EXTRA_DISABLE = 1;
    public final static int EXTRA_SETTINGS = 2;
    public final static int EXTRA_CANCEL = 3;

    Context context;
    Intent intent;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
        switch(intent.getIntExtra(NotificationActionReceiver.EXTRA_NAME,-1)){
            case 0:
                this.enableAction();
                break;
            case 1:
                this.disableAction();
                break;
            case 2:
                this.settingsAction();
                break;
            case 3:
                this.cancelAction();
                break;
            default:
                //something wrong
        }
    }

    private void enableAction(){
        Toast.makeText(this.context,"enable", Toast.LENGTH_SHORT).show();
    }
    private void disableAction(){
        Toast.makeText(this.context,"disable", Toast.LENGTH_SHORT).show();
    }
    private void settingsAction(){
        Toast.makeText(this.context,"setting", Toast.LENGTH_SHORT).show();
    }
    private void cancelAction(){
        Toast.makeText(this.context,"quit", Toast.LENGTH_SHORT).show();
    }
}