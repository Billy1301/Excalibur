package com.example.billy.excalibur.NyTimesAPIService;

import com.google.gson.annotations.SerializedName;

/**
 * Created by michaelmuccio on 4/19/16.
 */
public class ArticleSearchObjects {
    private String snippet;
    private String web_url;
    private String lead_paragraph;
    private String[] headline;

    public ArticleSearchObjects(String snippet, String web_url, String lead_paragraph, String[] headline) {
        this.snippet = snippet;
        this.web_url = web_url;
        this.lead_paragraph = lead_paragraph;
        this.headline = headline;
    }

    public String getSnippet() {
        return snippet;
    }

    public String getWeb_url() {
        return web_url;
    }

    public String getLead_paragraph() {
        return lead_paragraph;
    }

    public String[] getHeadline() {
        return headline;
    }
}
