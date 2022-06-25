package com.hades.example.android.lib;

import android.os.Bundle;

import com.hades.example.android.R;
import com.hades.example.android.base.BaseActivity;
import com.hades.example.android.lib.exoplayer2.TestExoplayer2MainActivity;

public class TestLibsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libs);
        initViews();

        findViewById(R.id.page_Exoplayer2).setOnClickListener(v -> page_Exoplayer2());
    }

    private void page_Exoplayer2() {
        showActivity(TestExoplayer2MainActivity.class);
    }
}
