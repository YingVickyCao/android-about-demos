package com.hades.example.android.lib.exoplayer2.bean;

import static com.hades.example.android.lib.exoplayer2.PlayerActivity.ACTION_VIEW_LIST;

import android.content.Intent;

public final class PlaylistSample extends Sample {

    public final UriSample[] children;

    public PlaylistSample(String name, UriSample... children) {
        super(name);
        this.children = children;
    }

    @Override
    public void addToIntent(Intent intent) {
        intent.setAction(ACTION_VIEW_LIST);
        for (int i = 0; i < children.length; i++) {
            children[i].addToPlaylistIntent(intent, "_" + i);
        }
    }
}
