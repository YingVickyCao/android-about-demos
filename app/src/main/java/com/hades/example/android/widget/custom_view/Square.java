package com.hades.example.android.widget.custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.hades.example.android.R;

public class Square extends View {
    Paint paint;

    public Square(Context context, AttributeSet set) {
        super(context, set);
        paint = new Paint();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.BLACK);

        paint.setAntiAlias(true);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(4);

        int height = (int) getResources().getDimension(R.dimen.size_50);

        /**
         * drawRect 绘制矩形，包括正方形、长方形
         */
        canvas.drawRect(height / 2, height / 2, height + height / 2, height / 2 + height, paint);
    }
}