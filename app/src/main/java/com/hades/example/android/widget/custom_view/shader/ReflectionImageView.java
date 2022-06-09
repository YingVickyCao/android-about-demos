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
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.hades.example.android.R;

/**
 * 利用LinearGradient （线性渐变）实现倒影图
 * https://www.cnblogs.com/tianzhijiexian/p/4298660.html
 */
public class ReflectionImageView extends View {
    private static final String TAG = ReflectionImageView.class.getSimpleName();
    Paint paint = new Paint();
    Bitmap source = BitmapFactory.decodeResource(getResources(), R.drawable.ic_grid_2);

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
        canvas.drawColor(getResources().getColor(R.color.reflection_bg_color, getContext().getTheme()));

        int x = 0, y = 0;
        // 绘制源图
        canvas.drawBitmap(source, x, y, null);
        Log.d(TAG, "onDraw: " + source.getWidth() + "," + source.getHeight());

        // 创建到影图，和源图大小一样
        Matrix matrix = new Matrix();
        matrix.setScale(1F, -1F);
        Bitmap reflection = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);

        // 新建图层，它的区域包含了倒置图
        int savedCount = canvas.saveLayer(x, y + source.getHeight(), x + source.getWidth(), y + source.getHeight() * 2, null);
        // ·
        canvas.drawBitmap(reflection, x, y + source.getHeight(), null);

        // 设置混合模式
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // Shader 用于填充混合目标，颜色从Color.BLACK到透明。
        // 倒影的高度是源图的1/2
        paint.setShader(new LinearGradient(x, y + source.getHeight(), x, y + source.getHeight() * 1.5f, Color.BLACK, Color.TRANSPARENT, Shader.TileMode.CLAMP));
        // 使用矩形，作为混合目标，用来裁剪到倒影
        canvas.drawRect(x, y + source.getHeight(), x + reflection.getWidth(), y + source.getHeight() * 2, paint);
        paint.setXfermode(null);

        canvas.restoreToCount(savedCount);
    }
}
