package com.hades.example.android.widget.custom_view.drawText;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.hades.example.android.R;

public class Text2 extends View {
    private Paint paint;
    private String text = "jEg测试 Mfgia 123";

    public Text2(Context context, AttributeSet set) {
        super(context, set);
        paint = new Paint();

        paint.setAntiAlias(true); // 去锯齿
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(getResources().getDimension(R.dimen.stroke_width));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(getResources().getDimension(R.dimen.text_size_20));
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.BLACK);

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;
        float ascent = fontMetrics.ascent;
        float descent = fontMetrics.descent;
        float leading = fontMetrics.leading;

        float centerY = getHeight() / 2;
        float newBaseLineY = centerY + ((bottom - top) / 2 - bottom);
        float measureText = paint.measureText(text);

        float newBaseLineX = getWidth()/2 - measureText/2;


//        int x = (int) getResources().getDimension(R.dimen.size_50);
//        int y = x;
//        /**
//         * canvas.drawText 画字符串
//         */
//        canvas.drawText(text, x, y, paint);
    }
}