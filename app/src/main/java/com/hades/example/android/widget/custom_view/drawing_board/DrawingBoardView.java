package com.hades.example.android.widget.custom_view.drawing_board;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.hades.example.android.R;

public class DrawingBoardView extends View {
    private static final String TAG = "DrawingBoardView";
    /**
     * 定义记录前一个拖动事件此点的坐标
     */
    float prevX;
    float prevY;

    private Path path = new Path();
    private Paint paint = new Paint();
    private Paint bmpPaint = new Paint();
    private Bitmap cachedBitmap = null;
    private Canvas cachedCanvas = null;

    private final EmbossMaskFilter embossMaskFilter = new EmbossMaskFilter(new float[]{1.5f, 1.5f, 1.5f}, 0.6f, 6, 4.2f);
    private final BlurMaskFilter blurMaskFilter = new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL);
    private MaskFilter maskFilter;

    public DrawingBoardView(Context context) {
        super(context);
    }

    public DrawingBoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d(TAG, "DrawingBoardView: ");

        cachedBitmap = Bitmap.createBitmap(500, 200, Bitmap.Config.ARGB_8888);

        cachedCanvas = new Canvas();
        cachedCanvas.setBitmap(cachedBitmap);

        paint = new Paint(Paint.DITHER_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        setPaintStrokeWidth(getResources().getDimension(R.dimen.size_1));
        paint.setAntiAlias(true);
        paint.setDither(true);
    }

    public DrawingBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d(TAG, "DrawingBoardView: ");
    }

    public DrawingBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Log.d(TAG, "DrawingBoardView: ");
    }

    public void setPaintStrokeWidth(float paintStrokeWidth) {
        paint.setStrokeWidth(paintStrokeWidth);
    }

    public void setPaintColor(int paintColor) {
        paint.setColor(paintColor);
    }

    public void useEmbossMaskFilter() {
        paint.setMaskFilter(embossMaskFilter);
    }

    public void useBlurMaskFilter() {
        paint.setMaskFilter(blurMaskFilter);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onTouchEvent: ACTION_DOWN");
                path.moveTo(x, y);
                prevX = x;
                prevY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onTouchEvent: ACTION_MOVE");
                path.quadTo(prevX, prevY, x, y);
                prevX = x;
                prevY = y;
                break;

            case MotionEvent.ACTION_UP:
                Log.d(TAG, "onTouchEvent: ACTION_UP");
                cachedCanvas.drawPath(path, paint);
//                path.reset();
                break;
        }

        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw: ");
        bmpPaint.reset();

        canvas.drawColor(getResources().getColor(R.color.paint_bg, getContext().getTheme()));
        canvas.drawBitmap(cachedBitmap, 0, 0, bmpPaint);
        canvas.drawPath(path, paint);
    }
}
