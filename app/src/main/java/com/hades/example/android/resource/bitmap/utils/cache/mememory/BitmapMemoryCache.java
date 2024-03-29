package com.hades.example.android.resource.bitmap.utils.cache.mememory;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import androidx.collection.LruCache;

import com.hades.utility.android.utils.ImageUtil;
import com.hades.utility.android.utils.VersionUtil;
import com.hades.example.android.resource.bitmap.utils.cache.ImageCacheParams;

import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BitmapMemoryCache {
    private static final String TAG = BitmapMemoryCache.class.getSimpleName();

    public static final int DEFAULT_MEMORY_CACHE_KILOBYTES_SIZE = 1024 * 5; // 5MB
    public static final boolean DEFAULT_MEMORY_CACHE_ENABLED = true;

    ImageUtil imageUtil = new ImageUtil();

    private LruCache<String, BitmapDrawable> mCachedBitmapDrawableLruCache;
    private Set<SoftReference<Bitmap>> mReusableBitmapsPopulatedIntoInBitmap;

    public void init(ImageCacheParams cacheParams) {
        if (VersionUtil.isNoLessThanV3()) {
            mReusableBitmapsPopulatedIntoInBitmap = Collections.synchronizedSet(new HashSet<SoftReference<Bitmap>>());
        }

        mCachedBitmapDrawableLruCache = new LruCache<String, BitmapDrawable>(cacheParams.memCacheSize) {
            @Override
            protected void entryRemoved(boolean evicted, String key, BitmapDrawable oldValue, BitmapDrawable newValue) {
                if (VersionUtil.isNoLessThanV3()) {
                    mReusableBitmapsPopulatedIntoInBitmap.add(new SoftReference<>(oldValue.getBitmap()));
                }
            }

            @Override
            protected int sizeOf(String key, BitmapDrawable value) {
                final int bitmapSize = imageUtil.getBitmapBytesSizeAtBitmapDrawable(value) / 1024;
                return bitmapSize == 0 ? 1 : bitmapSize;
            }
        };
    }

    public void cacheBitmap(String data, BitmapDrawable value) {
        if (data == null || value == null) {
            return;
        }

        if (mCachedBitmapDrawableLruCache != null) {
            mCachedBitmapDrawableLruCache.put(data, value);
        }
    }

    public BitmapDrawable getBitmapFromMemoryCache(String url) {
        BitmapDrawable bitmapDrawable = null;

        if (mCachedBitmapDrawableLruCache != null) {
            bitmapDrawable = mCachedBitmapDrawableLruCache.get(url);
        }
        return bitmapDrawable;
    }

    public void clearCache() {
        if (mCachedBitmapDrawableLruCache != null) {
            mCachedBitmapDrawableLruCache.evictAll();
        }
    }

    public Bitmap getReusableBitmap4InBitmap(BitmapFactory.Options options) {
        Bitmap bitmap = null;
        if (mReusableBitmapsPopulatedIntoInBitmap != null && !mReusableBitmapsPopulatedIntoInBitmap.isEmpty()) {
            synchronized (mReusableBitmapsPopulatedIntoInBitmap) {
                final Iterator<SoftReference<Bitmap>> iterator = mReusableBitmapsPopulatedIntoInBitmap.iterator();
                Bitmap item;
                while (iterator.hasNext()) {
                    item = iterator.next().get();
                    if (null != item && item.isMutable()) {
                        if (imageUtil.canUsedForInBitmapReuseWithTargetOptions(item, options)) {
                            bitmap = item;
                            iterator.remove();
                            break;
                        }
                    } else {
                        iterator.remove();
                    }
                }
            }
        }
        return bitmap;
    }
}
