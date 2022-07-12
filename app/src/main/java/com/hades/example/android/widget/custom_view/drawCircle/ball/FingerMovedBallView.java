package com.hades.example.android.widget.custom_view.drawCircle.ball;

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
 * 难点：onTouchEvent 触摸事件；每隔0.1秒重新绘制一次
 */
public class FingerMovedBallView extends View {
    private static final String TAG = "FingerMovedBallView";

    private float currentX = getResources().getDimension(R.dimen.size_15);
    private float currentY = getResources().getDimension(R.dimen.size_15);
    private float radius = getResources().getDimension(R.dimen.size_10);

    private int width = -1;
    private int height = -1;

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
            // 创建画笔，颜色为红色
            paint = new Paint();
            paint.setColor(Color.RED);
            // 设置消除锯齿
            paint.setAntiAlias(true);
        }
    }

    private void initBallStartPosition() {
        if (-1 == currentX) {
            // onDraw 中获取宽度和高度，并1/2 长度作为小球的开始位置
            currentX = (float) getWidth() / 2;
        }
        if (-1 == currentY) {
            currentY = (float) getHeight() / 2;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 设置画布的颜色
        canvas.drawColor(getResources().getColor(R.color.paint_bg, getContext().getTheme()));

        // Log.d(TAG, "onDraw: width=" + getWidth() + ",height=" + getHeight());
        // initBallStartPosition();

        reviseXY();

        // 绘制圆形小球
        canvas.drawCircle(currentX, currentY, radius, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Log.d(TAG, "onTouchEvent: " + event.getAction());
        Log.d(TAG, "onTouchEvent: width=" + getWidth() + ",height=" + getHeight());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onTouchEvent: Action Down");
                // 记录触屏坐标，并把保存
                currentX = event.getX();
                currentY = event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onTouchEvent: Action Move");
                // 记录触屏坐标，并把保存
                currentX = event.getX();
                currentY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "onTouchEvent: Action Up");
                break;
        }
        // 通知重绘： 用新的坐标重新绘制小球  => 调用invalidate()后，会自动调用onDraw()
        invalidate();
        return true;
    }

    // 纠正XY，不让小球跑到屏幕范围外
    private void reviseXY() {
        if (-1 == width) {
            width = getWidth();
        }
        if (-1 == height) {
            height = getHeight();
        }

        if (currentX <= radius) {
            currentX = radius;
        } else if (currentX >= (width - radius)) {
            currentX = width - radius;
        }

        if (currentY <= radius) {
            currentY = radius;
        } else if (currentY >= (height - radius)) {
            currentY = height - radius;
        }
    }
}
