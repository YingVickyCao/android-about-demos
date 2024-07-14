package com.hades.example.android.other_ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android.R;
import com.hades.example.android.base.BaseActivity;
import com.hades.example.android.base.ViewsVisibilityHelper;
import com.hades.example.android.other_ui._actionbar.TestActionBarActivity;
import com.hades.example.android.other_ui._dialog.TestDialogActivity;
import com.hades.example.android.other_ui._notification.TestNotificationFragment;
import com.hades.example.android.other_ui._popup_window.TestPopupWindowFragment;
import com.hades.example.android.other_ui._toast.TestToastFragment;
import com.hades.example.android.tools.FragmentUtils;
import com.hades.example.android.widget.WidgetActivity;
import com.hades.example.android.widget.list._recyclerview._dag_reorder_list.v2.screen_size.TestViewLocationFragment;

public class OtherUIActivity extends AppCompatActivity {
    ViewsVisibilityHelper visibilityHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_ui_layout);

        findViewById(R.id.pageNotification).setOnClickListener(v -> pageNotification());
        findViewById(R.id.pageToast).setOnClickListener(v -> pageToast());
        findViewById(R.id.pageActionBar).setOnClickListener(v -> pageActionBar());
        findViewById(R.id.pagePopupWindow).setOnClickListener(v -> pagePopupWindow());
        findViewById(R.id.pageDialog).setOnClickListener(v -> pageDialog());

        if (null == visibilityHelper) {
            visibilityHelper = new ViewsVisibilityHelper(findViewById(R.id.topic), findViewById(R.id.scrollView), findViewById(R.id.fragmentRoot));
        }
        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (visibilityHelper.isBtnsHiden()) {
                    FragmentUtils.remove(OtherUIActivity.this, R.id.fragmentRoot);
                    visibilityHelper.showBtns();
                } else {
                    finish();
                }
            }
        });
    }

    private void pageNotification() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestNotificationFragment(), TestNotificationFragment.class.getSimpleName());
    }

    private void pageToast() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestToastFragment(), TestToastFragment.class.getSimpleName());
    }

    private void pageActionBar() {
        startActivity(new Intent(this,TestActionBarActivity.class));
    }

    private void pagePopupWindow() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestPopupWindowFragment(), TestPopupWindowFragment.class.getSimpleName());
    }

    private void pageDialog() {
        startActivity(new Intent(this,TestDialogActivity.class));
    }
}