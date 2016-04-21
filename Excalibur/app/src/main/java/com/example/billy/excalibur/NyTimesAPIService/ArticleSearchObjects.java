package com.example.billy.excalibur.NyTimesAPIService;

/** Full Db search objects extending Article search objects and setting full article db to breaking
 * new articles.
 * Created by michaelmuccio on 4/19/16.
 */
public class ArticleSearchObjects {
    private String snippet;
    private String web_url;
    private String section_name;
    private String headline;
    private String multimedia;

    public String getSnippet() {
        return snippet;
    }

    public String getWeb_url() {
        return web_url;
    }

    public String getSection_name() {
        return section_name;
    }

    public String getHeadline() {
        return headline;
    }

    public String getMultimedia() {
        return multimedia;
    }
}
