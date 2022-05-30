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
public class ShaderExampleView_BitmapShader_Matrix extends View {
    private Paint paint=new Paint();
    private Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_grid);
    private Shader shader;
    int right = (int) getResources().getDimension(R.dimen.size_200);
    int bottom = (int) getResources().getDimension(R.dimen.size_200);

    public ShaderExampleView_BitmapShader_Matrix(Context context, AttributeSet set) {
        super(context, set);
        init();
    }

    private void init() {
        shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
    }

//    @Override
//    public void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//
//        canvas.drawColor(Color.LTGRAY);
//
//        paint.setShader(shader);
//
//        // 绘制矩形
//        canvas.drawRect(0, 0, right, bottom, paint);
//    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.LTGRAY);

        // 设置矩阵变换
        Matrix matrix = new Matrix();
        matrix.setTranslate(right / 2, right / 3    );
        // 设置Shader的变换矩阵
        shader.setLocalMatrix(matrix);

        paint.setShader(shader);

        // 绘制矩形
        canvas.drawRect(0, 0, right, bottom, paint);
    }
}