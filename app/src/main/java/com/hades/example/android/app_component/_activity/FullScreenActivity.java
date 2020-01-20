package com.hades.example.android.app_component._activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.github.yingvickycao.autils.base.BaseActivity;
import com.hades.example.android.R;

public class FullScreenActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);
    }
}
