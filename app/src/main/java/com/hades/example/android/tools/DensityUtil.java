package com.hades.example.android.tools;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;

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

    // Android 获取屏幕宽度和高度 https://www.jianshu.com/p/1a931d943fb4

    public static int getWidthSize(Activity context) {
        Display defaultDisplay = context.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        return point.x;
    }

    public static int getRealWidthSize(Activity context) {
        Display defaultDisplay = context.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getRealSize(point);
        return point.x;
    }

    public static int getHeightSize(Activity context) {
        Display defaultDisplay = context.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        return point.y;
    }

    public static int getRealHeightSize(Activity context) {
        Display defaultDisplay = context.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getRealSize(point);
        return point.y;
    }
}
