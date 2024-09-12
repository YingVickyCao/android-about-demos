package com.hades.example.android;

import android.os.Bundle;
import android.view.View;

import com.hades.example.android._feature.FeatureActivity;
import com.hades.example.android.app_component._activity._children.TestPreferenceActivity;
import com.hades.example.android.app_component.broadcast.normal.TestNormalBroadcastActivity;
import com.hades.example.android.app_component.broadcast.ordered.TestOrderedBroadcastActivity;
import com.hades.example.android.base.BaseActivity;
import com.hades.example.android.data_storage.DataStorageActivity;
import com.hades.example.android.gps.TestGpsActivity;
import com.hades.example.android.other_ui.OtherUIActivity;
import com.hades.example.android.qa.QAActivity;
import com.hades.example.android.test_libs.TestLibsActivity;
import com.hades.example.android.po.security.SecurityActivity;
import com.hades.example.android.resource.ResourceActivity;
import com.hades.example.android.widget.WidgetActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.pageSecurity).setOnClickListener(v -> pageSecurity());
        findViewById(R.id.pageWidget).setOnClickListener(v -> pageWidget());
        findViewById(R.id.pageDateStorage).setOnClickListener(v -> pageDateStorage());
        findViewById(R.id.pageResource).setOnClickListener(v -> pageResource());
        findViewById(R.id.page_Libs).setOnClickListener(v -> page_Libs());
        findViewById(R.id.pageOtherUI).setOnClickListener(v -> pageOtherUI());
        findViewById(R.id.page_Feature).setOnClickListener(v -> page_Feature());
        findViewById(R.id.pageQAAboutView).setOnClickListener(v -> pageQAAboutView());
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

    private void pageOtherUI() {
        showActivity(OtherUIActivity.class);
    }
    private void page_Feature() {
        showActivity(FeatureActivity.class);
    }

    private void pageQAAboutView() {
        showActivity(QAActivity.class);
    }
}
