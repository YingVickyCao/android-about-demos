package com.hades.example.android.widget.custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.hades.example.android.R;

/**
 * Path
 * canvas.drawPath
 */
public class Triangle extends View {
    Path path = new Path();
    Paint paint;

    public Triangle(Context context) {
        super(context);
        paint = new Paint();
    }

    public Triangle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

    public Triangle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
    }

    public Triangle(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        paint = new Paint();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.BLACK);

        paint.setAntiAlias(true);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(getResources().getDimension(R.dimen.stroke_width));

        // 定义一个Path对象，封闭成一个三角形
        path.moveTo((int) getResources().getDimension(R.dimen.size_100), (int) getResources().getDimension(R.dimen.size_25));
        path.lineTo((int) getResources().getDimension(R.dimen.size_50), (int) getResources().getDimension(R.dimen.size_75));
        path.lineTo((int) getResources().getDimension(R.dimen.size_150), (int) getResources().getDimension(R.dimen.size_75));
        path.close();

        // 根据Path进行绘制，绘制三角形
        canvas.drawPath(path, paint);
    }
}