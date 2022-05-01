package com.hades.example.android.widget.custom_view.matrix;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.hades.example.android.R;

public class MatrixOnBitmapView extends View {
    private static final String TAG = MatrixOnBitmapView.class.getSimpleName();

    Paint paint;
    // 初始化Matrix对象
    Matrix matrix = new Matrix();
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_2);

    private final int ACTION_SCALE = 1;
    private final int ACTION_SKEW = 2;
    private final int ACTION_TRANSLATE = 3;
    private final int ACTION_ROTATE = 4;

    private int action = 0;

    public MatrixOnBitmapView(Context context, AttributeSet set) {
        super(context, set);
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    public void bitmapScale() {
        action = ACTION_SCALE;
    }

    public void bitmapRotate() {
        action = ACTION_ROTATE;
    }

    public void bitmapTranslate() {
        action = ACTION_TRANSLATE;
    }

    public void bitmapSkew() {
        action = ACTION_SKEW;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        matrix.reset();
        if (ACTION_SCALE == action) {
            // 缩放
            matrix.setScale(2.0f, 2.0f);
            canvas.drawBitmap(bitmap, matrix, paint);
        } else if (ACTION_SKEW == action) {
            // 倾斜
            matrix.setSkew(0.2f, 0.4f);
            canvas.drawBitmap(bitmap, matrix, paint);
        } else if (ACTION_TRANSLATE == action) {
            // 移动
            matrix.setTranslate(200f, 200f);
            canvas.drawBitmap(bitmap, matrix, paint);
        } else if (ACTION_ROTATE == action) {
            // 旋转
            matrix.setRotate(90, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
            canvas.drawBitmap(bitmap, matrix, paint);
        } else {
            canvas.drawBitmap(bitmap, 0, 0, paint);
        }
    }
}