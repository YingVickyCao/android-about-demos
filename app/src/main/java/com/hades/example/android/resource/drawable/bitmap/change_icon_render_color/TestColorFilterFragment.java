package com.hades.example.android.resource.drawable.bitmap.change_icon_render_color;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.hades.example.android.R;
import com.hades.example.android.resource.drawable.vector.TestVectorDrawableFragment;

public class TestColorFilterFragment extends Fragment {
    private static final String TAG = TestVectorDrawableFragment.class.getSimpleName();

    private ImageView mTintImageView;
    private ImageView mColorFilterImageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.res_bitmap_drawable_color_filter, container, false);

        mTintImageView = view.findViewById(R.id.tintImageView);
        mColorFilterImageView = view.findViewById(R.id.colorFilterImageView);

        view.findViewById(R.id.red).setOnClickListener(v -> red());
        view.findViewById(R.id.clear).setOnClickListener(v -> clear());
        view.findViewById(R.id.green).setOnClickListener(v -> green());

        return view;
    }

    /**
     * 不同 ImageView 使用同一张图片，如何改变图片颜色 ？
     * Drawable.setColorFilter() > Drawable.setTint().
     * SVG
     * 前者，不影响其他ImageView。
     * 后者，有时其他ImageView，一块变色
     * <p>
     * PNG
     * 前者，不影响其他ImageView。
     * 后者，不影响其他ImageView。
     */
    private void red() {
        setColorFilter(android.R.color.holo_red_dark);
        setTint(android.R.color.holo_red_dark);
    }

    private void green() {
        setColorFilter(android.R.color.holo_green_dark);
        setTint(android.R.color.holo_green_dark);
    }

    private void clear() {
        clearColorFilter();
        clearTint();
    }

    private void setColorFilter(int color) {
//        mColorFilterImageView.setColorFilter(ContextCompat.getColor(getContext(), color));
        Log.d(TAG, "setColorFilter:before mutate @Drawable" + mColorFilterImageView.getDrawable().hashCode()); // mutate @Drawable196558238
        mColorFilterImageView.getDrawable().mutate();
        Log.d(TAG, "setColorFilter:after  mutate @Drawable" + mColorFilterImageView.getDrawable().hashCode()); // mutate @Drawable196558238
        mColorFilterImageView.setColorFilter(ContextCompat.getColor(getContext(), color));
    }

    private void setTint(int color) {
        mTintImageView.getDrawable().setTint(getResources().getColor(color));
    }

    private void clearColorFilter() {
        mColorFilterImageView.setColorFilter(null);
    }

    private void clearTint() {
        mTintImageView.getDrawable().setTintList(null);
    }

}