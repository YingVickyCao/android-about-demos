package com.hades.example.android.app_component._fragment.back;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

import com.hades.example.android.R;
import com.hades.example.android.tools.FragmentUtils;

public class TestBackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_test);

        findViewById(R.id.add_1).setOnClickListener(v -> FragmentUtils.replaceFragment(TestBackActivity.this, R.id.fragmentContainer, HandleBackFragment.newInstance(), HandleBackFragment.TAG));
        findViewById(R.id.add_2).setOnClickListener(v -> FragmentUtils.replaceFragment(TestBackActivity.this, "TestBack", R.id.fragmentContainer, NotHandleBackFragment.newInstance(),
                NotHandleBackFragment.TAG));
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