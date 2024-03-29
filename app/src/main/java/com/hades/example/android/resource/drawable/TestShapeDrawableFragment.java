package com.hades.example.android.resource.drawable;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.hades.example.android.R;
import com.hades.example.android.base.BaseFragment;

public class TestShapeDrawableFragment extends BaseFragment {
    private static final String TAG = TestShapeDrawableFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.res_drawable_shape, container, false);
        view.findViewById(R.id.checkIntrinsicHeight).setOnClickListener(v -> checkIntrinsicHeight());
        return view;
    }

    private void checkIntrinsicHeight() {
        // 5dp -> px=20
        /**
         * 20,20,20
         * No matter Shape Drawable put in any drawable folder, 不进行放缩
         */
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.drawable_shape_4_divider_vertical, getContext().getTheme());
        Log.d(TAG, "checkIntrinsicHeight:drawable intrinsicHeight=" + drawable.getIntrinsicHeight());
        Log.d(TAG, "checkIntrinsicHeight:drawable minimumHeight=" + drawable.getMinimumHeight());
        Log.d(TAG, "checkIntrinsicHeight:R.dimen.size_5= " + getResources().getDimension(R.dimen.size_5));
    }
}
