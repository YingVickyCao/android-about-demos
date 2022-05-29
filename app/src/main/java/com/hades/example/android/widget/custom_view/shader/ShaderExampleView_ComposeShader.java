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
    Paint paint;
    private int colors[] = new int[]{Color.RED, Color.GREEN, Color.YELLOW};
    private float positions[] = new float[]{0, 0.5F, 0.8F};
    Bitmap bitmap;
    private Shader bitmapShader;
    private Shader linearGradient;
    private Shader radialGradient;
    private Shader sweepGradient;
    private Shader composeShader;
    private Shader currentShader;

    //        int left = (int) getResources().getDimension(R.dimen.size_15);
//        int top = (int) getResources().getDimension(R.dimen.size_15);
    int right = (int) getResources().getDimension(R.dimen.size_400);
    int bottom = (int) getResources().getDimension(R.dimen.size_400);

    public ShaderExampleView_ComposeShader(Context context, AttributeSet set) {
        super(context, set);
        init();
    }

    private void init() {
        paint = new Paint();

        if (null == bitmapShader) {
            // 位图平铺
            // https://www.cnblogs.com/tianzhijiexian/p/4298660.html
            // 绘制过程是先采用Y轴模式，再使用X轴模式的
            if (bitmap == null) {
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_grid);
            }

            // Shader.TileMode.CLAMP - 边缘拉伸模式. 将边缘的一个像素进行拉伸、扩展
//             bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

            // Shader.TileMode.REPEAT - 重复模式(平移复制)
//            bitmapShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);

            // Shader.TileMode.MIRROR 镜像复制
            bitmapShader = new BitmapShader(bitmap, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
        }

        if (null == linearGradient) {
            // 线性渐变
            /**
             * 渐变的起点：(x0,y0)
             * 渐变的终点：(x1,y1)
             * color0:  起点的颜色
             * color1:  终点的颜色
             * TileMode：模式不能为空
             */
            linearGradient = new LinearGradient(0, 0, right, bottom, Color.RED, Color.YELLOW, Shader.TileMode.REPEAT);
//            linearGradient = new LinearGradient(0, 0, 400, 400, Color.RED, Color.YELLOW, Shader.TileMode.REPEAT);

            /**
             * 渐变的起点：(x0,y0)
             * 渐变的终点：(x1,y1)
             * colors:      包含多个颜色
             * positions:   包含多个颜色的位置。 可以为null。等于null时，颜色均匀地填充整个渐变区域
             * TileMode：模式不能为空
             */
//            linearGradient = new LinearGradient(0, 0, right, bottom, colors, null, Shader.TileMode.REPEAT);
//            linearGradient = new LinearGradient(0, 0, 400, 400, colors, null, Shader.TileMode.REPEAT);
//            linearGradient = new LinearGradient(0, 0, 400, 400, colors, positions, Shader.TileMode.REPEAT);// 颜色从红色到黄色的渐变起点是整个渐变区域（left, top, right, bottom定义了渐变区域）的起点，终点是渐变区域长度*10%的地方。例如，绿色到黄色是从渐变区域50%到80%。
//            linearGradient = new LinearGradient(0, 0, right, bottom, colors, positions, Shader.TileMode.REPEAT);
//            linearGradient = new LinearGradient(0, 0, right, bottom, colors, null, Shader.TileMode.REPEAT);
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

        // 绘制矩形
        canvas.drawRect(0, 0, right, bottom, paint);
    }
}