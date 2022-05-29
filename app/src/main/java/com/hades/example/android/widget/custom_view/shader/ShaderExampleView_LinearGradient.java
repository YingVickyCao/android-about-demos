package com.hades.example.android.widget.custom_view.shader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.hades.example.android.R;

/**
 * paint.setShader Paint设置渐变器 - LinearGradient（线性渐变）
 */
public class ShaderExampleView_LinearGradient extends View {
    private Paint paint;
    private Shader shader;

    int right = (int) getResources().getDimension(R.dimen.size_200);
    int bottom = (int) getResources().getDimension(R.dimen.size_200);
    private int colors[] = new int[]{Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE};
    private float positions[] = new float[]{0, 0.1F, 0.5F, 0.8F};

    public ShaderExampleView_LinearGradient(Context context, AttributeSet set) {
        super(context, set);
        init();
    }

    private void init() {
        paint = new Paint();
        if (null == shader) {
            // 线性渐变
            /**
             * 渐变的起点：(x0,y0)
             * 渐变的终点：(x1,y1)
             * color0:  起点的颜色
             * color1:  终点的颜色
             * TileMode：模式不能为空
             */
//            shader = new LinearGradient(0, 0, right, bottom, Color.RED, Color.YELLOW, Shader.TileMode.REPEAT);

            /**
             * 渐变的起点：(x0,y0)
             * 渐变的终点：(x1,y1)
             * colors:      包含多个颜色
             * positions:   包含多个颜色的位置。 可以为null。
             * TileMode：模式不能为空
             */
            // 等于null时，颜色均匀地填充整个渐变区域
            shader = new LinearGradient(0, 0, right, bottom, colors, null, Shader.TileMode.MIRROR);
            // 颜色从红色到黄色的渐变起点是整个渐变区域（left, top, right, bottom定义了渐变区域）的起点，终点是渐变区域长度*10%的地方。例如，绿色到黄色是从渐变区域50%到80%。
//            shader = new LinearGradient(0, 0, right, bottom, colors, positions, Shader.TileMode.REPEAT);
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);
        paint.setShader(shader);
        //设置阴影
//        paint.setShadowLayer(25, 20, 20, Color.GRAY);

        paint.setAntiAlias(true); // 去锯齿
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(4);

        // 绘制圆形
        /**
         * drawRect 绘制矩形
         */
        canvas.drawRect(0, 0, right, bottom, paint);
    }
}