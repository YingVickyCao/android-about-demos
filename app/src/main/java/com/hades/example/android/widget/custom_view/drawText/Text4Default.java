package com.hades.example.android.widget.custom_view.drawText;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * View 的大小 是 800x400
 * 设置baseline的起点坐标为（200，200），是开始文字开始绘制的地方
 */
public class Text4Default extends View {
    private Paint paint;

    public Text4Default(Context context, AttributeSet set) {
        super(context, set);
        paint = new Paint();

        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(120);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.BLACK);
        paint.setColor(Color.RED);

        /**
         * baseline：（200，200）
         */
        int baseline_x = 200;
        int baseline_y = 200;
        canvas.drawText("fGgA", baseline_x, baseline_y, paint);

        paint.setColor(Color.GREEN);
        canvas.drawLine(baseline_x, baseline_y, getWidth(), baseline_y, paint);
    }
}