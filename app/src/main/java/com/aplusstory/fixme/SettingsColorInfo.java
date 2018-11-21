package com.aplusstory.fixme;

public class SettingsColorInfo {

    public int colorIconId;
    public int colorCode;
    public String colorName;

    public SettingsColorInfo(int colorIconId, int colorCode) {
        this.colorIconId = colorIconId;
        this.colorCode = colorCode;
        this.colorName = ScheduleDataManager.TableColor.getColorText(colorCode);
    }
}
