package com.example.kotlin.test.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.internal.DefaultViewModelProviderFactory;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kotlin.test.R;
import com.example.kotlin.test.db.AppDatabase;
import com.example.kotlin.test.db.Menu;
import com.example.kotlin.test.db.MenuPageData;
import com.example.kotlin.test.db.PageData;
import com.example.kotlin.test.db.PageDataDao;
import com.example.kotlin.test.db.SimpleMenu;
import com.example.kotlin.test.viewmodel.MenuViewModule;
import com.example.kotlin.test.viewmodel.PageDataViewModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    private MenuViewModule menuViewModule;
    private PageDataViewModule pageDataViewModule;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerview);
        final MenuListAdapter adapter = new MenuListAdapter(new MenuListAdapter.MenuDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // create the view module
        // Error :Caused by: java.lang.RuntimeException: Cannot create an instance of class com.example.kotlin.test.viewmodel.MenuViewModule
        // https://blog.csdn.net/xiaojinlai123/article/details/106092108
        //        menuViewModule = new ViewModelProvider(this).get(MenuViewModule.class);
        menuViewModule = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(MenuViewModule.class);
        menuViewModule.getMenus().observe(this, new Observer<List<Menu>>() {
            @Override
            public void onChanged(List<Menu> menus) {
                adapter.submitList(menus);
            }
        });

        String menuCode = "437697905";
        menuViewModule.findMenuTitle(menuCode).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e(TAG, "onChanged: findMenuTitle=" + s);
            }
        });


        menuViewModule.findMenuSimple(menuCode).observe(this, new Observer<SimpleMenu>() {
            @Override
            public void onChanged(SimpleMenu s) {
                if (null != s) {
                    Log.e(TAG, "onChanged: findMenuSimple=" + s.menuTitle);
                }
            }
        });

        pageDataViewModule = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(PageDataViewModule.class);

        pageDataViewModule.getAll().observe(this, new Observer<List<PageData>>() {
            @Override
            public void onChanged(List<PageData> data) {
                if (data != null && !data.isEmpty()) {
                    for (PageData item : data) {
                        Log.e(TAG, "PageDataViewModule onChanged: " + item.toString());
                        return;
                    }
                }
                Log.e(TAG, "PageDataViewModule onChanged: empty");
            }
        });

        pageDataViewModule.getTitle(-2048508799).observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> list) {
                if (list != null && !list.isEmpty()) {
                    for (String item : list) {
                        Log.e(TAG, "PageDataViewModule getTitle by code: " + item);
                    }
                    return;
                }
                Log.e(TAG, "PageDataViewModule getTitle by code: empty");
            }
        });

        findViewById(R.id.title).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, NewMenuActivity.class);
                        startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
                    }
                }
        );

        findViewById(R.id.test).setOnClickListener(v -> test());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String menuText = data.getStringExtra(NewMenuActivity.EXTRA_REPLY);
            Menu menu = new Menu(menuText.hashCode(), menuText, "new");
            menuViewModule.insert(menu);
        } else {
            Toast.makeText(getApplicationContext(), "Empty not saved", Toast.LENGTH_LONG).show();
        }
    }

    private void test() {
//        List<Menu> menus = menuViewModule.getMenus().getValue();
//        for (Menu item : menus) {
////            List<PageData> pageDatas = new ArrayList<>();
//            for (int i = 0; i < 1000; i++) {
//                PageData pageData = new PageData();
//                pageData.id = (int) System.currentTimeMillis() + i + 1;
//                pageData.code = item.code;
//                pageData.title = "Item " + System.currentTimeMillis();
////                pageDatas.add(pageData);
//                pageDataViewModule.insert(pageData);
//            }
//        }


//        menuViewModule.delete(new Menu(439933464, "", ""));


//        List<String> list = pageDataViewModule.getTitle(-2048508799).getValue();
//        if (list != null && !list.isEmpty()) {
//            for (String item : list) {
//                Log.e(TAG, "PageDataViewModule getAll by code: " + item);
//                return;
//            }
//        }
//        Log.e(TAG, "PageDataViewModule getAll: empty");

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                List<String> list = pageDataViewModule.getTitle2(-2048508799);
//                if (list != null && !list.isEmpty()) {
//                    for (String item : list) {
//                        Log.e(TAG, "PageDataViewModule getAll by code: " + item);
//                    }
//                    return;
//                }
//                Log.e(TAG, "PageDataViewModule getAll by code: empty");
//            }
//        }).start();
//
//        AppDatabase.databaseExecutorService.execute(new Runnable() {
//            @Override
//            public void run() {
////                Map<Menu, List<PageData>> result = pageDataViewModule.loadMenuAndPageData();
//                List<MenuPageData> result = pageDataViewModule.loadMenuPageData();
//                Log.e(TAG, "run: " + result);
//            }
//        });

        List<MenuPageData> result = pageDataViewModule.loadMenuPageData();
        Log.e(TAG, "run: " + result);
    }
}