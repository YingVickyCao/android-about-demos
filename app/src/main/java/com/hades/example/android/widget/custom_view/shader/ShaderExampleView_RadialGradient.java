package com.hades.example.android.widget.custom_view.shader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.hades.example.android.R;

/**
 * RadialGradient (圆角渐变)
 */
public class ShaderExampleView_RadialGradient extends View {
    private Paint paint = new Paint();
    private int colors[] = new int[]{Color.RED, Color.GREEN, Color.YELLOW};

    private Shader shader;
    private int right = (int) getResources().getDimension(R.dimen.size_200);
    private int bottom = (int) getResources().getDimension(R.dimen.size_200);
    private int centerX = right / 2;
    private int centerY = centerX;
    private int radius = centerX;

    public ShaderExampleView_RadialGradient(Context context, AttributeSet set) {
        super(context, set);
        init();
    }

    private void init() {
        // 圆角渐变
        // （centerX，centerY）是圆心的坐标，radius是半径，centerColor是边缘的颜色，edgeColor是外围的颜色
//      shader = new RadialGradient(centerX, centerY, radius, Color.RED, Color.GREEN, Shader.TileMode.CLAMP);

        // colors:多个颜色。stops:色彩的位置。
        shader = new RadialGradient(centerX, centerY, radius, new int[]{Color.RED, Color.GREEN, Color.YELLOW}, new float[]{0.1f, 0.5f, 0.8f}, Shader.TileMode.CLAMP);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);
        paint.setShader(shader);

        paint.setAntiAlias(true); // 去锯齿
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(4);

        // 绘制矩形
        canvas.drawRect(0, 0, right, bottom, paint);
    }
}