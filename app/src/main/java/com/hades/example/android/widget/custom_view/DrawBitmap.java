package com.hades.example.android.widget.custom_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.hades.example.android.R;

public class DrawBitmap extends View {
    private static final String TAG = DrawBitmap.class.getSimpleName();

    Paint paint;

    public DrawBitmap(Context context, AttributeSet set) {
        super(context, set);
        paint = new Paint();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        Log.d(TAG, "draw:" + "left=" + getLeft() + ",top=" + getTop() + ",right=" + getRight() + ",bottom=" + getBottom());

        canvas.drawColor(Color.BLACK);

        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_home);
        canvas.drawBitmap(bitmap, 200, 200, paint);
    }
}