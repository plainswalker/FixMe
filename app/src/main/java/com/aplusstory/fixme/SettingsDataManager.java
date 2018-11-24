package com.aplusstory.fixme;

public interface SettingsDataManager extends UserDataManager{
    public static final String SETTINGS_FILE_NAME = SettingsFileManager.FILENAME_SETTINGS;

    public static final String PERIOD_SETTING_KEY = "advise_period";
    public static final String THRESHOLD_SETTING_KEY = "illuminance_threshold";
    public static final String VIBRATE_SETTING_KEY = "vibration";
    public static final String TRANSPARENCY_SETTING_KEY = "transparency";

    public static final String DURATION_SETTING_KEY = "data_duration";

    @Override
    void setFileManager(FileManager f);
}
