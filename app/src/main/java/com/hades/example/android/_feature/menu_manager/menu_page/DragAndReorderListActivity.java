package com.hades.example.android._feature.menu_manager.menu_page;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android.R;


public class DragAndReorderListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_recyclerview_4_drag_reorder_list_activity_v2);
    }
}