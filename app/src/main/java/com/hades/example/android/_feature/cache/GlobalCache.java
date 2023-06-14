package com.hades.example.android._feature.cache;

import com.hades.example.android._feature.menu_manager.Menu;

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


    private Menu menuTree;

    public Menu getMenuTree() {
        return menuTree;
    }

    public void setMenuTree(Menu menuTree) {
        this.menuTree = menuTree;
    }
}