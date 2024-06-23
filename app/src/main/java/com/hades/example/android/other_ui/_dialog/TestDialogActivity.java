package com.hades.example.android.other_ui._dialog;


import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.hades.example.android.R;
import com.hades.example.android.base.BaseActivity;
import com.hades.utility.android.utils.BlockQuickTap;
import com.hades.example.android.widget.pickers.DateTimePickerDialogFragment;
import com.hades.example.android.other_ui._dialog.depressed.TestAlertDialogFragment;
import com.hades.example.android.other_ui._dialog.depressed.TestProgressDialogFragment;
import com.hades.example.android.widget.pickers.TimePickerDialogFragment;
import com.hades.example.android.other_ui._dialog.good.activity.DialogStyleActivity;
import com.hades.example.android.other_ui._dialog.good.fragment.HalfWidthDialogFragment;
import com.hades.example.android.other_ui._dialog.good.fragment.MyAlertDialogFragment;
import com.hades.example.android.other_ui._dialog.good.fragment.MyBaseDialogFragment;
import com.hades.example.android.other_ui._dialog.good.fragment.ShowAsDialogOrEmbeddedDialogFragment;
import com.hades.example.android.other_ui._dialog.good.fragment.TestBottomSheetDialogFragment;

public class TestDialogActivity extends BaseActivity implements MyAlertDialogFragment.NoticeDialogListener {
    private static final String TAG = TestDialogActivity.class.getSimpleName();

    boolean mIsLargeLayout;

    private TestBottomSheetDialogFragment bottomSheetDialogFragment;
    private String MOCK_BOTTOM_SHEET_BEAN_1 = "bean 1";
    private String MOCK_BOTTOM_SHEET_BEAN_2 = "bean 2";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_ui_dialog);

        mIsLargeLayout = getResources().getBoolean(R.bool.is_large_layout);

        initViews(R.id.root);

        findViewById(R.id.btn1).setOnClickListener(v -> showMyBaseDialogFragment(1));
        findViewById(R.id.btn2).setOnClickListener(v -> showMyBaseDialogFragment(2));
        findViewById(R.id.btn3).setOnClickListener(v -> showMyBaseDialogFragment(3));
        findViewById(R.id.btn4).setOnClickListener(v -> showMyBaseDialogFragment(4));
        findViewById(R.id.btn5).setOnClickListener(v -> showMyBaseDialogFragment(5));
        findViewById(R.id.btn6).setOnClickListener(v -> showMyBaseDialogFragment(6));
        findViewById(R.id.btn7).setOnClickListener(v -> showMyBaseDialogFragment(7));
        findViewById(R.id.btn8).setOnClickListener(v -> showMyBaseDialogFragment(8));
        findViewById(R.id.btn9).setOnClickListener(v -> showMyBaseDialogFragment(9));

        findViewById(R.id.page_AlertDialog_DialogFragment).setOnClickListener(v -> page_AlertDialog_DialogFragment());

