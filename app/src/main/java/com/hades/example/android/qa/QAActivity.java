package com.hades.example.android.qa;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android.R;
import com.hades.example.android.base.DummyContentFragment;
import com.hades.example.android.widget.view_animator.viewflipper.ViewFlipperFragment;
import com.hades.utility.jvm.DummyItem;

import java.util.ArrayList;


public class QAActivity extends AppCompatActivity {
    private static final String TAG = "QAActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qa);
//        addFragment();
//        addFragment2();

        findViewById(R.id.test).setOnClickListener(v -> test());
        findViewById(R.id.test2).setOnClickListener(v -> test2());
    }

    private void addFragment() {
        ArrayList<DummyItem> list = new ArrayList<>();

        for (int i = 0; i <= 100; i++) {
            list.add(new DummyItem(i, String.valueOf(i), i));
        }
        getSupportFragmentManager().beginTransaction().add(R.id.root, DummyContentFragment.getInstance(list), "test").commit();
    }

    private void addFragment2() {
//        getSupportFragmentManager().beginTransaction().add(R.id.root, new AdapterViewFlipper3Fragment(), "test").commit();
        getSupportFragmentManager().beginTransaction().add(R.id.root, new ViewFlipperFragment(), "test").commit();

    }

    private void test() {
        DefaultUIConfigure configure = new DefaultUIConfigure();
        configure.setDefaultAlertConfigure(new DefaultAlertConfigure());
        configure.getDefaultAlertConfigure().setTitle("custom title");

        AlertParamGetter getter = new AlertParamGetter(this, configure);
        AlertParam alertParam = getter.getAlertParam();
        String title = alertParam.getTitle();
        Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
    }

    private void test2() {
        AlertParamGetter getter = new AlertParamGetter(this, null);
        AlertParam alertParam = getter.getAlertParam();
        String title = alertParam.getTitle();
        Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
    }
}