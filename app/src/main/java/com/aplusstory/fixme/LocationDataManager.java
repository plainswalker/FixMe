package com.aplusstory.fixme;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;


public interface LocationDataManager {
    void setFileManager(FileManager fm);

    public class LocatonData implements Serializable{
        public static final String KEY_DATETIME = "datetime";
        public static final String KEY_LATITUDE = "latitude";
        public static final String KEY_LONGTITUDE = "longtitude";
        public static final String DATE_FORMAT_GMT = "yyyy-MM-dd HH:mm:ss";

        long datetime; // in milliseconds
        double latitude;
        double longtitude;

        @Nullable
        public static LocatonData parseJSON(JSONObject json){
            DateFormat df = new SimpleDateFormat(DATE_FORMAT_GMT, Locale.US);
            Date date = null;
            long datetime = -1;
            double latitude = 0.0;
            double longtitude = 0.0;
            String dateTimeStr, latitudeStr,LongtitudeStr;

            try {
               dateTimeStr = json.getString(KEY_DATETIME);
               latitude = json.getDouble(KEY_LATITUDE);
               longtitude = json.getDouble(KEY_LONGTITUDE);
               date = df.parse(dateTimeStr);
               datetime = date.getTime();
            } catch(JSONException e){
                return null;
            } catch (ParseException e){
                return null;
            }
            return new LocatonData(datetime, latitude, longtitude);
        }

        public LocatonData(String datetime, double latitude, double longtitude){
            DateFormat df = new SimpleDateFormat(DATE_FORMAT_GMT, Locale.US);
            Date date = null;
            try {
                date = df.parse(datetime);
            } catch (ParseException e){
                //error handle
            }

            this.datetime = (date != null) ? date.getTime() : -1;

            this.latitude = latitude;
            this.longtitude = longtitude;
        }

        public LocatonData(long datetime, double latitude, double longtitude){
            this.datetime = datetime;
            this.latitude = latitude;
            this.longtitude = longtitude;
        }

        public JSONObject JSONify(){
            JSONObject json = new JSONObject();
            try {
                Date datetime = new Date(this.datetime);
                DateFormat df = new SimpleDateFormat(DATE_FORMAT_GMT, Locale.US);
                String dateStr = df.format(datetime);
                json.put(KEY_DATETIME, dateStr);
                json.put(KEY_LATITUDE, this.latitude);
                json.put(KEY_LONGTITUDE, this.longtitude);

            } catch (JSONException e){
                //error handle
            }

            return json;
        }

        @Override
        public String toString() {
            return this.JSONify().toString();
        }
    }
    class PathData{
        public static final String KEY_DATETIME_BEGIN = "dtBegin";
        public static final String KEY_DATETIME_END = "dtEnd";
        public static final String KEY_LOCATION_ARRAY = "locaArr";
        public static final String DATE_FORMAT_GMT = "yyyy-MM-dd HH:mm:ss";

        long dtBegin;
        long dtEnd;
        LocatonData[] locaArr;

        public PathData(String dtBegin, String dtEnd, LocatonData[] locaArr) {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT_GMT, Locale.US);
            Date dBegin = null, dEnd = null;
            try{
                dBegin = df.parse(dtBegin);
                dEnd = df.parse(dtEnd);
            } catch (ParseException e){
                //error handle
            }

            this.dtBegin = (dBegin != null) ? dBegin.getTime() : -1;
            this.dtEnd = (dEnd != null) ? dEnd.getTime() : -1;
            this.locaArr = locaArr.clone();
        }

        public JSONObject JSONify(){
            JSONObject json = new JSONObject();
            JSONArray jsonPath = new JSONArray();
            try{
                Date dtBegin = new Date(this.dtBegin);
                Date dtEnd = new Date(this.dtEnd);
                DateFormat df = new SimpleDateFormat(DATE_FORMAT_GMT, Locale.US);
                json.put(KEY_DATETIME_BEGIN, df.format(dtBegin));
                json.put(KEY_DATETIME_END, df.format(dtEnd));
                for(LocationDataManager.LocatonData loca : this.locaArr){
                    jsonPath.put(loca);
                }
                json.put(KEY_LOCATION_ARRAY, jsonPath);
            } catch(JSONException e){

            }

            return json;
        }

        @Override
        public String toString() {
            return this.JSONify().toString();
        }
    }
}
