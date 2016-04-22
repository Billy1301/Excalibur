package com.example.billy.excalibur.NyTimesAPIService;

import android.net.ParseException;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Mikhail on 4/18/16.
 */
public class NewsWireObjects {

    private String section;
    private String title;
    private String url;
    private String thumbnail_standard;
    @SerializedName("abstract") private String abstractResult;
    private long created_date;
    private static final String TAG = "NewsWireObjects ";



    public NewsWireObjects() {
    }

    public NewsWireObjects(String section, String title, String url, String thumbnail_standard, String abstractResult, String created_date) throws java.text.ParseException {
        this.section = section;
        this.title = title;
        this.url = url;
        this.thumbnail_standard = thumbnail_standard;
        this.abstractResult = abstractResult;
        String givenDateString = created_date;
//                "Tue Apr 23 16:08:28 GMT+05:30 2013";
        Log.i(TAG, created_date);
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss z");

        long timeInMillis =0;
        try {
            Date mDate = sdf.parse(givenDateString);
            timeInMillis = mDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.i(TAG, String.valueOf(timeInMillis));
        this.created_date = timeInMillis; //"created_date": "2016-04-22T07:19:19-04:00"
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
        return created_date;
    }
}
