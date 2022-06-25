package com.hades.example.android.test_libs.exoplayer2.bean;

import static com.hades.example.android.test_libs.exoplayer2.PlayerActivity.SUBTITLE_LANGUAGE_EXTRA;
import static com.hades.example.android.test_libs.exoplayer2.PlayerActivity.SUBTITLE_MIME_TYPE_EXTRA;
import static com.hades.example.android.test_libs.exoplayer2.PlayerActivity.SUBTITLE_URI_EXTRA;

import android.content.Intent;
import android.net.Uri;

import androidx.annotation.Nullable;

import com.google.android.exoplayer2.util.Assertions;

public final class SubtitleInfo {

    @Nullable
    public static SubtitleInfo createFromIntent(Intent intent, String extrasKeySuffix) {
        if (!intent.hasExtra(SUBTITLE_URI_EXTRA + extrasKeySuffix)) {
            return null;
        }
        return new SubtitleInfo(Uri.parse(intent.getStringExtra(SUBTITLE_URI_EXTRA + extrasKeySuffix)),
                intent.getStringExtra(SUBTITLE_MIME_TYPE_EXTRA + extrasKeySuffix),
                intent.getStringExtra(SUBTITLE_LANGUAGE_EXTRA + extrasKeySuffix));
    }

    public final Uri uri;
    public final String mimeType;
    @Nullable
    public final String language;

    public SubtitleInfo(Uri uri, String mimeType, @Nullable String language) {
        this.uri = Assertions.checkNotNull(uri);
        this.mimeType = Assertions.checkNotNull(mimeType);
        this.language = language;
    }

    public void addToIntent(Intent intent, String extrasKeySuffix) {
        intent.putExtra(SUBTITLE_URI_EXTRA + extrasKeySuffix, uri.toString());
        intent.putExtra(SUBTITLE_MIME_TYPE_EXTRA + extrasKeySuffix, mimeType);
        intent.putExtra(SUBTITLE_LANGUAGE_EXTRA + extrasKeySuffix, language);
    }
}
