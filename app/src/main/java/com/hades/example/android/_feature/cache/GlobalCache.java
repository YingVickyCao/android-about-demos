package com.hades.example.android._feature.cache;


public class GlobalCache {
    private static GlobalCache mInstance;

    private GlobalCache() {

    }

    public static GlobalCache getInstance() {
        GlobalCache cache = mInstance;
        if (null == cache) {
            synchronized (GlobalCache.class) {
                if (null == mInstance) {
                    mInstance = new GlobalCache();
                }
            }
        }
        return mInstance;
    }
}