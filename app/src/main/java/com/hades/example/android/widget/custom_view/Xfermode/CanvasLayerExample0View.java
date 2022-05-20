package com.hades.example.android.widget.custom_view.Xfermode;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 结论：Canvas 默认下只有一个图层。
 */
public class CanvasLayerExample0View extends View {
    private static final String TAG = "CanvasLayerExampleView";
    // FILTER_BITMAP_FLAG 对位图进行滤波
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    private final int OFFSET = 100;

    public CanvasLayerExample0View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        Log.d(TAG, "onDraw: " + canvas.getSaveCount()); // 1
        canvas.drawColor(Color.BLACK);                       //  默认图形 index=0
        Log.d(TAG, "onDraw: " + canvas.getSaveCount()); // 1

        canvas.drawColor(Color.CYAN);
        Log.d(TAG, "onDraw: " + canvas.getSaveCount()); // 1

        canvas.drawColor(Color.YELLOW);
        Log.d(TAG, "onDraw: " + canvas.getSaveCount()); // 1

        canvas.drawCircle(300, 300, 300, paint);
        Log.d(TAG, "onDraw: " + canvas.getSaveCount()); // 1
    }
}
