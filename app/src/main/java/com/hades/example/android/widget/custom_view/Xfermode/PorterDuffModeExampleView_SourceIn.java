package com.hades.example.android.widget.custom_view.Xfermode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.hades.example.android.R;

/**
 * PorterDuffXfermode:如何控制源图和目标图的合成。
 */
public class PorterDuffModeExampleView_SourceIn extends View {
    private static final String TAG = PorterDuffModeExampleView_SourceIn.class.getSimpleName();

    private final Paint paint = new Paint();
    private Bitmap destinationImage = BitmapFactory.decodeResource(getResources(), R.drawable.ic_composite_dst);
    private Bitmap sourceImage = BitmapFactory.decodeResource(getResources(), R.drawable.ic_composite_src);
    private Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

    public PorterDuffModeExampleView_SourceIn(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        canvas.drawBitmap(destinationImage, 0, 0, paint);
        paint.setXfermode(xfermode);
        canvas.drawBitmap(sourceImage, 0, 0, paint);
    }
}
