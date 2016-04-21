package com.example.billy.excalibur.SaveForLater;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by petermartinez on 4/20/16.
 */
public class ArticleForLater extends RealmObject {

    private String html;
    private String title;
    private String url;
    private String thumbnail_standard;
    private String abstractResult;
    @PrimaryKey
    private int code;

    public ArticleForLater() {}

    public int getCode() {
        return code;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail_standard() {
        return thumbnail_standard;
    }

    public void setThumbnail_standard(String thumbnail_standard) {
        this.thumbnail_standard = thumbnail_standard;
    }

    public String getAbstractResult() {
        return abstractResult;
    }

    public void setAbstractResult(String abstractResult) {
        this.abstractResult = abstractResult;
    }
}
