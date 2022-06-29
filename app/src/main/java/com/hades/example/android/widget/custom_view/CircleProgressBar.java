package com.hades.example.android.widget.custom_view;

import android.content.Context;
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

public class CircleProgressBar extends View {
    private static final String TAG = "CircleProgressBar";
    private Paint paint = new Paint();
    private RectF oval = new RectF();

    private int MODE_STROKE = 0;            // 环形
    private int MODE_STROKE_AND_TEXT = 1;   // 环形带文字
    private int MODE_STROKE_AND_FILL = 2;   // 环形，有扇形填充

    private int mode = MODE_STROKE_AND_TEXT;

    private String color_default = "#87A2BD";
    //    private String color_progress = "#0076D4";
    private String color_progress = "#ff0000";
    private String color_text = "#00ff00";
    private int thickness = getResources().getDimensionPixelSize(R.dimen.size_1);
    private int textSize = getResources().getDimensionPixelSize(R.dimen.text_size_30);
    private int progress = 50;

    public CircleProgressBar(Context context) {
        super(context);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.parseColor(color_default));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(thickness);
        paint.setAntiAlias(true);
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
        canvas.drawCircle(cx, cy, radius, paint);
        drawDefaultCircle(canvas, radius, cx, cy);

        if (progress > 0) {
            drawProgress(canvas, radius, cx, cy);
        }
        if (mode == MODE_STROKE_AND_TEXT) {
            drawText(canvas, radius, cx, cy);
        }
    }

    private void drawDefaultCircle(Canvas canvas, int radius, int cx, int cy) {
        canvas.drawCircle(cx, cy, radius, paint);
    }

    private void drawProgress(Canvas canvas, int radius, int cx, int cy) {
        paint.setColor(Color.parseColor(color_progress));
        oval.left = cx - radius;
        oval.top = cy - radius;
        oval.right = cx + radius;
        oval.bottom = cy + radius;
//        float startAngle = -90;
        float startAngle = 270;
        float sweepAngle = 360 * progress / 100;
//        float sweepAngle =90;
        boolean useCenter = false;
        canvas.drawArc(oval, startAngle, sweepAngle, useCenter, paint);
    }

    private void drawText(Canvas canvas, int radius, int cx, int cy) {
        paint.setStrokeWidth(0);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.parseColor(color_text));
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.DEFAULT_BOLD);

        String text = progress + "%";
        float textHalfWidth = paint.measureText(text) * 0.5f;
        float x = cx - textHalfWidth;
        float y = cy + textHalfWidth;
        canvas.drawText(text, x, y, paint);
    }
}