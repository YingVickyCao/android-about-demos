package com.hades.example.android.resource.dimension;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DimenRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hades.example.android.R;

/**
 * https://www.cnblogs.com/touko/p/6478851.html
 */
public class TestDimensionFragment extends Fragment {
    private static final String TAG = TestDimensionFragment.class.getSimpleName();

    private float mPx;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.res_dimension, container, false);
        view.findViewById(R.id.px2dp).setOnClickListener(v -> px2dp());
        view.findViewById(R.id.dp2px).setOnClickListener(v -> dp2px());
        view.findViewById(R.id.test_dimen).setOnClickListener(v -> test_dimen());
        return view;
    }

    private void px2dp() {
        float scale = getContext().getResources().getDisplayMetrics().density;
        float dp = mPx / scale; // 100.0
        Log.d(TAG, "px2dp: dp = " + dp);
    }

    private void dp2px() {
        float px = getResources().getDimension(R.dimen.size_100); // 262.5
        mPx = px;
        Log.d(TAG, "dp2px: px = " + px);

        float px2 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, px, getContext().getResources().getDisplayMetrics()); // 262.5
        Log.d(TAG, "dp2px: px2 = " + px2);

        float scale = getContext().getResources().getDisplayMetrics().density;
        float px3 = (100f * scale); // 262.5
        Log.d(TAG, "dp2px: px3 = " + px3);

    }

    private void test_dimen() {
        /**
         * 1 getDimension 返回值是float。
         * getDimensionPixelSize 返回值是int，四舍五入
         * getDimensionPixelOffset 返回值是int，只保留整数部分。
         *
         * 2 getDimension，getDimensionPixelSize，getDimensionPixelOffset 返回为实际值。
         * 若为dp/sp，则返回xml中定义的value * 屏幕密度。
         * 若为px，则返回xml中定义的value。
         */
        // dp->3.5,4.0,3.0
        test_dimen("dp", R.dimen.dp_01);
        // sp->3.5,4.0,3.0
        test_dimen("sp", R.dimen.sp_01);
        // px->1.0,1.0,1.0
        test_dimen("px", R.dimen.px_01);
    }

    private void test_dimen(String print, @DimenRes int id) {
        float valueOfDimension = getResources().getDimension(id);
        float valueOfDimensionPixelSize = getResources().getDimensionPixelSize(id);
        float valueOfDimensionPixelOffset = getResources().getDimensionPixelOffset(id);
        Log.d(TAG, "test_dimen: " + print + "->" + valueOfDimension + "," + valueOfDimensionPixelSize + "," + valueOfDimensionPixelOffset);
    }
}
