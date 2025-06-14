package com.example.kotlin.test.ui;

import android.content.Intent;
import android.os.Bundle;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kotlin.test.R;
import com.example.kotlin.test.db.Menu;
import com.example.kotlin.test.viewmodel.MenuViewModule;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    private MenuViewModule menuViewModule;

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

        findViewById(R.id.title).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, NewMenuActivity.class);
                        startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
                    }
                }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && requestCode == RESULT_OK) {
            String menuText = data.getStringExtra(NewMenuActivity.EXTRA_REPLY);
            Menu menu = new Menu(menuText.hashCode(), menuText, "new");
            menuViewModule.insert(menu);
        } else {
            Toast.makeText(getApplicationContext(), "Empty not saved", Toast.LENGTH_LONG).show();
        }
    }
}