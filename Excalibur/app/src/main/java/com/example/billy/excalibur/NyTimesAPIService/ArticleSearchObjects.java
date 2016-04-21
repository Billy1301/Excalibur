package com.example.billy.excalibur.NyTimesAPIService;

/** Full Db search objects extending Article search objects and setting full article db to breaking
 * new articles.
 * Created by michaelmuccio on 4/19/16.
 */
public class ArticleSearchObjects extends NewsWireObjects {
    private String snippet;
    private String web_url;
    private String section_name;
    private String headline;

    public ArticleSearchObjects() {
    }

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

    @Override
    public String getAbstractResult() {
        return snippet;
    }

    @Override
    public String getTitle() {
        return headline;
    }

    @Override
    public String getUrl() {
        return web_url;
    }

    @Override
    public String getSection() {
        return section_name;
    }
}
