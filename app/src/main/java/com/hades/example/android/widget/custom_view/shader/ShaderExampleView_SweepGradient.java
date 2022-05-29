package com.hades.example.android.widget.custom_view.shader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import com.hades.example.android.R;

/**
 * SweepGradient（角度渐变）
 */
public class ShaderExampleView_SweepGradient extends View {
    private Paint paint;
    private Shader shader;

    int right = (int) getResources().getDimension(R.dimen.size_200);
    int bottom = (int) getResources().getDimension(R.dimen.size_200);

    public ShaderExampleView_SweepGradient(Context context, AttributeSet set) {
        super(context, set);
        init();
    }

    private void init() {
        paint = new Paint();
        if (null == shader) {
            // 角度渐变
            // 产生从color0到color1的渐变
//            shader = new SweepGradient(right / 2, right / 2, Color.RED, Color.GREEN);

            // 产生从colors[0]-colors[count-1]渐变
            shader = new SweepGradient(right / 2, right / 2, new int[]{Color.RED, Color.GREEN, Color.YELLOW}, null);
        }
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

        /**
         * drawRect 绘制矩形
         */
        canvas.drawRect(0, 0, right, bottom, paint);
    }
}