package com.hades.example.android.widget.custom_view.layer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 结论：canvas.restoreToCount(count-1)作用是退栈，index=count-1
 */
public class CanvasLayerExample3View extends View {
    private static final String TAG = "CanvasLayerExampleView";
    // FILTER_BITMAP_FLAG 对位图进行滤波
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final int OFFSET = 100;

    public CanvasLayerExample3View(Context context, @Nullable AttributeSet attrs) {
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

        int saveCount2 = canvas.saveLayer(OFFSET * 2, OFFSET * 2, getWidth() - OFFSET * 2, getHeight() - OFFSET * 2, paint);    // 保存的是index=1的图层
        canvas.drawColor(Color.YELLOW); //  绘制到图层 index=2
        Log.d(TAG, "onDraw: " + canvas.getSaveCount()); // 3

        canvas.restoreToCount(saveCount1);  // 切换到图层index=0，即把图层index=2和index=3退栈，在图层index=0，继续绘制。
        Log.d(TAG, "onDraw: " + canvas.getSaveCount()); // 1
        canvas.drawCircle(300, 300, 300, paint);//  绘制到图层 index=0
        Log.d(TAG, "onDraw: " + canvas.getSaveCount()); // 1
    }
}
