package com.hades.example.android;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Configuration;

import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.database.DatabaseProvider;
import com.google.android.exoplayer2.database.ExoDatabaseProvider;
import com.google.android.exoplayer2.offline.ActionFileUpgradeUtil;
import com.google.android.exoplayer2.offline.DefaultDownloadIndex;
import com.google.android.exoplayer2.offline.DefaultDownloaderFactory;
import com.google.android.exoplayer2.offline.DownloadManager;
import com.google.android.exoplayer2.offline.DownloaderConstructorHelper;
import com.google.android.exoplayer2.ui.DownloadNotificationHelper;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.FileDataSource;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.NoOpCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.exoplayer2.util.Util;
import com.hades.example.android.test_libs.exoplayer2.DownloadTracker;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;

public class App extends Application implements Configuration.Provider {
    private static final String TAG = "App";

    // ExoPlayer2,STAT
    public static final String DOWNLOAD_NOTIFICATION_CHANNEL_ID = "download_channel";
    private static final String DOWNLOAD_ACTION_FILE = "actions";
    private static final String DOWNLOAD_TRACKER_ACTION_FILE = "tracked_actions";
    private static final String DOWNLOAD_CONTENT_DIRECTORY = "downloads";

    protected String userAgent;

    private DatabaseProvider databaseProvider;
    private File downloadDirectory;
    private Cache downloadCache;
    private DownloadManager downloadManager;
    private DownloadTracker downloadTracker;
    private DownloadNotificationHelper downloadNotificationHelper;

    // ExoPlayer2,END
    @Override
    public void onCreate() {
        super.onCreate();
        detect_if_new_install_or_updated_version();

        // ExoPlayer2,START
        userAgent = Util.getUserAgent(this, "ExoPlayerDemo");
        // ExoPlayer2,END
    }

