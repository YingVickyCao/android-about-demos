package com.hades.example.android.widget.custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.hades.example.android.R;

public class PathText extends View {
    private static final String TAG = "PathText";

    Paint paint = new Paint();
    Path path = new Path();
    private final String string = "A";

    public PathText(Context context) {
        super(context);
    }

    public PathText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d(TAG, "PathText: ");

        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setTextSize(R.dimen.text_size_20);

        for (int i = 0; i < 15; i++) {
            path.lineTo(i * 30, (float) Math.random() * 100);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(getResources().getColor(R.color.paint_bg, getContext().getTheme()));
//        canvas.translate(0, 20);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(getResources().getDimension(R.dimen.size_2));
        /**
         * TODO:canvas.drawTextOnPath 不显示text
         */
        canvas.drawTextOnPath(string, path, 100, 100, paint);
    }
}