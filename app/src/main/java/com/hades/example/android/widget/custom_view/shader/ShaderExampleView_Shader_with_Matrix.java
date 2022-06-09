package com.hades.example.android.widget.custom_view.shader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.hades.example.android.R;

/**
 * BitmapShader（位图平铺）
 */
public class ShaderExampleView_Shader_with_Matrix extends View {
    private Paint paint = new Paint();
    private Shader shader;

    private int colors[] = new int[]{Color.RED, Color.GREEN};
    int right = (int) getResources().getDimension(R.dimen.size_100);
    int bottom = (int) getResources().getDimension(R.dimen.size_100);

    public ShaderExampleView_Shader_with_Matrix(Context context, AttributeSet set) {
        super(context, set);
        init();
    }

    private void init() {
        shader = new LinearGradient(0, 0, right, bottom, colors, null, Shader.TileMode.MIRROR);
    }


    @Override
    public void onDraw(Canvas canvas) {
        // 设置矩阵变换
        Matrix matrix = new Matrix();
        matrix.setTranslate(right / 2, right / 2);
        // 设置Shader的变换矩阵
        shader.setLocalMatrix(matrix);

        paint.setShader(shader);

        canvas.drawCircle(right / 2, right / 2, right / 2, paint);
    }
}