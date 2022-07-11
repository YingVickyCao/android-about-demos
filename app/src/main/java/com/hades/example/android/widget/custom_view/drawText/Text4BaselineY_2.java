package com.hades.example.android.widget.custom_view.drawText;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.hades.example.android.R;

/**
 * View 的大小 是 800x400
 * 通过 绘制文字最小矩形 得出的 Baseline Y
 */
public class Text4BaselineY_2 extends View {
    private static final String TAG = Text4BaselineY_2.class.getSimpleName();

    private Paint paint;
    private String text = getResources().getString(R.string.drawText_text);

    public Text4BaselineY_2(Context context, AttributeSet set) {
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

        float center_X = getWidth() / 2;
        int center_Y = getHeight() / 2;

        float baseline_X = center_X - paint.measureText(text) / 2;
        float baseline_Y = baseline_Y(center_Y);
        /**
         * baseline_y=164.0
         */
        Log.d(TAG, "onDraw:baseline_y=" + baseline_Y);

        canvas.drawText(text, baseline_X, baseline_Y, paint);
    }

    private float baseline_Y(int center_Y) {
        Rect targetRect = new Rect();
        paint.getTextBounds(text, 0, text.length(), targetRect);
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        return center_Y + (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
    }
}