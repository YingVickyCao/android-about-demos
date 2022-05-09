package com.hades.example.android.widget.custom_view.shader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import com.hades.example.android.R;

/**
 * paint.setShader Paint设置渐变器 - LinearGradient（线性渐变）
 */
public class RectWithShader extends View {
    Paint paint;
    private int colors[] = new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
    Bitmap bitmap;
    private Shader bitmapShader;
    private Shader linearGradient;
    private Shader radialGradient;
    private Shader sweepGradient;
    private Shader composeShader;
    private Shader currentShader;

    public RectWithShader(Context context, AttributeSet set) {
        super(context, set);
        init();
    }

    private void init() {
        paint = new Paint();

        if (null == bitmapShader) {
            if (bitmap == null) {
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_grid);
            }
            // 位图平铺
            // Shader.TileMode.CLAMP - 边缘拉伸模式. 将边缘的一个像素进行拉伸、扩展
//            bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            // Shader.TileMode.REPEAT - 重复模式
//            bitmapShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            bitmapShader = new BitmapShader(bitmap, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
        }

        if (null == linearGradient) {
            // 线性渐变
            linearGradient = new LinearGradient(0, 0, 100, 100, colors, null, Shader.TileMode.REPEAT);
        }

        if (null == radialGradient) {
            // 圆角渐变
            radialGradient = new RadialGradient(100, 100, 80, colors, null, Shader.TileMode.REPEAT);
        }

        if (null == sweepGradient) {
            // 角度渐变
            sweepGradient = new SweepGradient(160, 160, colors, null);
        }
        if (null == composeShader) {
            // 组合渲染
            composeShader = new ComposeShader(linearGradient, radialGradient, PorterDuff.Mode.DARKEN);
        }
    }

    public void useBitmapShader() {
        currentShader = bitmapShader;
        invalidate();
    }

    public void useLinearGradient() {
        currentShader = linearGradient;
        invalidate();
    }

    public void useRadialGradient() {
        currentShader = radialGradient;
        invalidate();
    }

    public void useSweepGradient() {
        currentShader = sweepGradient;
        invalidate();
    }

    public void useComposeShader() {
        currentShader = composeShader;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);
        paint.setShader(currentShader);
        //设置阴影
//        paint.setShadowLayer(25, 20, 20, Color.GRAY);

        paint.setAntiAlias(true); // 去锯齿
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(4);

        // 绘制圆形
//        int left = (int) getResources().getDimension(R.dimen.size_15);
//        int top = (int) getResources().getDimension(R.dimen.size_15);
        int right = (int) getResources().getDimension(R.dimen.size_400);
        int bottom = (int) getResources().getDimension(R.dimen.size_400);
        /**
         * drawRect 绘制矩形
         */
        canvas.drawRect(0, 0, right, bottom, paint);
    }
}