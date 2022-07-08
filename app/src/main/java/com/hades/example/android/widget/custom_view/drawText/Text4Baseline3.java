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
 * 通过 Ascent和Descent 得出的 Baseline Y
 */
public class Text4Baseline3 extends View {
    private static final String TAG = Text4Baseline3.class.getSimpleName();

    private Paint paint;
    private String text = getResources().getString(R.string.drawText_text);

    public Text4Baseline3(Context context, AttributeSet set) {
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


        /*
        onDraw:baseline_X=252.0
        onDraw:baseline_Y=241
        onDraw:text height:160
        onDraw:text height:118.0
        onDraw: fontMetrics:FontMetricsInt: top=-127 ascent=-111 descent=29 bottom=33 leading=0
        onDraw: top_y:114,ascent_y:130,descent_y:270,bottom_y:274,text_height_half_Y:194
         */
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        float center_X = getWidth() / 2;
        int center_Y = getHeight() / 2;

        float baseline_X = center_X - paint.measureText(text) / 2;
        Log.d(TAG, "onDraw:baseline_X=" + baseline_X); // 252

        int baseline_Y = center_Y + (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent;
        Log.d(TAG, "onDraw:baseline_Y=" + baseline_Y);  // 214

        canvas.drawText(text, baseline_X, baseline_Y, paint);

        int top_y = fontMetrics.top + baseline_Y;
        int ascent_y = fontMetrics.ascent + baseline_Y;
        int descent_y = fontMetrics.descent + baseline_Y;
        int bottom_y = fontMetrics.bottom + baseline_Y;


        // 文字高度
        int text_height = bottom_y - top_y; // or fontMetrics.bottom - fontMetrics.top
        Log.d(TAG, "onDraw:text height:" + text_height);    // 160
        // 文字高度一半的y坐标
        int y_of_half_text_height = top_y + (text_height) / 2;

        // onDraw: fontMetrics:FontMetricsInt: top=-127 ascent=-111 descent=29 bottom=33 leading=0
        Log.d(TAG, "onDraw:fontMetrics:" + fontMetrics);
        // onDraw: top_y:87,ascent_y:103,descent_y:243,bottom_y:247,text_height_half_Y:167
        Log.d(TAG, "onDraw:top_y:" + top_y + ",ascent_y:" + ascent_y + ",descent_y:" + descent_y + ",bottom_y:" + bottom_y + ",text_height_half_Y:" + y_of_half_text_height);

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
        canvas.drawLine(0, baseline_Y, getWidth(), baseline_Y, paint);

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
        canvas.drawRect(baseline_X, ascent_y, (baseline_X + textWidth), descent_y, paint);
    }
}