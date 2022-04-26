package com.hades.example.android.widget.custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.hades.example.android.R;

/**
 * canvas.drawLine
 */
public class Lines extends View {
    private static final String TAG = Lines.class.getSimpleName();

    Paint paint;

    public Lines(Context context, AttributeSet set) {
        super(context, set);
        paint = new Paint();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        Log.d(TAG, "draw:" + "left=" + getLeft() + ",top=" + getTop() + ",right=" + getRight() + ",bottom=" + getBottom());

        canvas.drawColor(Color.BLACK);

        paint.setAntiAlias(true);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(getResources().getDimension(R.dimen.size_2));

        // 每条直线占用4个数据
        float[] pts = {
                50, 50, 400, 50,
                50, 100, 400, 100,

                50, 150, 400, 150,
                50, 200, 400, 200
        };
        /**
         * canvas.drawLines 绘制线
         */
//        canvas.drawLines(pts, paint);
        /**
         * drawLines
         * offset：跳过的数据个数，这些数据将不参与绘制过程。
         * count：实际参与绘制的数据个数。
         */
        canvas.drawLines(pts, 4, 12, paint);
    }
}