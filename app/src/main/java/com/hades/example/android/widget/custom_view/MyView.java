package com.hades.example.android.widget.custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.hades.example.android.R;

/**
 * Example: Canvas and Paint
 * canvas.drawColor
 * Canvas.drawCircle        绘制圆形
 * Canvas.drawRect          绘制矩形、长方形
 * Canvas.drawRoundRect     绘制圆角矩形
 * Canvas.drawOval          绘制椭圆
 * Canvas.drawPath          使用Path，绘制任意形状
 * Canvas.drawText
 * <p>
 * Paint.setColor
 * Paint.setARGB
 * Paint.setStyle
 * Paint.setStrokeWidth
 * Paint.reset()
 * Paint.setShader
 * Paint.setShadowLayer
 * Paint.setTextSize
 * Paint.setAntiAlias
 * Paint.moveTo
 * Paint.lineTo
 */
public class MyView extends View {
    private static final String TAG = "MyView2";

    private RectF rectF = new RectF();
    private Paint paint = new Paint();
    private Path trianglePath = new Path();
    Shader shader = new LinearGradient(0, 0, 40, 60, new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW}, null, Shader.TileMode.REPEAT);
    float sizeNum_10 = getResources().getDimension(R.dimen.size_10);
    float borderSize = getResources().getDimension(R.dimen.paint_border_size);

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 重写该方法，进行绘图
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 给画布绘制底色
         */
        canvas.drawColor(getResources().getColor(R.color.paint_bg, getContext().getTheme()));

        /**
         * 去锯齿
         */
        paint.setAntiAlias(true);
        /**
         * 设置画笔的颜色
         * paint.setColor
         * paint.setARGB
         */
        // paint.setColor(Color.RED);
        paint.setARGB(0xff, 0xFF, 0, 0);
        /**
         * 描边
         */
        paint.setStyle(Paint.Style.STROKE);
        float strokeWidth = getResources().getDimension(R.dimen.paint_stroke_width);
        Log.d(TAG, "onDraw: strokeWidth=" + strokeWidth);
        /**
         * 设置描边的宽度
         */
        paint.setStrokeWidth(strokeWidth);

        int viewWidth = this.getWidth();
        int viewHeight = this.getHeight();
        Log.d(TAG, "onDraw: viewWidth=" + viewWidth + ",viewHeight=" + viewHeight);

        /**
         *============== First Column,START ==============
         */
        /**
         * 绘制圆形
         */
        canvas.drawCircle((float) viewWidth / 10 + sizeNum_10, (float) viewWidth / 10 + sizeNum_10, (float) viewWidth / 10, paint);
        /**
         * 绘制正方形
         */
        canvas.drawRect(sizeNum_10, (float) viewWidth / 5 + sizeNum_10 * 2, (float) viewWidth / 5 + sizeNum_10, (float) viewWidth * 2 / 5 + sizeNum_10 * 2, paint);
        /**
         * 绘制长方形
         */
        canvas.drawRect(sizeNum_10, (float) viewHeight / 4 + sizeNum_10 * 3, (float) viewWidth / 5 + sizeNum_10, (float) viewHeight / 4 + sizeNum_10 * 7, paint);


        /**
         * 绘制圆角矩形
         */
        /**
         * Warning: Avoid object allocations during draw/layout operations (preallocate and reuse instead)
         * 原因：不要在自定义View的onMeasure、onLayout、onDraw等方法里面做new对象的操作
         */
