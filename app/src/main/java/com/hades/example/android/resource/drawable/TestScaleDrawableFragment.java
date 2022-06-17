package com.hades.example.android.resource.drawable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hades.example.android.R;

public class TestScaleDrawableFragment extends Fragment {
    private static final String TAG = TestScaleDrawableFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.res_drawable_scale, container, false);
        setImageView(view, R.id.imageviewLevel_1, 1);
        setImageView(view, R.id.imageviewLevel_10000, 10000);

//        setImageView(view, R.id.top);
//        setImageView(view, R.id.bottom);
//        setImageView(view, R.id.left);
//        setImageView(view, R.id.right);
//
//        setImageView(view, R.id.center_vertical);
//        setImageView(view, R.id.center_horizontal);
//        setImageView(view, R.id.center);
//
//        setImageView(view, R.id.fill_vertical);
//        setImageView(view, R.id.fill_horizontal);
//        setImageView(view, R.id.fill);
//
//        setImageView(view, R.id.clip_vertical);
//        setImageView(view, R.id.clip_horizontal);
        return view;
    }

    private void setImageView(View view, @IdRes int id, int level) {
        ImageView imageView = view.findViewById(id);
        imageView.setImageLevel(level);
    }
}