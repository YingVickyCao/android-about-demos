package com.hades.example.android.widget.custom_view.drawing_board;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class DrawingBoardView extends View {
    /**
     * 定义记录前一个拖动事件此点的坐标
     */
    float prevX;
    float preY;

    private Path path = new Path();
    private Paint paint = new Paint();
    private Paint bmpPaint = new Paint();
    private Bitmap cachedBitmap = null;
    Canvas cachedCanvas = null;

    public DrawingBoardView(Context context) {
        super(context);
    }

    public DrawingBoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        cachedBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);

        cachedCanvas = new Canvas();
        cachedCanvas.setBitmap(cachedBitmap);

        paint = new Paint(Paint.DITHER_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setDither(true);

    }

    public DrawingBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DrawingBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                prevX = x;
                preY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                path.quadTo(prevX, preY, x, y);
                prevX = x;
                preY = y;
                break;

            case MotionEvent.ACTION_UP:
                cachedCanvas.drawPath(path, paint);
                path.reset();
                break;

        }

        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        bmpPaint.reset();
        canvas.drawBitmap(cachedBitmap, 0, 0, bmpPaint);
        canvas.drawPath(path, paint);
    }
}
