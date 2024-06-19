package com.hades.example.android.resource.bitmap.utils.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.hades.example.android.BuildConfig;
import com.hades.utility.android.utils.AndroidStorageUtils;
import com.hades.example.android.resource.bitmap.utils.cache.disk.DiskCache;
import com.hades.example.android.resource.bitmap.utils.cache.mememory.BitmapMemoryCache;

import java.io.File;

/**
 * A holder class that contains cache parameters.
 */
public class ImageCacheParams {
    private static final String TAG = ImageCacheParams.class.getSimpleName();

    public static final String SDK_NAME = "bitmap_three_level_cache";
    public static final String SDK_USER_AGENT = SDK_NAME + BuildConfig.VERSION_NAME;
    public static final int THREAD_STATS_TAG = SDK_NAME.hashCode();

    public int memCacheSize = BitmapMemoryCache.DEFAULT_MEMORY_CACHE_KILOBYTES_SIZE;
    public int diskCacheSize = DiskCache.DEFAULT_DISK_CACHE_BYTES_SIZE;
    public File diskCacheDir;
    public Bitmap.CompressFormat compressFormat = DiskCache.DEFAULT_COMPRESS_FORMAT;
    public int compressQuality = ImageCache.DEFAULT_COMPRESS_QUALITY;
    public boolean memoryCacheEnabled = BitmapMemoryCache.DEFAULT_MEMORY_CACHE_ENABLED;
    public boolean diskCacheEnabled = DiskCache.DEFAULT_DISK_CACHE_ENABLED;
    public boolean initDiskCacheOnCreate = DiskCache.DEFAULT_INIT_DISK_CACHE_ON_CREATE;

    AndroidStorageUtils fileUtil = new AndroidStorageUtils();

    public ImageCacheParams(Context context, String diskCacheDirectoryName) {
        diskCacheDir = fileUtil.getDiskCacheDir(context, diskCacheDirectoryName);
        Log.i(TAG, "ImageCacheParams: diskCacheDir=" + diskCacheDir.getPath());
    }

    public ImageCacheParams(Context context, String diskCacheDirectoryName, float percentOfAppMemory) {
        this(context, diskCacheDirectoryName);
        memCacheSize = setMemCacheSizePercent(percentOfAppMemory);
    }

    public int setMemCacheSizePercent(float percentOfAppMemory) {
        if (percentOfAppMemory < 0.01f || percentOfAppMemory > 0.8f) {
            throw new IllegalArgumentException("setMemCacheSizePercent - percent must be between 0.01 and 0.8 (inclusive)");
        }
        return Math.round(percentOfAppMemory * getAppMemory() / 1024);
    }

    private float getAppMemory() {
        return Runtime.getRuntime().maxMemory();
    }

}
