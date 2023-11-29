package com.hades.example.android.widget.textview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.hades.example.android.R;

public class CustomDrawableSizeAppCompatTextView extends AppCompatTextView {
    private static final String TAG = "CustomDrawableSizeAppCompatTextView";

    boolean isDrawableSizeDone = false;

    public CustomDrawableSizeAppCompatTextView(@NonNull Context context) {
        super(context);
//        init();
    }

    public CustomDrawableSizeAppCompatTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        init();
    }

    public CustomDrawableSizeAppCompatTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        init();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!isDrawableSizeDone) {
            init();
            isDrawableSizeDone = true;
        }
    }

    // https://blog.csdn.net/qq_35427437/article/details/80293655
    private void init() {
        Drawable[] compoundDrawables = getCompoundDrawables(); // compoundDrawables is always not null
        Log.d(TAG, "init: compoundDrawables size = " + compoundDrawables.length);
        Log.d(TAG, "init: compoundDrawables[0] = " + compoundDrawables[0]); // TextView_drawableLeft
        Log.d(TAG, "init: compoundDrawables[1] = " + compoundDrawables[1]); // TextView_drawableTop
        Log.d(TAG, "init: compoundDrawables[2] = " + compoundDrawables[2]); // TextView_drawableRight
        Log.d(TAG, "init: compoundDrawables[3] = " + compoundDrawables[3]); // TextView_drawableBottom

        Drawable leftDrawable = compoundDrawables[0];
        if (null != leftDrawable) {
            int icon_size = getResources().getDimensionPixelSize(R.dimen.size_50);
//            int icon_size = leftDrawable.getIntrinsicWidth();
            int height = getMeasuredHeight();
            int width = getMeasuredWidth();
//            Log.d(TAG, "init: icon_size=" + icon_size);
//            Log.d(TAG, "init: height=" + height);
//            Log.d(TAG, "init: getIntrinsicWidth=" + leftDrawable.getIntrinsicWidth());
//            Log.d(TAG, "init: getIntrinsicHeight=" + leftDrawable.getIntrinsicHeight());
//
//            int left = getResources().getDimensionPixelOffset(R.dimen.size_5);
//            int top = height / 2 - icon_size / 2;
//            int right = left + icon_size / 2;
//            int bottom = height - top - icon_size;
//

            // TODO: 2023/11/29  leftDrawable.setBounds
//            leftDrawable.setBounds(icon_size / 5, icon_size, icon_size / 5 + icon_size, icon_size * 2);
//            leftDrawable.setBounds(0, icon_size, icon_size / 5 + icon_size, icon_size * 2);
        }
    }
}