    // ExoPlayer2,START
    // detect if new install or updated version
    private void detect_if_new_install_or_updated_version() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            long firstInstallTime = packageInfo.firstInstallTime;
            long lastUpdateTime = packageInfo.lastUpdateTime;
            boolean flag = (firstInstallTime == lastUpdateTime);
            int versionCode = packageInfo.versionCode;
            String versionName = packageInfo.versionName;
            Log.d(TAG, "isFirstInstalled: flag=" + flag);
            Log.d(TAG, "isFirstInstalled: firstInstallTime=" + firstInstallTime);
            Log.d(TAG, "isFirstInstalled: lastUpdateTime= " + lastUpdateTime);
            Log.d(TAG, "isFirstInstalled: versionCode= " + versionCode);
            Log.d(TAG, "isFirstInstalled: versionName= " + versionName);
            // cached_versionCode vs versionCode
        } catch (PackageManager.NameNotFoundException ex) {
            ex.printStackTrace();
            Log.d(TAG, "isFirstInstalled: ex:" + ex.getMessage());
        }
    }

    /**
     * Returns a {@link DataSource.Factory}.
     */
    public DataSource.Factory buildDataSourceFactory() {
        DefaultDataSourceFactory upstreamFactory = new DefaultDataSourceFactory(this, buildHttpDataSourceFactory());
        return buildReadOnlyCacheDataSource(upstreamFactory, getDownloadCache());
    }

    /**
     * Returns a {@link HttpDataSource.Factory}.
     */
    public HttpDataSource.Factory buildHttpDataSourceFactory() {
        HttpDataSource.Factory factory = new DefaultHttpDataSourceFactory(userAgent);
//        HttpDataSource.RequestProperties requestProperties = factory.getDefaultRequestProperties();
//        requestProperties.clear();
//        requestProperties.remove("Cookie");
//        requestProperties.set("Cookie","k1=v1");
//        requestProperties.set("Cookie","k2=v2");
        return factory;
    }

    /**
     * Returns whether extension renderers should be used.
     */
    public boolean useExtensionRenderers() {
        return false;
    }

    public RenderersFactory buildRenderersFactory(boolean preferExtensionRenderer) {
        @DefaultRenderersFactory.ExtensionRendererMode
        int extensionRendererMode =
                useExtensionRenderers()
                        ? (preferExtensionRenderer
                        ? DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER
                        : DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON)
                        : DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF;
        return new DefaultRenderersFactory(/* context= */ this)
                .setExtensionRendererMode(extensionRendererMode);
    }

    public DownloadNotificationHelper getDownloadNotificationHelper() {
        if (downloadNotificationHelper == null) {
            downloadNotificationHelper = new DownloadNotificationHelper(this, DOWNLOAD_NOTIFICATION_CHANNEL_ID);
        }
        return downloadNotificationHelper;
    }

    public DownloadManager getDownloadManager() {
        initDownloadManager();
        return downloadManager;
    }

    public DownloadTracker getDownloadTracker() {
        initDownloadManager();
        return downloadTracker;
    }

    protected synchronized Cache getDownloadCache() {
        if (downloadCache == null) {
            File downloadContentDirectory = new File(getDownloadDirectory(), DOWNLOAD_CONTENT_DIRECTORY);
            downloadCache = new SimpleCache(downloadContentDirectory, new NoOpCacheEvictor(), getDatabaseProvider());
        }
        return downloadCache;
    }

    private synchronized void initDownloadManager() {
        if (downloadManager == null) {
            DefaultDownloadIndex downloadIndex = new DefaultDownloadIndex(getDatabaseProvider());
            upgradeActionFile(DOWNLOAD_ACTION_FILE, downloadIndex, /* addNewDownloadsAsCompleted= */ false);
            upgradeActionFile(DOWNLOAD_TRACKER_ACTION_FILE, downloadIndex, /* addNewDownloadsAsCompleted= */ true);
            DownloaderConstructorHelper downloaderConstructorHelper = new DownloaderConstructorHelper(getDownloadCache(), buildHttpDataSourceFactory());
            downloadManager = new DownloadManager(this, downloadIndex, new DefaultDownloaderFactory(downloaderConstructorHelper));
            downloadTracker = new DownloadTracker(/* context= */ this, buildDataSourceFactory(), downloadManager);
        }
    }

    private void upgradeActionFile(
            String fileName, DefaultDownloadIndex downloadIndex, boolean addNewDownloadsAsCompleted) {
        try {
            ActionFileUpgradeUtil.upgradeAndDelete(new File(getDownloadDirectory(), fileName), /* downloadIdProvider= */ null,
                    downloadIndex,                    /* deleteOnFailure= */ true, addNewDownloadsAsCompleted);
        } catch (IOException e) {
            com.google.android.exoplayer2.util.Log.e(TAG, "Failed to upgrade action file: " + fileName, e);
        }
    }

    private DatabaseProvider getDatabaseProvider() {
        if (databaseProvider == null) {
            databaseProvider = new ExoDatabaseProvider(this);
        }
        return databaseProvider;
    }

    private File getDownloadDirectory() {
        if (downloadDirectory == null) {
            downloadDirectory = getExternalFilesDir(null);
            if (downloadDirectory == null) {
                downloadDirectory = getFilesDir();
            }
        }
        return downloadDirectory;
    }

    protected static CacheDataSourceFactory buildReadOnlyCacheDataSource(DataSource.Factory upstreamFactory, Cache cache) {
        return new CacheDataSourceFactory(cache, upstreamFactory, new FileDataSource.Factory(),
                /* cacheWriteDataSinkFactory= */ null,
                CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR,
                /* eventListener= */ null);
    }
    // ExoPlayer2,END

    /*
        Work Manager :
        Enable Logging
        Step 2 : Provide custom WorkManager Configuration

        Step 3 : see logs with log-tag prefix WM-
        adb shell dumpsys jobscheduler > log.txt
    */
    @NonNull
    @Override
    public Configuration getWorkManagerConfiguration() {
        // WorkManager will be init when call WorkManager.getInstance(Context) rather than auto at application startup.
        return new Configuration.Builder()
                .setMinimumLoggingLevel(Log.DEBUG)
                .setExecutor(Executors.newFixedThreadPool(8))
                .build();
    }
}
