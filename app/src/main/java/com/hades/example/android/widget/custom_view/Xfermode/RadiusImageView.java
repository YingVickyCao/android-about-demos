package com.hades.example.android.widget.custom_view.Xfermode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.hades.example.android.R;

public class RadiusImageView extends View {
    Paint paint = new Paint();
    Bitmap dest = BitmapFactory.decodeResource(getResources(), R.drawable.ic_2);
    Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

    public RadiusImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 不加Layer，画不出圆形的图片
        int savedCount = canvas.saveLayer(0, 0, getWidth(), getHeight(), paint);

        // 移动坐标轴，否则画出的圆形图片不在中间
        canvas.translate((getWidth() - dest.getWidth()) / 2, (getHeight() - dest.getHeight()) / 2);
        // Rect 作为 Destination
        float radius = getResources().getDimension(R.dimen.size_8);
        canvas.drawRoundRect(new RectF(0, 0, dest.getWidth(), dest.getHeight()), 70, 70, paint);

        paint.setXfermode(xfermode);
        // 画出圆形图片
        canvas.drawBitmap(dest, 0, 0, paint);
        paint.setXfermode(null);

        canvas.restoreToCount(savedCount);
    }
}
