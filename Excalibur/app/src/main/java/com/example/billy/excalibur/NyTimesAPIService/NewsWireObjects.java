package com.example.billy.excalibur.NyTimesAPIService;

import android.net.ParseException;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Mikhail on 4/18/16.
 */
public class NewsWireObjects {

    private String section;
    private String title;
    private String url;
    private String thumbnail_standard;
    @SerializedName("abstract") private String abstractResult;
    private String created_date;
    private static final String TAG = "NewsWireObjects ";



    public NewsWireObjects() {
    }

    public NewsWireObjects(String section, String title, String url, String thumbnail_standard, String abstractResult, String created_date) {
        this.section = section;
        this.title = title;
        this.url = url;
        this.thumbnail_standard = thumbnail_standard;
        this.abstractResult = abstractResult;
        this.created_date = created_date;
    }


    public String getSection() {
        return section;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getThumbnail_standard() {
        return thumbnail_standard;
    }

    public String getAbstractResult() {
        return abstractResult;
    }

    public long getCreated_date() {
        Log.i(TAG, created_date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ");

        long timeInMillis = 0;
        try {
            Date mDate = sdf.parse(created_date);
            timeInMillis = mDate.getTime();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        Log.i(TAG, String.valueOf(timeInMillis));
        //"created_date": "2016-04-22T07:19:19-04:00"
        return timeInMillis;
    }
}
