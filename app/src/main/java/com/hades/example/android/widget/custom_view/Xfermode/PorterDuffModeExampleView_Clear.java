package com.hades.example.android.widget.custom_view.Xfermode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.hades.example.android.R;

public class PorterDuffModeExampleView_Clear extends View {
    private static final String TAG = PorterDuffModeExampleView_Clear.class.getSimpleName();

    private final Paint paint = new Paint();
    private Bitmap destinationImage = BitmapFactory.decodeResource(getResources(), R.drawable.ic_composite_dst);
    private Bitmap sourceImage = BitmapFactory.decodeResource(getResources(), R.drawable.ic_composite_src);
    private Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);

    public PorterDuffModeExampleView_Clear(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int saveCount = canvas.saveLayer(0, 0, getWidth(), getHeight(), paint);

        canvas.drawBitmap(destinationImage, 0, 0, paint);
        paint.setXfermode(xfermode);
        canvas.drawBitmap(sourceImage, 0, 0, paint);
        paint.setXfermode(null);

        canvas.restoreToCount(saveCount);
    }
}
