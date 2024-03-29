/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hades.example.android.resource.bitmap.utils.cache;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.Log;

import androidx.fragment.app.FragmentManager;

import com.hades.example.android.resource.bitmap.utils.RetainImageCacheFragment;
import com.hades.example.android.resource.bitmap.utils.cache.disk.DiskCache;
import com.hades.example.android.resource.bitmap.utils.cache.mememory.BitmapMemoryCache;
import com.hades.utility.android.utils.IInBitmapListener;

public class ImageCache implements IInBitmapListener {
    public static final String TAG = ImageCache.class.getSimpleName();

    public static final int DEFAULT_COMPRESS_QUALITY = 70;

    BitmapMemoryCache mBitmapMemoryCache = new BitmapMemoryCache();
    DiskCache mDiskCache = new DiskCache();

    private ImageCache(ImageCacheParams cacheParams) {
        init(cacheParams);
    }

    public static ImageCache getInstance(FragmentManager fragmentManager, ImageCacheParams cacheParams) {
        final RetainImageCacheFragment mRetainFragment = findOrCreateRetainFragment(fragmentManager);
        ImageCache imageCache = (ImageCache) mRetainFragment.getObject();
        if (imageCache == null) {
            imageCache = new ImageCache(cacheParams);
            mRetainFragment.setObject(imageCache);
        }

        return imageCache;
    }

    public void initDiskCache() {
        mDiskCache.initDiskCache();
    }

    static RetainImageCacheFragment findOrCreateRetainFragment(FragmentManager fm) {
        RetainImageCacheFragment fragment = (RetainImageCacheFragment) fm.findFragmentByTag(RetainImageCacheFragment.TAG);
        if (null != fragment) {
            return fragment;
        }

        fragment = new RetainImageCacheFragment();
        fm.beginTransaction().add(fragment, RetainImageCacheFragment.TAG).commitAllowingStateLoss();

        return fragment;
    }


    public BitmapMemoryCache getMemoryCache() {
        return mBitmapMemoryCache;
    }

    public DiskCache getDiskCache() {
        return mDiskCache;
    }

    private void init(ImageCacheParams cacheParams) {
        mDiskCache.init(cacheParams);
        if (cacheParams.initDiskCacheOnCreate) {
            mDiskCache.initDiskCache();
        }

        if (cacheParams.memoryCacheEnabled) {
            Log.d(TAG, "Memory cache created (size = " + cacheParams.memCacheSize + ")");
            mBitmapMemoryCache.init(cacheParams);
        }
    }

    public void cacheBitmap(String data, BitmapDrawable value) {
        if (data == null || value == null) {
            return;
        }
        mBitmapMemoryCache.cacheBitmap(data, value);
        mDiskCache.cacheBitmap(data, value);
    }

    public BitmapDrawable getBitmapFromMemoryCache(String url) {
        BitmapDrawable memValue = mBitmapMemoryCache.getBitmapFromMemoryCache(url);
        if (memValue != null) {
            Log.d(TAG, "Memory cache hit");
        }
        return memValue;
    }

    public Bitmap getBitmapFromDiskCache(String url) {
        return mDiskCache.getBitmap(url, this);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public Bitmap getReusableBitmap4InBitmap(BitmapFactory.Options options) {
        return mBitmapMemoryCache.getReusableBitmap4InBitmap(options);
    }

    public void clearCache() {
        mBitmapMemoryCache.clearCache();
        mDiskCache.clearCache();
    }

    public void flush() {
        mDiskCache.flush();
    }

    public void close() {
        mDiskCache.close();
    }
}