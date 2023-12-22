package com.hades.example.android._feature.menu_manager;

import android.util.Log;

import com.hades.utility.jvm.JsonUtil;


public class MenuParser {
    private static final String TAG = "ParseMenu";

    public Menu parseMenu(String json) {
        Menu menu = new JsonUtil().fromJson(json, Menu.class);
        Log.d(TAG, "parseMenu: " + menu);
        return menu;
    }
}
