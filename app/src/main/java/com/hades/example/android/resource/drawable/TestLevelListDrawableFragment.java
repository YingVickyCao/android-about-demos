package com.hades.example.android.resource.drawable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.hades.example.android.R;
import com.hades.example.android.base.BaseFragment;

public class TestLevelListDrawableFragment extends BaseFragment {

    private ImageView img_1_1;
    private ImageView img_1_2;
    private ImageView img_1_3;

    private ImageView img_2_1;
    private ImageView img_2_2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.res_drawable_level_iist, container, false);

        img_1_1 = view.findViewById(R.id.image_1_1);
        img_1_2 = view.findViewById(R.id.image_1_2);
        img_1_3 = view.findViewById(R.id.image_1_3);

        img_2_1 = view.findViewById(R.id.image_2_1);
        img_2_2 = view.findViewById(R.id.image_2_2);

        view.findViewById(R.id.setMaxLevel0).setOnClickListener(v -> setMaxLevel0());
        view.findViewById(R.id.setMaxLevel1).setOnClickListener(v -> setMaxLevel1());
        return view;
    }

    private void setMaxLevel0() {
        setLevel(0);
    }

    private void setMaxLevel1() {
        setLevel(1);
    }

    private void setLevel(int level) {
        img_1_1.setImageLevel(level);
        img_1_2.setImageLevel(level);
        img_1_3.setImageLevel(level);

        img_2_1.setImageLevel(level);
        img_2_2.setImageLevel(level);
    }
}