package com.hades.example.android.widget.custom_view.drawText;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.hades.example.android.R;

/**
 * View 的大小 是 800x400
 * 绘制文字最小矩形
 */
public class Text4DrawTextMinRect extends View {
    private static final String TAG = Text4DrawTextMinRect.class.getSimpleName();

    private Paint paint;
    private String text = getResources().getString(R.string.drawText_text);

    public Text4DrawTextMinRect(Context context, AttributeSet set) {
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

        /**
         * baseline：（200，200）
         */
        int baseline_x = 200;
        int baseline_y = 200;
        canvas.drawText(text, baseline_x, baseline_y, paint);

        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int top_y = fontMetrics.top + baseline_y;
        int ascent_y = fontMetrics.ascent + baseline_y;
        int descent_y = fontMetrics.descent + baseline_y;
        int bottom_y = fontMetrics.bottom + baseline_y;

//        int text_height_half_Y = fontMetrics.top + (fontMetrics.bottom - fontMetrics.top) / 2;

        // 文字高度
        int text_height = bottom_y - top_y; // or fontMetrics.bottom - fontMetrics.top
        // 文字高度一半的y坐标
        int y_of_half_text_height = top_y + (text_height) / 2;

        // onDraw: fontMetrics:FontMetricsInt: top=-127 ascent=-111 descent=29 bottom=33 leading=0
        Log.d(TAG, "onDraw: fontMetrics:" + fontMetrics);
        // onDraw: top_y:73,ascent_y:89,descent_y:229,bottom_y:233,text_height_half_Y:153
        Log.d(TAG, "onDraw: top_y:" + top_y + ",ascent_y:" + ascent_y + ",descent_y:" + descent_y + ",bottom_y:" + bottom_y + ",text_height_half_Y:" + y_of_half_text_height);

        // 绘制top
        paint.setColor(Color.MAGENTA);
        canvas.drawLine(0, top_y, getWidth(), top_y, paint);

        // 绘制Ascent线
        paint.setColor(Color.CYAN);
        canvas.drawLine(0, ascent_y, getWidth(), ascent_y, paint);

        // 绘制 Height/2
        paint.setColor(Color.BLACK);
        canvas.drawLine(0, y_of_half_text_height, getWidth(), y_of_half_text_height, paint);

        // 绘制Baseline
        paint.setColor(Color.RED);
        canvas.drawLine(0, baseline_y, getWidth(), baseline_y, paint);

        // 绘制descent
        paint.setColor(Color.GREEN);
        canvas.drawLine(0, descent_y, getWidth(), descent_y, paint);

        // 绘制bottom
        paint.setColor(Color.MAGENTA);
        canvas.drawLine(0, bottom_y, getWidth(), bottom_y, paint);

        // 绘制文字最小矩形
        // 文字宽度
        float textWidth = paint.measureText(text);
        paint.setColor(Color.parseColor("#20ff0000"));
        canvas.drawRect(baseline_x, ascent_y, (baseline_y + textWidth), descent_y, paint);
    }
}