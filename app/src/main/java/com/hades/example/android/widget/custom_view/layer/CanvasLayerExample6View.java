package com.hades.example.android.widget.custom_view.layer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.hades.example.android.R;

/**
 * 结论：
 */
public class CanvasLayerExample6View extends View {
    private static final String TAG = CanvasLayerExample6View.class.getSimpleName();
    // FILTER_BITMAP_FLAG 对位图进行滤波
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CanvasLayerExample6View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.translate(10, 10);

        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(75, 75, 75, paint);
        Log.d(TAG, "onDraw: " + canvas.getSaveCount()); // 1
        int count = canvas.saveLayerAlpha(0, 0, 220, 220, 0x88);
        canvas.drawColor(getResources().getColor(R.color.color_green_alpha_10, getContext().getTheme()));
        Log.d(TAG, "onDraw: " + canvas.getSaveCount());// 2

        paint.setColor(Color.BLUE);
        canvas.drawCircle(125, 125, 75, paint);

        canvas.restore();
        // restore()次数大于 save() 次数会报错：java.lang.IllegalStateException: Underflow in restore - more restores than saves
        canvas.restore();
//        canvas.restoreToCount(count);
        Log.d(TAG, "onDraw: " + canvas.getSaveCount());
        paint.setColor(Color.YELLOW);
        canvas.drawCircle(165, 200, 75, paint);// 1
    }
}
