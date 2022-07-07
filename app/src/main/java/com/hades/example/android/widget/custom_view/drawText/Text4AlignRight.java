package com.hades.example.android.widget.custom_view.drawText;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * View 的大小 是 800x400
 * Paint.setTextAlign(Paint.Align.RIGHT);
 * XY (200，200）位于baseline的右边
 */
public class Text4AlignRight extends View {
    private Paint paint;

    public Text4AlignRight(Context context, AttributeSet set) {
        super(context, set);
        paint = new Paint();

        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(120);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.GRAY);
        paint.setColor(Color.BLACK);

        paint.setTextAlign(Paint.Align.RIGHT);
        /**
         * baseline：（200，200）
         */
        int baseline_x = 200;
        int baseline_y = 200;
        canvas.drawText("fGgA", baseline_x, baseline_y, paint);

        paint.setColor(Color.RED);
        canvas.drawLine(0, baseline_y, getWidth(), baseline_y, paint);

        paint.setColor(Color.GREEN);
        canvas.drawLine(baseline_x, 0, baseline_x, getHeight(), paint);
    }
}