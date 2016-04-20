package com.example.billy.excalibur.NyTimesAPIService;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mikhail on 4/18/16.
 */
public class NewsWireObjects {

    private String section;
    private String title;
    private String url;
    private String thumbnail_standard;
    @SerializedName("abstract") private String abstractResult;
    


    public NewsWireObjects() {
    }

    public NewsWireObjects(String section, String title, String url, String thumbnail_standard, String abstractResult) {
        this.section = section;
        this.title = title;
        this.url = url;
        this.thumbnail_standard = thumbnail_standard;
        this.abstractResult = abstractResult;
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
}
