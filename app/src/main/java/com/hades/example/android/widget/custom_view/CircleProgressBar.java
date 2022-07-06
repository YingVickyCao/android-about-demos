package com.hades.example.android.widget.custom_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.hades.example.android.R;

/**
 * 难点：
 * 计算圆的radius
 * 计算弧的开始位置、结束位置
 * 计算字体的位置
 */
// https://www.cnblogs.com/kimmy/p/4833321.html
public class CircleProgressBar extends View {
    private static final String TAG = CircleProgressBar.class.getSimpleName();
    private Paint defaultPaint = new Paint();
    private Paint progressPaint = new Paint();
    private Paint textPaint = new Paint();
    private RectF oval = new RectF();

    public static final int RING = -1;            // 环形
    public static final int RING_WITH_TEXT = -2;   // 环形带文字
    public static final int RING_WITH_FAN = -3;   // 环形，有扇形填充
    public int mMode = RING;

    private int color_default;
    private int color_progress;
    private int color_text;
    private int thickness;
    private int textSize;
    private int progress = 0;

    public CircleProgressBar(Context context) {
        super(context);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.circleProgressStyle);

        // 默认圆
        defaultPaint.setColor(color_default);
        defaultPaint.setStyle(Paint.Style.STROKE);
        defaultPaint.setStrokeWidth(thickness);
        defaultPaint.setAntiAlias(true);

        // 进度
        progressPaint.setColor(color_progress);
        progressPaint.setStyle(mMode == RING_WITH_FAN ? Paint.Style.FILL_AND_STROKE : Paint.Style.STROKE);
        progressPaint.setStrokeWidth(thickness);
        progressPaint.setAntiAlias(true);

        // 文字
        textPaint.setStrokeWidth(0);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(color_text);
        textPaint.setTextSize(textSize);
        textPaint.setTypeface(Typeface.DEFAULT);
        textPaint.setAntiAlias(true);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgress, defStyleAttr, R.style.CircleProgressStyle);
        if (null != typedArray) {
            mMode = typedArray.getInteger(R.styleable.CircleProgress_mode, RING);
            thickness = typedArray.getDimensionPixelSize(R.styleable.CircleProgress_thickness, getResources().getDimensionPixelSize(R.dimen.size_2));
            color_default = typedArray.getColor(R.styleable.CircleProgress_color_default, Color.parseColor("#87A2BD"));
            color_progress = typedArray.getColor(R.styleable.CircleProgress_color_progress, Color.parseColor("#ff0000"));
            if (mMode == RING_WITH_TEXT) {
                color_text = typedArray.getColor(R.styleable.CircleProgress_color_text, Color.parseColor("#00ff00"));
                textSize = typedArray.getDimensionPixelSize(R.styleable.CircleProgress_android_textSize, getResources().getDimensionPixelSize(R.dimen.text_size_20));
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        Log.d(TAG, "onDraw: " + width);

        int cx = width / 2;
        int cy = width / 2;
        int radius = width / 2 - (thickness / 2);
        drawDefaultCircle(canvas, radius, cx, cy);

        if (progress >= 0) {
            drawProgress(canvas, radius, cx, cy);
        }
        if (mMode == RING_WITH_TEXT) {
            drawText(canvas, cx, cy);
        }
    }

    private void drawDefaultCircle(Canvas canvas, int radius, int cx, int cy) {
        canvas.drawCircle(cx, cy, radius, defaultPaint);
    }

    private void drawProgress(Canvas canvas, int radius, int cx, int cy) {
        oval.left = cx - radius;
        oval.top = cy - radius;
        oval.right = cx + radius;
        oval.bottom = cy + radius;
        // 开始位置：270度
        float startAngle = -90; // -90 / 270
        // 结束位置：progress[0 ～100]， 转换成 结束位置
        float sweepAngle = 360 * (progress) / 100;
        boolean useCenter = (mMode == RING_WITH_FAN);
        Log.d(TAG, "drawProgress:progress=" + progress + ",startAngle=" + startAngle + ",sweepAngle=" + sweepAngle);
        canvas.drawArc(oval, startAngle, sweepAngle, useCenter, progressPaint);
    }

    private void drawText(Canvas canvas, int cx, int centerY) {
        String text = progress + "%";
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        textPaint.setColor(color_text);
        textPaint.setTextAlign(Paint.Align.CENTER);
        int baseline = centerY + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        float x = cx;
        Log.d(TAG, "drawText: baseline=" + baseline);
        Log.d(TAG, "drawText: bottom=" + fontMetrics.bottom);
        Log.d(TAG, "drawText: top=" + fontMetrics.top);
        Log.d(TAG, "drawText: ascent=" + fontMetrics.ascent);
        Log.d(TAG, "drawText: descent=" + fontMetrics.descent);
        Log.d(TAG, "drawText: leading=" + fontMetrics.leading);
        Log.d(TAG, "drawText: y/2=" + centerY/2);
        canvas.drawText(text, x, baseline, textPaint);
    }

    /**
     * @param progress 0 ~ 100
     */
    public void setProgress(int progress) {
        if (this.progress < 0) {
            this.progress = 0;
        }
        if (this.progress == 100 && progress > 100) {
            return;
        }
        if (progress < 0) {
            return;
        }
        this.progress = progress;
        Log.d(TAG, "setProgress: " + progress);
        invalidate();
    }

    /**
     * @return 0 ~ 100
     */
    public int getProgress() {
        Log.d(TAG, "getProgress: " + progress);
        return progress;
    }
}