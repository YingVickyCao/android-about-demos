package com.hades.example.android.widget.custom_view.drawCircle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.hades.example.android.R;

/**
 * paint.setStyle(Paint.Style.STROKE)
 */
public class Circle4Stroke extends View {
    Paint paint;

    public Circle4Stroke(Context context, AttributeSet set) {
        super(context, set);
        paint = new Paint();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.BLACK);

        paint.setAntiAlias(true); // 去锯齿
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);

        // 绘制圆形
        int radius = (int) getResources().getDimension(R.dimen.size_50);
        canvas.drawCircle(radius * 2, radius * 2, radius, paint);
    }
}