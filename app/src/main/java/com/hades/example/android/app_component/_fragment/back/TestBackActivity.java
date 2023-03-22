package com.hades.example.android.app_component._fragment.back;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

import com.hades.example.android.R;
import com.hades.example.android.tools.FragmentUtils;

/**
 * onBackPressed() is depressed, use OnBackPressedCallback 代替 onBackPressed()。
 * 测试结果：
 * If Fragment 有addCallback OnBackPressedCallback，若 Fragment 不处理，最终后activity 会处理 back。
 * If Fragment 没有addCallback OnBackPressedCallback，若 最终后activity 会处理 back。
 *
 * If Activity 有addCallback OnBackPressedCallback，同时 有onBackPressed()，若 最终后activity 先执行 onBackPressed() 再执行OnBackPressedCallback - handleOnBackPressed 。
 * If Activity 只有addCallback OnBackPressedCallback，没有onBackPressed()，若 最终后activity 执行OnBackPressedCallback - handleOnBackPressed 。
 * If Activity 没有addCallback OnBackPressedCallback，只有onBackPressed()，若 最终后activity 执行onBackPressed() 。
 */
public class TestBackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_test);

        findViewById(R.id.add_1).setOnClickListener(v -> FragmentUtils.replaceFragment(TestBackActivity.this, R.id.fragmentContainer, HandleBackFragment.newInstance(), HandleBackFragment.TAG));
        findViewById(R.id.add_2).setOnClickListener(v -> FragmentUtils.replaceFragment(TestBackActivity.this, "TestBack", R.id.fragmentContainer, NotHandleBackFragment.newInstance(),
                NotHandleBackFragment.TAG));

        // Way 2 :

//        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
//            @Override
//            public void handleOnBackPressed() {
//                FragmentUtils.popBackStack(TestBackActivity.this);
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer);
//                if (fragment instanceof IBack) {
//                    boolean flag = ((IBack) fragment).handleBack();
//                    if (flag) {
//                        // we have consumed the test
//                        this.setEnabled(true);
//                        return;
//                    }
//                }
//                this.setEnabled(false);
//                getOnBackPressedDispatcher().onBackPressed();
//            }
//        });
    }

//    @Override
//    public void onBackPressed() {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer);
//        if (fragment instanceof IBack) {
//            boolean flag = ((IBack) fragment).handleBack();
//            if (flag) {
//                return;
//            }
//        }
//        super.onBackPressed();
//    }
}