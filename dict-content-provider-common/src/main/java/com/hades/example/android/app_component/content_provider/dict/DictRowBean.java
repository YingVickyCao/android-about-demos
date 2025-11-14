package com.hades.example.android.app_component.content_provider.dict;

public class DictRowBean {
    private long _id = -1;
    private String word;
    private int detail;

    public DictRowBean(long id, String col2, int col3) {
        this._id = id;
        this.word = col2;
        this.detail = col3;
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

    public int getDetail() {
        return detail;
    }

    public void setDetail(int detail) {
        this.detail = detail;
    }
}
