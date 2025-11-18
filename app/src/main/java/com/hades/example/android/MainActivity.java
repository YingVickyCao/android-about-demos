package com.hades.example.android;

import android.content.Intent;
import android.os.Bundle;

import com.hades.example.android._feature.FeatureActivity;
import com.hades.example.android._process_and_thread.TestBackgroundTasksActivity;
import com.hades.example.android.app_component.TestAppComponentActivity;
import com.hades.example.android.app_component.content_provider.dict.DictActivity;
import com.hades.example.android.app_component.service.boundservice.TestLocalBoundServiceActivity;
import com.hades.example.android.app_component.service.boundservice.TestRemoteBoundServiceActivity2;
import com.hades.example.android.app_component.service.unbounservice.StartServiceTest1Activity;
import com.hades.example.android.base.BaseActivity;
import com.hades.example.android.data_storage.DataStorageActivity;
import com.hades.example.android.other_ui.OtherUIActivity;
import com.hades.example.android.test_libs.TestLibsActivity;
import com.hades.example.android.po.security.SecurityActivity;
import com.hades.example.android.resource.ResourceActivity;
import com.hades.example.android.widget.WidgetActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.pageComponent).setOnClickListener(v -> pageComponent());
        findViewById(R.id.pageSecurity).setOnClickListener(v -> pageSecurity());
        findViewById(R.id.page_background_tasks).setOnClickListener(v -> page_background_tasks());
        findViewById(R.id.pageWidget).setOnClickListener(v -> pageWidget());
        findViewById(R.id.pageDateStorage).setOnClickListener(v -> pageDateStorage());
        findViewById(R.id.pageResource).setOnClickListener(v -> pageResource());
        findViewById(R.id.page_Libs).setOnClickListener(v -> page_Libs());
        findViewById(R.id.pageOtherUI).setOnClickListener(v -> pageOtherUI());
        findViewById(R.id.page_Feature).setOnClickListener(v -> page_Feature());
        findViewById(R.id.pageQAAboutView).setOnClickListener(v -> pageQAAboutView());

//        page_background_tasks();
        pageQAAboutView();
    }

    private void pageComponent() {
        showActivity(TestAppComponentActivity.class);
    }

    private void pageSecurity() {
        showActivity(SecurityActivity.class);
    }

    private void page_background_tasks() {
        startActivity(new Intent(this, TestBackgroundTasksActivity.class));
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

    private void pageOtherUI() {
        showActivity(OtherUIActivity.class);
    }

    private void page_Feature() {
        showActivity(FeatureActivity.class);
    }

    private void pageQAAboutView() {
        showActivity(StartServiceTest1Activity.class);
    }
}
