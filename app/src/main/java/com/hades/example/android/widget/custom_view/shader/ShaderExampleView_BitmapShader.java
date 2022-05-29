package com.hades.example.android.widget.custom_view.shader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.hades.example.android.R;

/**
 * BitmapShader（位图平铺）
 */
public class ShaderExampleView_BitmapShader extends View {
    private Paint paint;
    private Bitmap bitmap;
    private Shader shader;
    int right = (int) getResources().getDimension(R.dimen.size_400);
    int bottom = (int) getResources().getDimension(R.dimen.size_400);

    public ShaderExampleView_BitmapShader(Context context, AttributeSet set) {
        super(context, set);
        init();
    }

    private void init() {
        paint = new Paint();

        if (null == shader) {
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
            shader = new BitmapShader(bitmap, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
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

        // 绘制矩形
        canvas.drawRect(0, 0, right, bottom, paint);
    }
}