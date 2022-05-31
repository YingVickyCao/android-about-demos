package com.hades.example.android.app_component.content_provider.system.media;

import android.net.Uri;

import java.io.Serializable;

public class GalleryItem implements Serializable {
    public int id;
    public String name;
    public Uri uri;
    public String path;

    public GalleryItem(Uri uri) {
        this.uri = uri;
    }

    public GalleryItem(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "GalleryItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", uri=" + uri +
                ", path='" + path + '\'' +
                '}';
    }
}