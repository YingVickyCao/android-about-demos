package com.hades.example.android.widget.custom_view.Xfermode;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class CanvasLayerExample4View extends View {
    private static final String TAG = "CanvasLayerExampleView";
    // FILTER_BITMAP_FLAG 对位图进行滤波
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    private final int OFFSET = 100;

    public CanvasLayerExample4View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        Log.d(TAG, "onDraw: " + canvas.getSaveCount()); // 1
        canvas.drawColor(Color.BLACK);  //  默认图层 index=0
        Log.d(TAG, "onDraw: " + canvas.getSaveCount()); // 1

        int saveCount1 = canvas.saveLayer(OFFSET, OFFSET, getWidth() - OFFSET, getHeight() - OFFSET, paint); // 保存的是index=0的图层
        canvas.drawColor(Color.CYAN);   //  绘制到图层 index=1
        Log.d(TAG, "onDraw: " + canvas.getSaveCount()); // 2

        int saveCount2 = canvas.saveLayer(OFFSET, OFFSET, getWidth() - OFFSET, getHeight() - OFFSET, paint);    // 保存的是index=1的图层
        // 由于saveLayer时，图形index=0和index=1 的offset是一样的，即图层范围是一样的，YELLOW 完全覆盖 CYAN。
        canvas.drawColor(Color.YELLOW); //  绘制到图层 index=2
        Log.d(TAG, "onDraw: " + canvas.getSaveCount()); // 3

        canvas.restoreToCount(saveCount1);  // 切换到图形index=0
        Log.d(TAG, "onDraw: " + canvas.getSaveCount()); // 1
        canvas.drawCircle(300, 300, 300, paint);//  绘制到图层 index=0
        Log.d(TAG, "onDraw: " + canvas.getSaveCount()); // 1
    }
}