//        findViewById(R.id.showAsDialogOrEmbeddedFragment).setOnClickListener(v -> showedAsDialogOrEmbeddedFragment());
        findViewById(R.id.showAsDialog).setOnClickListener(v -> showdAsDialog());
        findViewById(R.id.showAsEmbeddedFragment).setOnClickListener(v -> showAsEmbeddedFragment());

        findViewById(R.id.pageBottomSheetDialogFragment).setOnClickListener(v -> pageBottomSheetDialogFragment());
        findViewById(R.id.page_BottomSheetDialogFragment_mock_app_crash).setOnClickListener(v -> fixCrash());

        findViewById(R.id.pageDialogStyleActivity).setOnClickListener(v -> pageDialogStyleActivity());

        findViewById(R.id.page_AlertDialog).setOnClickListener(v -> pageAlertDialog());
        findViewById(R.id.pageProgressDialog).setOnClickListener(v -> pageProgressDialog());
        findViewById(R.id.pageHalfWidthDialogFragment).setOnClickListener(v -> pageHalfWidthDialogFragment());
    }

    @Override
    protected void onResume() {
        super.onResume();
        //        pageBottomSheetDialogFragment();
    }

    private void firstRemoveDialogFragment() {
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
    }

    void showMyBaseDialogFragment(int index) {
        firstRemoveDialogFragment();

        // Create and show the dialog.
        DialogFragment newFragment = MyBaseDialogFragment.newInstance(index);
        newFragment.show(getSupportFragmentManager(), "dialog");
    }

    private void page_AlertDialog_DialogFragment() {
        DialogFragment newFragment = new MyAlertDialogFragment();
        newFragment.show(getSupportFragmentManager(), "dialog"); // tag: findFragmentByTag()
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Toast.makeText(this, "onDialogPositiveClick", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Toast.makeText(this, "onDialogNegativeClick", Toast.LENGTH_SHORT).show();
    }

    private void showedAsDialogOrEmbeddedFragment() {
        firstRemoveDialogFragment();
        if (mIsLargeLayout) {
            // The device is using a large layout, so show the fragment as a dialog
            showdAsDialog();
        } else {
            // The device is smaller, so show the fragment fullscreen
            showAsEmbeddedFragment();
        }
    }

    private void showdAsDialog() {
        new ShowAsDialogOrEmbeddedDialogFragment().show(getSupportFragmentManager(), "dialog"); // be created and shown as a dialog
    }

    private void showAsEmbeddedFragment() {
        showFragment(new ShowAsDialogOrEmbeddedDialogFragment(), "dialog");                     // embedded : added as content in a view hierarchy
    }

    private void pageBottomSheetDialogFragment() {
//        showFragment(new TestBottomSheetDialogFragment()); // embedded : added as content in a view hierarchy
//        new TestBottomSheetDialogFragment().show(getSupportFragmentManager(), "dialog"); // be created and shown as a dialog

        fixCrash();
        fixCrash();
    }

    // Fixed:快速多次show dialog fragment，app crashed：java.lang.IllegalStateException: Fragment already added
    private void fixCrash() {
        // https://www.jianshu.com/p/1068f9f75fe4
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (null == bottomSheetDialogFragment) {
            Log.d(TAG, "fixCrash: new TestBottomSheetDialogFragment");
            bottomSheetDialogFragment = new TestBottomSheetDialogFragment();
            bottomSheetDialogFragment.setUniqueKey(MOCK_BOTTOM_SHEET_BEAN_1);
        } else {
//            Log.d(TAG, "fixCrash: setData-FragmentManager@" + fragmentManager.hashCode());
            if (bottomSheetDialogFragment.getUniqueKey().equalsIgnoreCase(MOCK_BOTTOM_SHEET_BEAN_1)) {
                // 判断dialog 是否正在showing
                if (bottomSheetDialogFragment.getDialog() != null && bottomSheetDialogFragment.getDialog().isShowing()) {
                    Log.d(TAG, "fixCrash: same data, already showing");
                    return;
                } else {
                    DialogFragment fragment = (DialogFragment) getSupportFragmentManager().findFragmentByTag(TestBottomSheetDialogFragment.TAG);
                    Log.d(TAG, "fixCrash:fragment@" + (null != fragment ? fragment.hashCode() : "null") + ",same data,but not showing");
                    if (BlockQuickTap.isRepeatShowDialog(bottomSheetDialogFragment.getUniqueKey())) {
                        Log.d(TAG, "fixCrash: isRepeatShowDialog");
                        return;
                    }
                    Log.d(TAG, "fixCrash: remove ");
                    getSupportFragmentManager().beginTransaction().remove(bottomSheetDialogFragment).commit();
                }
            } else {
                Log.d(TAG, "fixCrash: no data, added");
                bottomSheetDialogFragment.setUniqueKey(String.valueOf(MOCK_BOTTOM_SHEET_BEAN_2));
            }
        }
        bottomSheetDialogFragment.show(fragmentManager, TestBottomSheetDialogFragment.TAG);
    }

    private void pageHalfWidthDialogFragment() {
        new HalfWidthDialogFragment().show(getSupportFragmentManager(), "dialog");
    }

    private void pageDialogStyleActivity() {
        showActivity(DialogStyleActivity.class);
    }

    private void pageAlertDialog() {
        showFragment(new TestAlertDialogFragment());
    }

    private void pageProgressDialog() {
        showFragment(new TestProgressDialogFragment());
    }
}