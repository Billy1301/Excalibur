package com.example.billy.excalibur.NyTimesAPIService.ArticleSearchAPI;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/** Full Db search objects extending Article search objects and setting full article db to breaking
 * new articles.
 * Created by michaelmuccio on 4/19/16.
 */
public class Doc {
    private String lead_paragraph;
    private String web_url;
    private String section_name;
    private Headlines headline;
    private Multimedia[] multimedia;
    private String pub_date;

    public String getLead_paragraph() {
        return lead_paragraph;
    }

    public String getWeb_url() {
        return web_url;
    }

    public Headlines getHeadline() {
        return headline;
    }

    public Multimedia[] getMultimedia() {
        return multimedia;
    }

    public Long getPub_date() {


//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ZZZ");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


        long timeInMillis = 0;
        try {
            Date mDate = sdf.parse(pub_date.substring(0,10));
            Log.i("pubdate", pub_date.substring(0,10));
            timeInMillis = mDate.getTime();
            Log.i("pubdate", String.valueOf(timeInMillis));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        //"pub_date": "2016-04-16T00:00:00Z",
        return timeInMillis;
    }
}
