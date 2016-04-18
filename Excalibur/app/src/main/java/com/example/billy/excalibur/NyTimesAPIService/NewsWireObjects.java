package com.example.billy.excalibur.NyTimesAPIService;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mikhail on 4/18/16.
 */
public class NewsWireObjects {

    NewsWireResults newsWireResults;
    private String section;
    private String title;
    private String url;
    private String thumbnail_standard;

    @SerializedName("abstract") private String abstractResult;

    public NewsWireResults getNewsWireResults() {
        return newsWireResults;
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
