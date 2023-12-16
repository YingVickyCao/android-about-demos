package com.hades.example.android.tools;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

public class TestDensityUtil {
    private static final String TAG = TestDensityUtil.class.getSimpleName();

    /**
     * scale=2.25
     * density=2.25
     * scaledDensity=2.25
     * with,px=1600,dp=711,px=1600
     * height,px=2452,dp=1090
     * densityDpi=360
     * xdpi=286.449
     * ydpi=286.197
     */
    public void temp(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        double scale = DensityUtil.scale(context);
//        Log.e(TAG, "temp: scale=" + scale);
        Log.e(TAG, "temp: density=" + dm.density);
        Log.e(TAG, "temp: scaledDensity=" + dm.scaledDensity);

        Log.e(TAG, "temp:dm.densityDpi with,px=" + dm.widthPixels + ",dp=" + DensityUtil.px2dp(context, dm.widthPixels) + ",px=" + DensityUtil.dp2px(context, 711));
        Log.e(TAG, "temp: height,px=" + dm.heightPixels + ",dp=" + DensityUtil.px2dp(context, dm.heightPixels));

        Log.e(TAG, "temp: densityDpi=" + dm.densityDpi);
        Log.e(TAG, "temp: xdpi=" + dm.xdpi);
        Log.e(TAG, "temp: ydpi=" + dm.ydpi);
    }
}