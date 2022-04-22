package com.hades.example.android.widget.custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.hades.example.android.R;
import com.hades.example.android.resource.ResourceActivity;

public class MyView2 extends View {
    private static final String TAG = "MyView2";

    private RectF rectF = new RectF();

    public MyView2(Context context) {
        super(context);
    }

    public MyView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        float strokeWidth = getResources().getDimension(R.dimen.paint_stroke_width);
        Log.d(TAG, "onDraw: strokeWidth=" + strokeWidth);
        paint.setStrokeWidth(strokeWidth);

        int viewWidth = this.getWidth();
        int viewHeight = this.getHeight();
        Log.d(TAG, "onDraw: viewWidth=" + viewWidth + ",viewHeight=" + viewHeight);

        float sizeNum_5 = getResources().getDimension(R.dimen.paint_side_num_5);
        float sizeNum_10 = getResources().getDimension(R.dimen.paint_side_num_10);
        float sizeNum_15 = getResources().getDimension(R.dimen.paint_side_num_15);
        float sizeNum_20 = getResources().getDimension(R.dimen.paint_side_num_20);

        float borderSize = getResources().getDimension(R.dimen.paint_border_size);

        canvas.drawCircle((float) viewWidth / 10 + sizeNum_10, (float) viewWidth / 10 + sizeNum_10, (float) viewWidth / 10, paint);
        canvas.drawRect(sizeNum_10, (float) viewWidth / 5 + sizeNum_20, (float) viewWidth / 5 + sizeNum_10, (float) viewWidth * 2 / 5 + sizeNum_20, paint);
        canvas.drawRect(sizeNum_10, (float) viewHeight / 4 + sizeNum_20 * 2, (float) viewWidth / 5 + sizeNum_10, (float) viewHeight / 4 + sizeNum_20 * 4, paint);
        /**
         * Warning: Avoid object allocations during draw/layout operations (preallocate and reuse instead)
         * 原因：不要在自定义View的onMeasure、onLayout、onDraw等方法里面做new对象的操作
         */
//        RectF rectF = new RectF(sizeNum_10, (float) viewHeight / 3 + sizeNum_10 * 3, (float) viewHeight / 5 + 10, (float) viewHeight / 3 + sizeNum_10 * 3 * 3);
        // Fix:start
        rectF.left = sizeNum_10;
        rectF.top = (float) viewHeight / 3 + sizeNum_10 * 4;
        rectF.right = (float) viewWidth / 5 + 10;
        rectF.bottom = (float) viewHeight / 3 + sizeNum_10 * 6;
        // Fix:END
        canvas.drawRoundRect(rectF, borderSize, borderSize, paint);

        rectF.left = sizeNum_10;
        rectF.top = (float) (viewHeight / 2.5) + sizeNum_10 * 3;
        rectF.right = (float) viewWidth / 5 + 10;
        rectF.bottom = (float) (viewHeight / 2.5) + sizeNum_10 * 6;
        paint.setColor(Color.GREEN);
        canvas.drawOval(rectF, paint);
    }
}
