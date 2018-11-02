package com.aplusstory.fixme;


import java.util.Date;

public class ScheduleData {

    public static class RepeatDuration{
        public static final int REPEAT_DAYLY = 0;
        public static final int REPEAT_WEEKLY = 1;
        public static final int REPEAT_MONTHLY = 2;
        public static final int REPEAT_YEARLY = 3;
    }
    public static class TableColor{
        public static final int WHITE = 0;
        public static final int LIGHTGREEN = 1;
        public static final int SKYBLUE = 2;
        public static final int PINK = 3;
        public static final int YELLOW = 4;
        public static final int PURPLE = 5;
        public static final int RED = 6;
        public static final int CYAN = 7;
        public static final int BLUE = 8;
        public static final int GREEN = 9;
        public static final int BLACK = 10;
    }

    boolean isRepeated;
    int repeatDuration;
    long scheduleBegin;
    long scheduleEnd;
    Double latitude;
    Double longitude;
    String locationAddress;
    String memo;
    long alertTime;
    int tableColor;
    //TODO : getters, setters
    public void setBegin(Date date){
        this.scheduleBegin = date.getTime();
    }
    public void setEnd(Date date){

    }
}
