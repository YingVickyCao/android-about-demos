package com.hades.example.android.widget.custom_view.Xfermode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

public class PorterDuffModeExampleViewBackup extends View {
    private static final String TAG = PorterDuffModeExampleViewBackup.class.getSimpleName();
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG); // 抗锯齿标志

    private final PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER);
    private final Bitmap srcBitap;
    private final Bitmap dstBitmap;
    private final int screenWidth;
//    public PorterDuffXferModeExampleView(Context context) {
//        super(context);
//    }

    public PorterDuffModeExampleViewBackup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        screenWidth = setScreenWidth();
        srcBitap = setSrcBitmap(screenWidth / 3f, screenWidth / 3f);
        dstBitmap = setDstBitmap(screenWidth / 3f, screenWidth / 3f);
    }

//    public PorterDuffXferModeExampleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }
//
//    public PorterDuffXferModeExampleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }

    private int setScreenWidth() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        return displayMetrics.widthPixels;
    }

    private Bitmap setSrcBitmap(float width, float height) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xFFFECF41);

        Bitmap bitmap = Bitmap.createBitmap((int) width, (int) height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawCircle(width / 2, height / 2, width / 5, paint);
        return bitmap;
    }

    private Bitmap setDstBitmap(float width, float height) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xFF63A9FE);

        Bitmap bitmap = Bitmap.createBitmap((int) width, (int) height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawCircle(width / 2, height * 4 / 5, width * 4 / 5, paint);
        return bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(dstBitmap, 0, 0, paint);
        canvas.drawBitmap(srcBitap, screenWidth / 3f, 0, paint);

        Log.d(TAG, "onDraw:saved Count:" + canvas.getSaveCount());
        int layerCount = canvas.saveLayer(0, 0, getWidth(), getHeight(), null);
        Log.d(TAG, "onDraw:layerCount:" + layerCount);
    }
}
