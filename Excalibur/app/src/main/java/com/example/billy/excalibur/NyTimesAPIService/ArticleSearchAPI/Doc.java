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

    public String getSection_name() {
        return section_name;
    }

    public Headlines getHeadline() {
        return headline;
    }

    public Multimedia[] getMultimedia() {
        return multimedia;
    }

    public void setLead_paragraph(String lead_paragraph) {
        this.lead_paragraph = lead_paragraph;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public void setSection_name(String section_name) {
        this.section_name = section_name;
    }

    public void setHeadline(Headlines headline) {
        this.headline = headline;
    }

    public void setMultimedia(Multimedia[] multimedia) {
        this.multimedia = multimedia;
    }
}
