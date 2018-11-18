package com.aplusstory.fixme;


import android.os.Parcel;
import android.os.Parcelable;

public class ScheduleData implements Parcelable {

    public String repeatDate;
    public int repeatState;

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
}
