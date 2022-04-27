package com.hades.example.android.widget.custom_view.ball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.hades.example.android.R;

/**
 * 小球跟着手指动
 */
public class FingerMovedBallView extends View {
    private static final String TAG = "FingerMovedBallView";
    private float currentX = -1;
    private float currentY = -1;
    Paint paint;

    public FingerMovedBallView(Context context) {
        super(context);
        initViews();
    }

    public FingerMovedBallView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public FingerMovedBallView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    public FingerMovedBallView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews();
    }

    private void initViews() {
        if (null == paint) {
            paint = new Paint();
            paint.setColor(Color.RED);
        }
    }

    private void initBallStartPosition() {
//        if (-1 == currentX) {
//            // onDraw 中获取宽度和高度，并1/2 长度作为小球的开始位置
//            currentX = (float) getWidth() / 2;
//        }
//        if (-1 == currentY) {
//            currentY = (float) getHeight() / 2;
//        }

        if (-1 == currentX) {
            currentX = getResources().getDimension(R.dimen.size_15);
        }
        if (-1 == currentY) {
            currentY = getResources().getDimension(R.dimen.size_15);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(getResources().getColor(R.color.paint_bg, getContext().getTheme()));

        Log.d(TAG, "onDraw: width=" + getWidth() + ",height=" + getHeight());
        initBallStartPosition();

        float radius = getResources().getDimension(R.dimen.size_10);
        /**
         * 绘制圆形小球
         */
        canvas.drawCircle(currentX, currentY, radius, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: " + event.getAction());
        /**
         * 获取当前手指触摸的X 和 Y 坐标位置，并把保存
         */
        currentX = event.getX();
        currentY = event.getY();

        /**
         * 通知重绘： 让小球绘制到当前手指触摸的X 和 Y 坐标
         */
        invalidate();
        return true;
    }
}
