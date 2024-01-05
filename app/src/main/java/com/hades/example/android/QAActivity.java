package com.hades.example.android;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android.app_component._fragment.back.TestBackActivity;
import com.hades.example.android.app_component.content_provider.system.media.GalleryActivity;
import com.hades.example.android.app_component.service.unbounservice.StartServiceTest1Activity;
import com.hades.example.android.base.DummyContentFragment;
import com.hades.example.android.widget.view_animator.adapterviewflipper.v2.AdapterViewFlipper2Fragment;
import com.hades.utility.jvm.DummyItem;

import java.util.ArrayList;


public class QAActivity extends AppCompatActivity {
    private static final String TAG = "QAActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qa);
//        addFragment();
        addFragment2();
    }

    private void addFragment() {
        ArrayList<DummyItem> list = new ArrayList<>();

        for (int i = 0; i <= 100; i++) {
            list.add(new DummyItem(i, String.valueOf(i), i));
        }
        getSupportFragmentManager().beginTransaction().add(R.id.root, DummyContentFragment.getInstance(list), "test").commit();
    }

    private void addFragment2() {
        getSupportFragmentManager().beginTransaction().add(R.id.root, new AdapterViewFlipper2Fragment(), "test").commit();

    }
}