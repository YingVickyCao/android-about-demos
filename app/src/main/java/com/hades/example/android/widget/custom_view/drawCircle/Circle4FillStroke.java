package com.hades.example.android.widget.custom_view.drawCircle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class Circle4FillStroke extends View {
    Paint paint;

    public Circle4FillStroke(Context context, AttributeSet set) {
        super(context, set);
        paint = new Paint();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setAntiAlias(true); // 去锯齿
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(10); // px
        // 绘制圆形
        canvas.drawCircle(500, 500, 400, paint);

        paint.reset();

        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(100);
        canvas.drawCircle(500, 500, 400, paint);
    }

}
