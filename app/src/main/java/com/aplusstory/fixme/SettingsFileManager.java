package com.aplusstory.fixme;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.jetbrains.annotations.Nullable;

public class SettingsFileManager implements FileManager {
    public static final String FILENAME_SETTINGS = "settings";

    private Context context;
    private SharedPreferences sp = null;

    public SettingsFileManager(Context context){
        this.context = context;
        this.sp = this.context.getSharedPreferences(FILENAME_SETTINGS, 0);
    }

    @Nullable
    @Override
    public String getData(String key) {
        String value = null;
        if(this.sp != null && this.sp.contains(key)){
            value = this.sp.getString(key, null);
        }

        return value;
    }

    @Override
    public boolean setData(String key, String value) {
        boolean rt = false;
        if(this.sp != null){
            Log.d(this.getClass().getName(), "setting data, key : " + key +  ", value : " + value);
            rt = this.sp.edit().putString(key, value).commit();
        }
        return rt;
    }
}
