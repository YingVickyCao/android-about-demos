package com.hades.example.android.widget.custom_view.layer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.hades.example.android.R;

/**
 * 结论：
 */
public class SaveExample2View extends View {
    private static final String TAG = SaveExample2View.class.getSimpleName();

    private final int OFFSET = 100;

    Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    Paint rectPaint = new Paint();

    Paint linePaint = new Paint();
    Path linePath = new Path();

    public SaveExample2View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        bgPaint.setColor(Color.GRAY);
        bgPaint.setStyle(Paint.Style.FILL);

        rectPaint.setColor(Color.RED);
        rectPaint.setStrokeWidth(4);

        linePaint.setColor(Color.GREEN);
        linePaint.setStrokeWidth(4);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int px = getWidth() - OFFSET;
        int py = getHeight() - OFFSET;

        Log.d(TAG, "onDraw: "+canvas.getSaveCount());   // 1
        int savedCount1 = canvas.save();
        Log.d(TAG, "onDraw: "+canvas.getSaveCount());
        canvas.rotate(90, px / 3, py / 4);   // 2

        // 绘制矩形
        canvas.drawRect(OFFSET * 2, OFFSET * 2, OFFSET * 3, OFFSET * 4, rectPaint);

        canvas.restore();
//        canvas.restoreToCount(savedCount1);
        Log.d(TAG, "onDraw: "+canvas.getSaveCount());   // 1

        // 根据Path进行绘制，绘制三角形
        linePath.moveTo((int) getResources().getDimension(R.dimen.size_100), (int) getResources().getDimension(R.dimen.size_25) + OFFSET * 7);
        linePath.lineTo((int) getResources().getDimension(R.dimen.size_50), (int) getResources().getDimension(R.dimen.size_75) + OFFSET * 7);
        linePath.lineTo((int) getResources().getDimension(R.dimen.size_150), (int) getResources().getDimension(R.dimen.size_75) + OFFSET * 7);
        canvas.drawPath(linePath, linePaint);
    }
}
