package com.hades.example.android._feature.menu_manager.menu_page;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android.R;
import com.hades.example.android._feature.cache.GlobalCache;
import com.hades.example.android._feature.menu_manager.Menu;
import com.hades.example.android._feature.menu_manager.MenuParser;
import com.hades.example.java.lib.FileUtils;

public class MenuActivity extends AppCompatActivity {
    private static final String TAG = "MenuActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);
//        FragmentUtils.replaceFragment(this, R.id.root, new J2v8Fragment(), J2v8Fragment.TAG);
//        FragmentUtils.replaceFragment(this, R.id.root, new WorkManagerFragment(), WorkManagerFragment.TAG);
//        findViewById(R.id.test).setOnClickListener(v -> test());
    }

    private void test() {
        try {
            String json = FileUtils.getInstance().convert(getResources().getAssets().open("menus.json"));
            MenuParser menuParser = new MenuParser();
            Menu menu = menuParser.parseMenu(json);
            GlobalCache.getInstance().setMenuTree(menu);
        } catch (Exception ex) {
            Log.d(TAG, "onCreate: " + ex);
        }
    }
}
