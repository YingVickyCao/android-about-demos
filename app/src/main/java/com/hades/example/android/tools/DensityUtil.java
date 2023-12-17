package com.hades.example.android.tools;

import android.content.Context;
import android.util.DisplayMetrics;

public class DensityUtil {
    private static final String TAG = DensityUtil.class.getSimpleName();
    private static volatile DensityUtil mInstance;

    public static DensityUtil getInstance() {
        if (null == mInstance) {
            synchronized (DensityUtil.class) {
                if (null == mInstance) {
                    mInstance = new DensityUtil();
                }
            }
        }
        return mInstance;
    }

    public static float scale(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.densityDpi / 160F;
    }

    public static int dp2px(Context context, float dp) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
//        return (int) (dipValue * scale + 0.5f);
        return (int) (dp * dm.density + 0.5f);
    }

    public static int px2dp(Context context, float px) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
//        return (int) (pxValue / scale + 0.5f);
        return (int) (px / dm.density + 0.5f);
    }
}
