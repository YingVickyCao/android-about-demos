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
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.hades.example.android.R;

public class RoundImageView extends View {
    private static final String TAG = RoundImageView.class.getSimpleName();
    Paint paint = new Paint();
//        Bitmap dest = BitmapFactory.decodeResource(getResources(), R.drawable.ic_2);
    Bitmap dest = BitmapFactory.decodeResource(getResources(), R.drawable.ic_grid);
    Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

    int viewWith = -1;
    int viewHeight = -1;

    public RoundImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // onDraw:[canvas]width:798,height:798
        // onDraw:[dest]width:1470,height:1449

        // onDraw:[canvas]width:1400,height:1400
        // onDraw:[dest]width:798,height:952
        int with = getWidth();
        int height = getHeight();

        int destWidth = dest.getWidth();
        int destHeight = dest.getHeight();
        Log.d(TAG, "onDraw:[canvas]width:" + with + ",height:" + height);
        Log.d(TAG, "onDraw:[dest]width:" + destWidth + ",height:" + destHeight);

        int usedWidth = Math.min(with, destWidth);
        int usedHeight;
        if (height == 0) {
            usedHeight = destHeight;
        } else {
            usedHeight = Math.min(height, destHeight);
        }

        // 不加Layer，画不出圆形的图片
        int savedCount = canvas.saveLayer(0, 0, usedWidth, usedHeight, paint);

        // Circle 作为 Destination
        int radius = (usedWidth + usedHeight) / 2 / 2;
        canvas.drawCircle(usedWidth / 2, usedHeight / 2, radius, paint);
        paint.setXfermode(xfermode);
        // 画出圆形图片
        canvas.drawBitmap(dest, 0, 0, paint);
        paint.setXfermode(null);

        canvas.restoreToCount(savedCount);

        if (viewWith == -1) {
            Log.d(TAG, "onDraw: call requestLayout");
            viewWith = usedWidth;
            viewHeight = usedHeight;
            requestLayout();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (viewWith != -1) {
            Log.d(TAG, "onMeasure: viewWith:" + viewWith + ",viewHeight:" + viewHeight);
            setMeasuredDimension(viewWith, viewHeight);
        }
    }
}
