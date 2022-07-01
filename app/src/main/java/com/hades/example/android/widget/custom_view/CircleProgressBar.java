package com.hades.example.android.widget.custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.hades.example.android.R;

public class CircleProgressBar extends View {
    private static final String TAG = "CircleProgressBar";
    private Paint defaultPaint = new Paint();
    private Paint progressPaint = new Paint();
    private Paint textPaint = new Paint();
    private RectF oval = new RectF();

    private int MODE_STROKE = 0;            // 环形
    private int MODE_STROKE_AND_TEXT = 1;   // 环形带文字
    private int MODE_STROKE_AND_FILL = 2;   // 环形，有扇形填充

    private int mode = MODE_STROKE_AND_FILL;

    private String color_default = "#87A2BD";
    //    private String color_progress = "#0076D4";
    private String color_progress = "#ff0000";
    private String color_text = "#00ff00";
    private int thickness = getResources().getDimensionPixelSize(R.dimen.size_1);
    private int textSize = getResources().getDimensionPixelSize(R.dimen.text_size_20);
    private int progress = 0;

    public CircleProgressBar(Context context) {
        super(context);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        defaultPaint.setColor(Color.parseColor(color_default));
        defaultPaint.setStyle(Paint.Style.STROKE);
        defaultPaint.setStrokeWidth(thickness);
        defaultPaint.setAntiAlias(true);

        progressPaint.setColor(Color.parseColor(color_progress));
        if (mode == MODE_STROKE_AND_FILL){
            progressPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        }
        else {
            progressPaint.setStyle(Paint.Style.STROKE);
        }

        progressPaint.setStrokeWidth(thickness);
        progressPaint.setAntiAlias(true);

        textPaint.setStrokeWidth(0);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.parseColor(color_text));
        textPaint.setTextSize(textSize);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        Log.d(TAG, "onDraw: " + width);

        int radius = width / 2;
        int cx = radius;
        int cy = radius;
        drawDefaultCircle(canvas, radius, cx, cy);

        if (progress >= 0) {
            drawProgress(canvas, radius, cx, cy);
        }
        if (mode == MODE_STROKE_AND_TEXT) {
            drawText(canvas, radius, cx, cy);
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
        float startAngle = -90;
//        float startAngle = 270;
        float sweepAngle = 360 * (progress) / 100;
//        float sweepAngle =90;
        boolean useCenter = (mode == MODE_STROKE_AND_FILL);
        Log.d(TAG, "drawProgress:progress=" + progress + ",startAngle=" + startAngle + ",sweepAngle=" + sweepAngle);
        canvas.drawArc(oval, startAngle, sweepAngle, useCenter, progressPaint);
    }

    private void drawText(Canvas canvas, int radius, int cx, int cy) {
        Rect targetRect = new Rect(0, 0, getWidth(), getHeight());
        textPaint.setColor(Color.TRANSPARENT);
        canvas.drawRect(targetRect, textPaint);

        String text = progress + "%";
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        textPaint.setColor(Color.parseColor(color_text));
        textPaint.setTextAlign(Paint.Align.CENTER);
        int baseline = targetRect.centerY() - (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.top;
        // 优化后
//        int baseline = (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        float x = targetRect.centerX();
        float y = baseline;
        canvas.drawText(text, x, y, textPaint);
    }

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

    public int getProgress() {
        Log.d(TAG, "getProgress: " + progress);
        return progress;
    }
}