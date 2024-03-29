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
public class Line extends View {
    private static final String TAG = Line.class.getSimpleName();

    Paint paint;

    public Line(Context context, AttributeSet set) {
        super(context, set);
        paint = new Paint();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d(TAG, "draw:" + "left=" + getLeft() + ",top=" + getTop() + ",right=" + getRight() + ",bottom=" + getBottom());

        canvas.drawColor(Color.BLACK);

        paint.setAntiAlias(true);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(getResources().getDimension(R.dimen.size_2));

        /**
         * canvas.drawLine 绘制线
         */
        canvas.drawLine((int) getResources().getDimension(R.dimen.size_50)
                , (int) getResources().getDimension(R.dimen.size_50)
                , (int) getResources().getDimension(R.dimen.size_150)
                , (int) getResources().getDimension(R.dimen.size_50), paint);
    }
}