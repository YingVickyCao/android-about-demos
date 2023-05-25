package com.hades.example.android;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android._process_and_thread.workmanager.WorkManagerFragment;
import com.hades.example.android.tools.FragmentUtils;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);
//        FragmentUtils.replaceFragment(this, R.id.root, new J2v8Fragment(), J2v8Fragment.TAG);
        FragmentUtils.replaceFragment(this, R.id.root, new WorkManagerFragment(), WorkManagerFragment.TAG);
    }
}
