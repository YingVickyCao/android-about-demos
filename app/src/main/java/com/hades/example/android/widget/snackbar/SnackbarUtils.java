package com.hades.example.android.widget.snackbar;

import android.annotation.TargetApi;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;
import com.hades.example.android.R;
import com.hades.example.android.lib.utils.ThemeUtils;
import com.hades.example.android.tools.DensityUtil;

public class SnackbarUtils {
    //工具类当前持有的Snackbar实例
    private static Snackbar mSnackbar = null;

    public SnackbarUtils(@NonNull Snackbar snackbar) {
        this.mSnackbar = snackbar;
    }

    /**
     * 获取 mSnackbar
     *
     * @return
     */
    public Snackbar getSnackbar() {
        return mSnackbar;
    }

    /**
     * 设置Snackbar显示的位置
     *
     * @param gravity
     */
    public SnackbarUtils gravityFrameLayout(int gravity) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mSnackbar.getView().getLayoutParams().width, mSnackbar.getView().getLayoutParams().height);
        params.gravity = gravity;
        mSnackbar.getView().setLayoutParams(params);
        return new SnackbarUtils(mSnackbar);
    }

    /**
     * 设置Snackbar显示的位置,当Snackbar和CoordinatorLayout组合使用的时候
     *
     * @param gravity
     */
    public SnackbarUtils gravityCoordinatorLayout(int gravity) {
        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(mSnackbar.getView().getLayoutParams().width, mSnackbar.getView().getLayoutParams().height);
        params.gravity = gravity;
        mSnackbar.getView().setLayoutParams(params);
        return new SnackbarUtils(mSnackbar);
    }



    /**
     * 计算单行的Snackbar的高度值(单位 pix)
     *
     * @return
     */
    private int calculateSnackBarHeight() {
        int SnackbarHeight = DensityUtil.dp2px(mSnackbar.getView().getContext(), 28) + ThemeUtils.sp2px(mSnackbar.getView().getContext(), 14);
        Log.e("Jet", "直接获取MessageView高度:" + mSnackbar.getView().findViewById(R.id.snackbar_text).getHeight());
        return SnackbarHeight;
    }

    /**
     * 设置Snackbar显示在指定View的上方
     * 注:暂时仅支持单行的Snackbar,因为{@link SnackbarUtils#calculateSnackBarHeight()}暂时仅支持单行Snackbar的高度计算
     *
     * @param targetView     指定View
     * @param contentViewTop Activity中的View布局区域 距离屏幕顶端的距离
     * @param marginLeft     左边距
     * @param marginRight    右边距
     * @return
     */
    public SnackbarUtils above(View targetView, int contentViewTop, int marginLeft, int marginRight) {
        marginLeft = marginLeft <= 0 ? 0 : marginLeft;
        marginRight = marginRight <= 0 ? 0 : marginRight;
        int[] locations = new int[2];
        targetView.getLocationOnScreen(locations);
        Log.e("Jet", "距离屏幕左侧:" + locations[0] + "==距离屏幕顶部:" + locations[1]);
        int snackbarHeight = calculateSnackBarHeight();
        Log.e("Jet", "Snackbar高度:" + snackbarHeight);
        //必须保证指定View的顶部可见 且 单行Snackbar可以完整的展示
        if (locations[1] >= contentViewTop + snackbarHeight) {
            gravityFrameLayout(Gravity.BOTTOM);
            ViewGroup.LayoutParams params = mSnackbar.getView().getLayoutParams();
            ((ViewGroup.MarginLayoutParams) params).setMargins(marginLeft, 0, marginRight, mSnackbar.getView().getResources().getDisplayMetrics().heightPixels - locations[1]);
            mSnackbar.getView().setLayoutParams(params);
        }
        return new SnackbarUtils(mSnackbar);
    }

    /**
     * 设置Snackbar显示在指定View的下方
     * 注:暂时仅支持单行的Snackbar,因为{@link SnackbarUtils#calculateSnackBarHeight()}暂时仅支持单行Snackbar的高度计算
     *
     * @param targetView     指定View
     * @param contentViewTop Activity中的View布局区域 距离屏幕顶端的距离
     * @param marginLeft     左边距
     * @param marginRight    右边距
     * @return
     */
    public SnackbarUtils bellow(View targetView, int contentViewTop, int marginLeft, int marginRight) {
        marginLeft = marginLeft <= 0 ? 0 : marginLeft;
        marginRight = marginRight <= 0 ? 0 : marginRight;
        int[] locations = new int[2];
        targetView.getLocationOnScreen(locations);
        int snackbarHeight = calculateSnackBarHeight();
        int screenHeight = ThemeUtils.getScreenHeight(mSnackbar.getView().getContext());
        //必须保证指定View的底部可见 且 单行Snackbar可以完整的展示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //为什么要'+2'? 因为在Android L(Build.VERSION_CODES.LOLLIPOP)以上,例如Button会有一定的'阴影(shadow)',阴影的大小由'高度(elevation)'决定.
            //为了在Android L以上的系统中展示的Snackbar不要覆盖targetView的阴影部分太大比例,所以人为减小2px的layout_marginBottom属性.
            if (locations[1] + targetView.getHeight() >= contentViewTop && locations[1] + targetView.getHeight() + snackbarHeight + 2 <= screenHeight) {
                gravityFrameLayout(Gravity.BOTTOM);
                ViewGroup.LayoutParams params = mSnackbar.getView().getLayoutParams();
                ((ViewGroup.MarginLayoutParams) params).setMargins(marginLeft, 0, marginRight, screenHeight - (locations[1] + targetView.getHeight() + snackbarHeight + 2));
                mSnackbar.getView().setLayoutParams(params);
            }
        } else {
            if (locations[1] + targetView.getHeight() >= contentViewTop && locations[1] + targetView.getHeight() + snackbarHeight <= screenHeight) {
                gravityFrameLayout(Gravity.BOTTOM);
                ViewGroup.LayoutParams params = mSnackbar.getView().getLayoutParams();
                ((ViewGroup.MarginLayoutParams) params).setMargins(marginLeft, 0, marginRight, screenHeight - (locations[1] + targetView.getHeight() + snackbarHeight));
                mSnackbar.getView().setLayoutParams(params);
            }
        }
        return new SnackbarUtils(mSnackbar);
    }


    /**
     * 显示 mSnackbar
     */
    public void show() {
        if (mSnackbar != null) {
            mSnackbar.show();
        }
    }
}
