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
public class ShaderExampleView_ComposeShader extends View {
    Paint paint = new Paint();
    private Shader linearGradient;
    private Shader radialGradient;
    private Shader composeShader;

    int right = (int) getResources().getDimension(R.dimen.size_400);
    int bottom = (int) getResources().getDimension(R.dimen.size_400);

    public ShaderExampleView_ComposeShader(Context context, AttributeSet set) {
        super(context, set);
        init();
    }

    private void init() {
        /**
         * 渐变的起点：(x0,y0)
         * 渐变的终点：(x1,y1)
         * color0:  起点的颜色
         * color1:  终点的颜色
         * TileMode：模式不能为空
         */
        // 线性渐变
        linearGradient = new LinearGradient(0, 0, right, bottom, Color.RED, Color.YELLOW, Shader.TileMode.REPEAT);
        // 圆角渐变
        radialGradient = new RadialGradient(100, 100, 80, new int[]{Color.RED, Color.GREEN, Color.YELLOW}, null, Shader.TileMode.REPEAT);
        
        // 组合渲染
        composeShader = new ComposeShader(linearGradient, radialGradient, PorterDuff.Mode.DARKEN);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);
        paint.setShader(composeShader);

        paint.setAntiAlias(true); // 去锯齿
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(4);

        // 绘制矩形
        canvas.drawRect(0, 0, right, bottom, paint);
    }
}