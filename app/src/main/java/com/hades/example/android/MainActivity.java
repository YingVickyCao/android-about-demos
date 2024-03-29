package com.hades.example.android;

import android.os.Bundle;
import android.view.View;

import com.hades.example.android.base.BaseActivity;
import com.hades.example.android.data_storage.DataStorageActivity;
import com.hades.example.android.test_libs.TestLibsActivity;
import com.hades.example.android.po.security.SecurityActivity;
import com.hades.example.android.resource.ResourceActivity;
import com.hades.example.android.widget.WidgetActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        findViewById(R.id.pageSecurity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageResource();
            }
        });
        findViewById(R.id.pageWidget).setOnClickListener(v -> pageWidget());
        findViewById(R.id.pageDateStorage).setOnClickListener(v -> pageDateStorage());
        findViewById(R.id.pageResource).setOnClickListener(v -> pageResource());
        findViewById(R.id.page_Libs).setOnClickListener(v -> page_Libs());
        findViewById(R.id.pageQAAboutView).setOnClickListener(v -> pageQAAboutView());
        showCurrentTest();

    }

    @Override
    protected void showCurrentTest() {
        pageWidget();
    }

    private void pageSecurity() {
        showActivity(SecurityActivity.class);
    }

    private void pageWidget() {
        showActivity(WidgetActivity.class);
    }

    private void pageDateStorage() {
        showActivity(DataStorageActivity.class);
    }

    private void pageResource() {
        showActivity(ResourceActivity.class);
    }

    private void page_Libs() {
        showActivity(TestLibsActivity.class);
    }

    private void pageQAAboutView() {
        showActivity(QAActivity.class);
    }
}
