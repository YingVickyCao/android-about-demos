package com.hades.example.android._feature.menu_manager.menu_page;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android.R;
import com.hades.example.android._feature.cache.GlobalCache;
import com.hades.example.android._feature.menu_manager.Menu;
import com.hades.example.android._feature.menu_manager.MenuParser;
import com.hades.example.android.lib.utils.BlockQuickTap;
import com.hades.example.java.lib.FileUtils;

public class MenuActivity extends AppCompatActivity {
    private static final String TAG = "MenuActivity";
    MenuHandler menuHandler = new MenuHandler();
    MenuFragment menuFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseMenuTree();
        menuHandler.setActivity(this);
        setContentView(R.layout.menu_layout);
//        FragmentUtils.replaceFragment(this, R.id.root, new WorkManagerFragment(), WorkManagerFragment.TAG);
//        findViewById(R.id.test).setOnClickListener(v -> test());

        findViewById(R.id.menu).setOnClickListener(v -> {
            if (BlockQuickTap.isFastClickBtn(R.id.menu)) {
                return;
            }
            if (null == menuFragment) {
                menuFragment = new MenuFragment();
            }
            menuFragment.show(getSupportFragmentManager(), MenuFragment.class.getSimpleName());
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != menuHandler) {
            menuHandler.setActivity(null);
        }
    }

    private void parseMenuTree() {
        try {
            String json = FileUtils.getInstance().convert(getResources().getAssets().open("menus.json"));
            MenuParser menuParser = new MenuParser();
            Menu menu = menuParser.parseMenu(json);
            GlobalCache.getInstance().setMenuTree(menu);
        } catch (Exception ex) {
            Log.d(TAG, "onCreate: " + ex);
        }
    }

    public void openMenu(Menu menu) {
        Log.d(TAG, "openMenu: ");
        if (null == menu) {
            return;
        }

        Message message = menuHandler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putString("code", menu.getCode());
        bundle.putString("type", menu.getType());
        message.setData(bundle);
        menuHandler.sendMessage(message);
    }
}
