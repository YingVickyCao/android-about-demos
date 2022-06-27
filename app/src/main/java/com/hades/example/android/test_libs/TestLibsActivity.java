package com.hades.example.android.test_libs;

import android.os.Bundle;

import com.hades.example.android.R;
import com.hades.example.android.base.BaseActivity;
import com.hades.example.android.test_libs.exoplayer2.TestExoplayer2MainActivity;
import com.hades.example.android.test_libs.okhttp.TestOkHttpActivity;

public class TestLibsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libs);
        initViews();

        findViewById(R.id.page_Exoplayer2).setOnClickListener(v -> page_Exoplayer2());
        findViewById(R.id.page_OkHttp).setOnClickListener(v -> page_OkHttp());
    }

    private void page_Exoplayer2() {
        showActivity(TestExoplayer2MainActivity.class);
    }

    private void page_OkHttp(){
        showActivity(TestOkHttpActivity.class);
    }
}
