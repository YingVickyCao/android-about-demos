package com.hades.example.android.widget.custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.hades.example.android.R;

public class RectWithBorder extends View {
    private static final String TAG = RectWithBorder.class.getSimpleName();

    Paint paint;

    public RectWithBorder(Context context, AttributeSet set) {
        super(context, set);
        paint = new Paint();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d(TAG, "draw:" + "left=" + getLeft() + ",top=" + getTop() + ",right=" + getRight() + ",bottom=" + getBottom());

        canvas.drawColor(Color.BLACK);

        paint.setAntiAlias(true);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(getResources().getDimension(R.dimen.paint_stroke_width));

        int left = (int) getResources().getDimension(R.dimen.size_50);
        int top = (int) getResources().getDimension(R.dimen.size_25);
        int right = (int) getResources().getDimension(R.dimen.size_150);
        int bottom = (int) getResources().getDimension(R.dimen.size_75);
        int radius = (int) getResources().getDimension(R.dimen.paint_border_size);
        /**
         * drawRoundRect 绘制圆角的矩形，包括正方形、长方形
         */
        canvas.drawRoundRect(left, top, right, bottom, radius, radius, paint);
    }
}