package com.hades.example.android.widget.custom_view.matrix;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.hades.example.android.R;

public class MatrixOnBitmapView extends View {
    private static final String TAG = MatrixOnBitmapView.class.getSimpleName();

    Paint paint;

    public MatrixOnBitmapView(Context context, AttributeSet set) {
        super(context, set);
        paint = new Paint();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        Log.d(TAG, "draw:" + "left=" + getLeft() + ",top=" + getTop() + ",right=" + getRight() + ",bottom=" + getBottom());

        canvas.drawColor(Color.BLACK);

        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        /**
         * 绘制Bitmap
         */
        canvas.drawBitmap(bitmap, 200, 200, paint);

        ColorMatrix colorFilter = new ColorMatrix(new float[]{
                -1f, 0f, 0f, 0f, 255f,
                0f, -1f, 0f, 0f, 255f,
                0f, 0f, -1f, 0f, 255f,
                0f, 0f, 0f, 1f, 0f}); //去掉 和 绿色结合的部分
        paint.setColorFilter(new ColorMatrixColorFilter(colorFilter));
        canvas.drawBitmap(bitmap, 200, 800, paint);
    }
}