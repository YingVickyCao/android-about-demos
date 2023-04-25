package com.hades.example.android.media.audio.exoplayer;

public class ExampleMediaItem {
    private String name;
    private String url;
    private String scheme;
    private String subTitleUrl;
    private String subTitleMimeType;
    private String subtitleLanguage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getSubTitleUrl() {
        return subTitleUrl;
    }

    public void setSubTitleUrl(String subTitleUrl) {
        this.subTitleUrl = subTitleUrl;
    }

    public String getSubTitleMimeType() {
        return subTitleMimeType;
    }

    public void setSubTitleMimeType(String subTitleMimeType) {
        this.subTitleMimeType = subTitleMimeType;
    }

    public String getSubtitleLanguage() {
        return subtitleLanguage;
    }

    public void setSubtitleLanguage(String subtitleLanguage) {
        this.subtitleLanguage = subtitleLanguage;
    }
}
