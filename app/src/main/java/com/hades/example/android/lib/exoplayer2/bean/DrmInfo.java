package com.hades.example.android.lib.exoplayer2.bean;

import static com.hades.example.android.lib.exoplayer2.PlayerActivity.DRM_KEY_REQUEST_PROPERTIES_EXTRA;
import static com.hades.example.android.lib.exoplayer2.PlayerActivity.DRM_LICENSE_URL_EXTRA;
import static com.hades.example.android.lib.exoplayer2.PlayerActivity.DRM_MULTI_SESSION_EXTRA;
import static com.hades.example.android.lib.exoplayer2.PlayerActivity.DRM_SCHEME_EXTRA;
import static com.hades.example.android.lib.exoplayer2.PlayerActivity.DRM_SCHEME_UUID_EXTRA;

import android.content.Intent;

import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

import java.util.UUID;

public final class DrmInfo {

    public static DrmInfo createFromIntent(Intent intent, String extrasKeySuffix) {
        String schemeKey = DRM_SCHEME_EXTRA + extrasKeySuffix;
        String schemeUuidKey = DRM_SCHEME_UUID_EXTRA + extrasKeySuffix;
        if (!intent.hasExtra(schemeKey) && !intent.hasExtra(schemeUuidKey)) {
            return null;
        }
        String drmSchemeExtra = intent.hasExtra(schemeKey) ? intent.getStringExtra(schemeKey) : intent.getStringExtra(schemeUuidKey);
        UUID drmScheme = Util.getDrmUuid(drmSchemeExtra);
        String drmLicenseUrl = intent.getStringExtra(DRM_LICENSE_URL_EXTRA + extrasKeySuffix);
        String[] keyRequestPropertiesArray = intent.getStringArrayExtra(DRM_KEY_REQUEST_PROPERTIES_EXTRA + extrasKeySuffix);
        boolean drmMultiSession = intent.getBooleanExtra(DRM_MULTI_SESSION_EXTRA + extrasKeySuffix, false);
        return new DrmInfo(drmScheme, drmLicenseUrl, keyRequestPropertiesArray, drmMultiSession);
    }

    public final UUID drmScheme;
    public final String drmLicenseUrl;
    public final String[] drmKeyRequestProperties;
    public final boolean drmMultiSession;

    public DrmInfo(UUID drmScheme, String drmLicenseUrl, String[] drmKeyRequestProperties, boolean drmMultiSession) {
        this.drmScheme = drmScheme;
        this.drmLicenseUrl = drmLicenseUrl;
        this.drmKeyRequestProperties = drmKeyRequestProperties;
        this.drmMultiSession = drmMultiSession;
    }

    public void addToIntent(Intent intent, String extrasKeySuffix) {
        Assertions.checkNotNull(intent);
        intent.putExtra(DRM_SCHEME_EXTRA + extrasKeySuffix, drmScheme.toString());
        intent.putExtra(DRM_LICENSE_URL_EXTRA + extrasKeySuffix, drmLicenseUrl);
        intent.putExtra(DRM_KEY_REQUEST_PROPERTIES_EXTRA + extrasKeySuffix, drmKeyRequestProperties);
        intent.putExtra(DRM_MULTI_SESSION_EXTRA + extrasKeySuffix, drmMultiSession);
    }
}
