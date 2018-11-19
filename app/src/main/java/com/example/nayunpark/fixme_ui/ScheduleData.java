package com.example.nayunpark.fixme_ui;


import android.os.Parcel;
import android.os.Parcelable;

public class ScheduleData implements Parcelable {

    private String repeatDate;
    private int repeatState;

    public ScheduleData(String repeatDate, int repeatState) {
        this.repeatDate = repeatDate;
        this.repeatState = repeatState;
    }


    protected ScheduleData(Parcel in) {
        repeatDate = in.readString();
        repeatState = in.readInt();
    }

    public static final Creator<ScheduleData> CREATOR = new Creator<ScheduleData>() {
        @Override
        public ScheduleData createFromParcel(Parcel in) {
            return new ScheduleData(in);
        }

        @Override
        public ScheduleData[] newArray(int size) {
            return new ScheduleData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(repeatDate);
        dest.writeInt(repeatState);
    }

    public String getRepeatDate() {
        return repeatDate;
    }

    public void setRepeatDate(String repeatDate) {
        this.repeatDate = repeatDate;
    }

    public int getRepeatState() {
        return repeatState;
    }

    public void setRepeatState(int repeatState) {
        this.repeatState = repeatState;
    }
}
