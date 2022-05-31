package com.hades.example.android.app_component.content_provider.system.media;

import android.net.Uri;

import java.io.Serializable;

public class GalleryItem implements Serializable {
    public Uri uri;
    public String path;

    public GalleryItem(Uri uri, String path) {
        this.uri = uri;
        this.path = path;
    }
}