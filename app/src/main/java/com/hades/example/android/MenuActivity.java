package com.hades.example.android;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android._feature._web_based_contents._R8.J2v8Fragment;
import com.hades.example.android.tools.FragmentUtils;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);
        FragmentUtils.replaceFragment(this, R.id.root, new J2v8Fragment(), J2v8Fragment.TAG);
    }
}