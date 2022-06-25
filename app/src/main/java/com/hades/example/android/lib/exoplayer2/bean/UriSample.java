package com.hades.example.android.lib.exoplayer2.bean;

import static com.hades.example.android.lib.exoplayer2.PlayerActivity.AD_TAG_URI_EXTRA;
import static com.hades.example.android.lib.exoplayer2.PlayerActivity.EXTENSION_EXTRA;
import static com.hades.example.android.lib.exoplayer2.PlayerActivity.IS_LIVE_EXTRA;
import static com.hades.example.android.lib.exoplayer2.PlayerActivity.URI_EXTRA;

import android.content.Intent;
import android.net.Uri;

import androidx.annotation.Nullable;

import com.hades.example.android.lib.exoplayer2.PlayerActivity;

public final class UriSample extends Sample {
    public static UriSample createFromIntent(Uri uri, Intent intent, String extrasKeySuffix) {
        String extension = intent.getStringExtra(EXTENSION_EXTRA + extrasKeySuffix);
        String adsTagUriString = intent.getStringExtra(AD_TAG_URI_EXTRA + extrasKeySuffix);
        boolean isLive = intent.getBooleanExtra(IS_LIVE_EXTRA + extrasKeySuffix, /* defaultValue= */ false);
        Uri adTagUri = adsTagUriString != null ? Uri.parse(adsTagUriString) : null;
        return new UriSample(null, uri, extension, isLive, DrmInfo.createFromIntent(intent, extrasKeySuffix), adTagUri, null, SubtitleInfo.createFromIntent(intent, extrasKeySuffix));
    }

    public final Uri uri;
    public final String extension;
    public final boolean isLive;
    public final DrmInfo drmInfo;
    public final Uri adTagUri;
    @Nullable
    public final String sphericalStereoMode;
    @Nullable
    public SubtitleInfo subtitleInfo;

    public UriSample(String name, Uri uri, String extension, boolean isLive, DrmInfo drmInfo, Uri adTagUri, @Nullable String sphericalStereoMode, @Nullable SubtitleInfo subtitleInfo) {
        super(name);
        this.uri = uri;
        this.extension = extension;
        this.isLive = isLive;
        this.drmInfo = drmInfo;
        this.adTagUri = adTagUri;
        this.sphericalStereoMode = sphericalStereoMode;
        this.subtitleInfo = subtitleInfo;
    }

    @Override
    public void addToIntent(Intent intent) {
        intent.setAction(PlayerActivity.ACTION_VIEW).setData(uri);
        intent.putExtra(PlayerActivity.IS_LIVE_EXTRA, isLive);
        intent.putExtra(PlayerActivity.SPHERICAL_STEREO_MODE_EXTRA, sphericalStereoMode);
        addPlayerConfigToIntent(intent, "");
    }

    public void addToPlaylistIntent(Intent intent, String extrasKeySuffix) {
        intent.putExtra(URI_EXTRA + extrasKeySuffix, uri.toString());
        intent.putExtra(PlayerActivity.IS_LIVE_EXTRA + extrasKeySuffix, isLive);
        addPlayerConfigToIntent(intent, extrasKeySuffix);
    }

    private void addPlayerConfigToIntent(Intent intent, String extrasKeySuffix) {
        intent.putExtra(EXTENSION_EXTRA + extrasKeySuffix, extension).putExtra(AD_TAG_URI_EXTRA + extrasKeySuffix, adTagUri != null ? adTagUri.toString() : null);
        if (drmInfo != null) {
            drmInfo.addToIntent(intent, extrasKeySuffix);
        }
        if (subtitleInfo != null) {
            subtitleInfo.addToIntent(intent, extrasKeySuffix);
        }
    }
}