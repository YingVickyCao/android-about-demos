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
 * 通过Top and bottom 得出的 Baseline Y
 */
public class Text4Baseline1 extends View {
    private static final String TAG = Text4Baseline1.class.getSimpleName();

    private Paint paint;
    private String text = getResources().getString(R.string.drawText_text);

    public Text4Baseline1(Context context, AttributeSet set) {
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


        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        /**
         * baseline：（200，200）
         */
        float center_X = getWidth()/2;
        int center_Y = getHeight()/2;
        float baseline_x = center_X - paint.measureText(text) / 2;
        /**
         * 屏幕 top线 Y     =1188
         * 屏幕 baseline Y  =1353
         * 屏幕 bottom 线 Y =1588
         */
        int baseline_y = center_Y + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        Log.d(TAG, "onDraw:baseline_x=" + baseline_x); // 252
        Log.d(TAG, "onDraw:baseline_y=" + baseline_y); // 247
        canvas.drawText(text, baseline_x, baseline_y, paint);

        int top_y = fontMetrics.top + baseline_y;
        int ascent_y = fontMetrics.ascent + baseline_y;
        int descent_y = fontMetrics.descent + baseline_y;
        int bottom_y = fontMetrics.bottom + baseline_y;

        // 文字高度
        int text_height = bottom_y - top_y; // or fontMetrics.bottom - fontMetrics.top
        Log.d(TAG, "onDraw:text height:" + text_height);    // 160
        Log.d(TAG, "onDraw:text height:" + getFontHeight(paint, text));// 118
        // 文字高度一半的y坐标
        int y_of_half_text_height = top_y + (text_height) / 2;

        // onDraw: fontMetrics:FontMetricsInt: top=-127 ascent=-111 descent=29 bottom=33 leading=0
        Log.d(TAG, "onDraw: fontMetrics:" + fontMetrics);
        // top_y:120,ascent_y:136,descent_y:276,bottom_y:280,text_height_half_Y:200
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
        paint.setColor(Color.YELLOW);
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

    public float getFontHeight(Paint paint, String str) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect.height();
    }
}