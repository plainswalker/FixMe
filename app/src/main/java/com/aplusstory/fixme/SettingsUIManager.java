package com.aplusstory.fixme;

public interface SettingsUIManager extends ApplicationUIManager{
    //final String[] item = {"선택하세요", "5분마다", "10분마다", "30분마다", "1시간마다", "3시간마다"};
    //final String[] item = {"선택하세요", "없음", "1주일", "1개월", "6개월", "1년"};

    int LIGHT_SENSITIVITY_LOW = 0;
    int LIGHT_SENSETIVITY_MIDDLE = 1;
    int LIGHT_SENSETIVITY_HIGH = 2;

    int ALERT_PEROID_5MIN = 1;
    int ALERT_PEROID_10MIN = 2;
    int ALERT_PEROID_30MIN = 3;
    int ALERT_PEROID_1HOUR = 4;
    int ALERT_PEROID_3HOUR = 5;

    int FOOTDATA_DURATION_NONE = 1;
    int FOOTDATA_DURATION_AWEEK = 2;
    int FOOTDATA_DURATION_AMONTH = 3;
    int FOOTDATA_DURATION_HALFYEAR = 4;
    int FOOTDATA_DURATION_AYEAR = 5;

    float LIGHT_SENSETIVITY_VALUE_HIGH = 150.0f;
    float LIGHT_SENSETIVITY_VALUE_MIDDLE = 100.0f;
    float LIGHT_SENSETIVITY_VALUE_LOW = 50.0f;

    long ALERT_PEROID_VALUE_5MIN = 5 * 60 * 1000;
    long ALERT_PEROID_VALUE_10MIN = 10 * 60 * 1000;
    long ALERT_PEROID_VALUE_30MIN = 30 * 60 * 1000;
    long ALERT_PEROID_VALUE_1HOUR = 60 * 60 * 1000;
    long ALERT_PEROID_VALUE_3HOUR = 3 * 60 * 60 * 1000;

    @Override
    void setDataManager(UserDataManager m);
    void setDataManager(SettingsDataManager m);
}