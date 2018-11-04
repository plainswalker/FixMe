package com.aplusstory.fixme;

import org.jetbrains.annotations.Nullable;

//TODO : save settings in key(String), value(Stirng)
public class SettingsFileManager implements FileManager {
    public static final String FILENAME_SETTINGS = "settings";

    @Nullable
    @Override
    public String getData(String key) {
        return null;
    }

    @Override
    public boolean setData(String key, String value) {
        return false;
    }
}
