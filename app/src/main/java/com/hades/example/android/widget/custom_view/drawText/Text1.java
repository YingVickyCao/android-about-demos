package com.hades.example.android.widget.custom_view.drawText;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.hades.example.android.R;

public class Text1 extends View {
    Paint paint;

    public Text1(Context context, AttributeSet set) {
        super(context, set);
        paint = new Paint();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.BLACK);

        paint.setAntiAlias(true); // 去锯齿
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(getResources().getDimension(R.dimen.stroke_width));
        paint.setTextAlign(Paint.Align.CENTER);

        paint.setTextSize(getResources().getDimension(R.dimen.text_size_20));
        int x = (int) getResources().getDimension(R.dimen.size_50);
        int y = x;
        /**
         * canvas.drawText 画字符串
         */
        canvas.drawText("测试drawText 1234", x, y, paint);
    }
}