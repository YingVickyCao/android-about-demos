package com.hades.example.android._feature;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android.R;
import com.hades.example.android._feature._web_based_contents.r8.J2v8Fragment;
import com.hades.example.android._process_and_thread.workmanager.WorkManagerFragment;
import com.hades.example.android.base.ViewsVisibilityHelper;
import com.hades.example.android.tools.FragmentUtils;

public class FeatureActivity extends AppCompatActivity {
    ViewsVisibilityHelper visibilityHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature);

        findViewById(R.id.page_J2v8).setOnClickListener(v -> page_J2v8());
        findViewById(R.id.page_faq_penetrate_click).setOnClickListener(v -> page_faq_penetrate_click());
        findViewById(R.id.page_faq_player_controller_layer).setOnClickListener(v -> page_faq_player_controller_layer());
        findViewById(R.id.page_WorkManager).setOnClickListener(v -> page_WorkManager());

        if (null == visibilityHelper) {
            visibilityHelper = new ViewsVisibilityHelper(findViewById(R.id.topic), findViewById(R.id.scrollView), findViewById(R.id.fragmentRoot));
        }
        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (visibilityHelper.isBtnsHiden()) {
                    FragmentUtils.remove(FeatureActivity.this, R.id.fragmentRoot);
                    visibilityHelper.showBtns();
                } else {
                    finish();
                }
            }
        });
    }

    private void page_J2v8() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new J2v8Fragment(), J2v8Fragment.class.getSimpleName());
    }

    private void page_faq_player_controller_layer() {
        startActivity(new Intent(this, FaqPenetrateClickActivity.class));
    }

    private void page_faq_penetrate_click() {
        startActivity(new Intent(this, FaqPlayerControllerPlayerActivity.class));
    }

    private void page_WorkManager() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new WorkManagerFragment(), WorkManagerFragment.class.getSimpleName());
    }
}