//        RectF rectF = new RectF(sizeNum_10, (float) viewHeight / 3 + sizeNum_10 * 3, (float) viewHeight / 5 + 10, (float) viewHeight / 3 + sizeNum_10 * 3 * 3);
        // Fix:start
        rectF.left = sizeNum_10;
        rectF.top = (float) (viewHeight / 3) + sizeNum_10 * 3;
        rectF.right = (float) viewWidth / 5 + sizeNum_10;
        rectF.bottom = (float) (viewHeight / 3) + sizeNum_10 * 8;
        // Fix:END
        canvas.drawRoundRect(rectF, borderSize, borderSize, paint);

        /**
         * 绘制椭圆
         */
        rectF.left = sizeNum_10;
        rectF.top = (float) (viewHeight / 2);
        rectF.right = (float) viewWidth / 5 + sizeNum_10;
        rectF.bottom = (float) (viewHeight / 2) + sizeNum_10 * 3;
        canvas.drawOval(rectF, paint);

        /**
         * 使用Path，绘制三角形
         */
        /**
         * .1 -> .2 -> .3---close--->.1
         *        .3
         *
         *  .1----------> .2
         */
        trianglePath.moveTo(sizeNum_10, (float) viewHeight * 7 / 10); // .1
        trianglePath.lineTo((float) viewWidth / 5 + sizeNum_10, (float) viewHeight * 7 / 10);   //.1 -----> .2
        trianglePath.lineTo((float) viewWidth / 10 + sizeNum_10, (float) viewHeight * 6 / 10); // 2. ----->.3
        trianglePath.close();   // .3 -> .1
        canvas.drawPath(trianglePath, paint);
        /**
         *============== First Column,END ==============
         */
        /**
         *============== Second Column,START ==============
         */
        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(getResources().getDimension(R.dimen.paint_stroke_width));

        canvas.drawCircle((float) viewWidth / 3 + sizeNum_10, (float) viewWidth / 10 + sizeNum_10, (float) viewWidth / 10, paint);
        canvas.drawRect((float) viewWidth / 5 + sizeNum_10 * 2, (float) viewWidth / 5 + sizeNum_10 * 2, (float) viewWidth * 2 / 5 + sizeNum_10 * 2, (float) viewWidth * 2 / 5 + sizeNum_10 * 2, paint);
        canvas.drawRect((float) viewWidth / 5 + sizeNum_10 * 2, (float) viewHeight / 4 + sizeNum_10 * 3, (float) viewWidth * 2 / 5 + sizeNum_10 * 2, (float) viewHeight / 4 + sizeNum_10 * 7, paint);

        rectF.left = (float) viewWidth / 5 + sizeNum_10 * 2;
        rectF.top = (float) (viewHeight / 3) + sizeNum_10 * 3;
        rectF.right = (float) viewWidth * 2 / 5 + sizeNum_10 * 2;
        rectF.bottom = (float) (viewHeight / 3) + sizeNum_10 * 8;
        canvas.drawRoundRect(rectF, borderSize, borderSize, paint);

        rectF.left = (float) viewWidth / 5 + sizeNum_10 * 2;
        rectF.top = (float) (viewHeight / 2);
        rectF.right = (float) viewWidth * 2 / 5 + sizeNum_10 * 2;
        rectF.bottom = (float) (viewHeight / 2) + sizeNum_10 * 3;
        canvas.drawOval(rectF, paint);

        trianglePath.reset();
        trianglePath.moveTo((float) viewWidth / 5 + sizeNum_10 * 2, (float) viewHeight * 7 / 10); // .1
        trianglePath.lineTo((float) viewWidth * 2 / 5 + sizeNum_10 * 2, (float) viewHeight * 7 / 10);   //.1 -----> .2
        trianglePath.lineTo((float) viewWidth * 3 / 10 + sizeNum_10 * 2, (float) viewHeight * 6 / 10); // 2. ----->.3
        trianglePath.close();   // .3 -> .1
        canvas.drawPath(trianglePath, paint);
        /**
         *============== Second Column,END ==============
         */

        /**
         *============== Third Column,START ==============
         */
        paint.reset();
        /**
         * 为Path设置颜色渐变器
         */
        paint.setShader(shader);
        /**
         * 设置阴影
         */
        paint.setShadowLayer(25, 20, 20, Color.GRAY);

        canvas.drawCircle((float) viewWidth * 6 / 10 + sizeNum_10, (float) viewWidth / 10 + sizeNum_10, (float) viewWidth / 10, paint);
        canvas.drawRect((float) viewWidth * 5 / 10 + sizeNum_10 * 2, (float) viewWidth / 5 + sizeNum_10 * 2, (float) viewWidth * 7 / 10 + sizeNum_10 * 2, (float) viewWidth * 2 / 5 + sizeNum_10 * 2, paint);
        canvas.drawRect((float) viewWidth * 5 / 10 + sizeNum_10 * 2, (float) viewHeight / 4 + sizeNum_10 * 3, (float) viewWidth * 7 / 10 + sizeNum_10 * 2, (float) viewHeight / 4 + sizeNum_10 * 7, paint);

        rectF.left = (float) viewWidth * 5 / 10 + sizeNum_10 * 2;
        rectF.top = (float) (viewHeight / 3) + sizeNum_10 * 3;
        rectF.right = (float) viewWidth * 7 / 10 + sizeNum_10 * 2;
        rectF.bottom = (float) (viewHeight / 3) + sizeNum_10 * 8;
        canvas.drawRoundRect(rectF, borderSize, borderSize, paint);

        rectF.left = (float) viewWidth * 5 / 10 + sizeNum_10 * 2;
        rectF.top = (float) (viewHeight / 2);
        rectF.right = (float) viewWidth * 7 / 10 + sizeNum_10 * 2;
        rectF.bottom = (float) (viewHeight / 2) + sizeNum_10 * 3;
        canvas.drawOval(rectF, paint);

        trianglePath.reset();
        trianglePath.moveTo((float) viewWidth * 5 / 10 + sizeNum_10 * 2, (float) viewHeight * 7 / 10); // .1
        trianglePath.lineTo((float) viewWidth * 7 / 10 + sizeNum_10 * 2, (float) viewHeight * 7 / 10);   //.1 -----> .2
        trianglePath.lineTo((float) viewWidth * 6 / 10 + sizeNum_10 * 2, (float) viewHeight * 6 / 10); // 2. ----->.3
        trianglePath.close();   // .3 -> .1
        canvas.drawPath(trianglePath, paint);
        /**
         *============== Third Column,END ==============
         */

        /**
         *============== Fourth Column,START ==============
         */
        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setTextSize(getResources().getDimension(R.dimen.paint_text_size));

        /**
         *绘制字体
         */
        canvas.drawText("Test", (float) viewWidth * 8 / 10, (float) viewWidth / 10 + sizeNum_10, paint);
        /**
         *============== Fourth Column,END ==============
         */
    }
}
