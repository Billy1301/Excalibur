package com.example.billy.excalibur.NyTimesAPIService.ArticleSearchAPI;

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

}
