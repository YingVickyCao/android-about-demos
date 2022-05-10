package com.hades.example.android.widget.custom_view.shader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.hades.example.android.R;

/**
 * 利用LinearGradient （线性渐变）实现倒影图
 * https://www.cnblogs.com/tianzhijiexian/p/4298660.html
 */
public class ReflectionImageView extends View {
    Paint paint = new Paint();

    public ReflectionImageView(Context context) {
        super(context);
    }

    public ReflectionImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ReflectionImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ReflectionImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.GRAY);

        int x = 200, y = 200;

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_grid);

        Matrix matrix = new Matrix();
        matrix.setScale(1F, -1F);
        Bitmap reflectionBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        canvas.drawBitmap(bitmap, x, y, null);

        int sc = canvas.saveLayer(x, y + bitmap.getHeight(), x + bitmap.getWidth(), y + bitmap.getHeight() * 2, null, Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(reflectionBitmap, x, y + bitmap.getHeight(), null);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        paint.setShader(new LinearGradient(x, y + bitmap.getHeight(), x, y + bitmap.getHeight() + bitmap.getHeight() / 4, Color.BLACK, Color.TRANSPARENT, Shader.TileMode.CLAMP));

        canvas.drawRect(x, y + bitmap.getHeight(), x + reflectionBitmap.getWidth(), y + bitmap.getHeight() * 2, paint);

        paint.setXfermode(null);

        canvas.restoreToCount(sc);
    }
}
