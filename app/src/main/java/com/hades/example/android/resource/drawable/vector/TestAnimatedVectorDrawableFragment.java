package com.hades.example.android.resource.drawable.vector;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hades.example.android.R;

// 矢量动画
public class TestAnimatedVectorDrawableFragment extends Fragment {
    private static final String TAG = "TestAnimatedVectorDrawableFragment";

    private ImageView imageView_changePath;
    private ImageView imageView_changeColor;
    private ImageView imageView_rotate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.res_drawable_animated_vector_drawble, container, false);

        imageView_changePath = view.findViewById(R.id.imageView_changePath);
        imageView_changeColor = view.findViewById(R.id.imageView_changeColor);
        imageView_rotate = view.findViewById(R.id.imageView_rotate);

        view.findViewById(R.id.startAnimation).setOnClickListener(v -> startAnimation());
        view.findViewById(R.id.stop_reset_Animation).setOnClickListener(v -> stop_reset_Animation());
        return view;
    }

    private void startAnimation() {
        startAnimation(imageView_changePath);
        startAnimation(imageView_changeColor);
        startAnimation(imageView_rotate);
    }

    private void startAnimation(ImageView imageView) {
        if (null != imageView && null != imageView.getDrawable() && imageView.getDrawable() instanceof AnimatedVectorDrawable) {
            AnimatedVectorDrawable drawable = (AnimatedVectorDrawable) imageView.getDrawable();
            drawable.start();
        }
    }

    private void stop_reset_Animation() {
        stop_reset_Animation(imageView_changePath);
        stop_reset_Animation(imageView_changeColor);
        stop_reset_Animation(imageView_rotate);
    }

    private void stop_reset_Animation(ImageView imageView) {
        if (null != imageView && null != imageView.getDrawable() && imageView.getDrawable() instanceof AnimatedVectorDrawable) {
            AnimatedVectorDrawable drawable = (AnimatedVectorDrawable) imageView.getDrawable();
            drawable.stop();
            drawable.reset();
        }
    }
}
