package com.hades.example.android.test.apk_upgrade;

import org.json.JSONObject;

import java.io.Serializable;

public class AppVersionBean implements Serializable {
    private String tittle;
    private String content;
    private String url;
    private String md5;
    private String versionCode;

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public boolean isValid() {
        // TODO:
        return true;
    }

    public static AppVersionBean parse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String title = jsonObject.optString("title");
            String content = jsonObject.optString("content");
            String url = jsonObject.optString("url");
            String md5 = jsonObject.optString("md5");
            String versionCode = jsonObject.optString("versionCode");

            AppVersionBean bean = new AppVersionBean();
            bean.tittle = title;
            bean.content = content;
            bean.url = url;
            bean.md5 = md5;
            bean.versionCode = versionCode;
            return bean;
        } catch (Exception exception) {
            return null;
        }
    }
}
