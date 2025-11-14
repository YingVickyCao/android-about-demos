package com.hades.example.android.app_component.content_provider.dict.common;

public class DictRowBean {
    private long _id = -1;
    private String word;
    private String detail;

    public DictRowBean(long _id, String word, String detail) {
        this._id = _id;
        this.word = word;
        this.detail = detail;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
