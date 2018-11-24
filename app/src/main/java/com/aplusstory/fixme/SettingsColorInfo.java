package com.aplusstory.fixme;

public class SettingsColorInfo {

    public int colorId;
    public int colorCode;
    public String colorName;

    public SettingsColorInfo(int colorIconId, int colorCode) {
        this.colorId = colorIconId;
        this.colorCode = colorCode;
        this.colorName = ScheduleDataManager.TableColor.getColorText(colorCode);
    }
}
