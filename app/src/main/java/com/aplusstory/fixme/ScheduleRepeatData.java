package com.aplusstory.fixme;


import android.os.Parcel;
import android.os.Parcelable;

public class ScheduleRepeatData implements Parcelable {

    public String repeatDate;
    public int repeatState;

    public ScheduleRepeatData(String repeatDate, int repeatState) {
        this.repeatDate = repeatDate;
        this.repeatState = repeatState;
    }


    protected ScheduleRepeatData(Parcel in) {
        repeatDate = in.readString();
        repeatState = in.readInt();
    }

    public static final Creator<ScheduleRepeatData> CREATOR = new Creator<ScheduleRepeatData>() {
        @Override
        public ScheduleRepeatData createFromParcel(Parcel in) {
            return new ScheduleRepeatData(in);
        }

        @Override
        public ScheduleRepeatData[] newArray(int size) {
            return new ScheduleRepeatData[size];
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
}
