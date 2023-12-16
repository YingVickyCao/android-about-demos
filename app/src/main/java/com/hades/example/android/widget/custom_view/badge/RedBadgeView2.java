package com.hades.example.android.widget.custom_view.badge;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;

import com.hades.example.android.R;
import com.hades.example.android.tools.DensityUtil;

// Ref https://blog.csdn.net/FlyPig_Vip/article/details/122408139
// 文字越多宽度越大,其他不发生改变
public class RedBadgeView2 extends androidx.appcompat.widget.AppCompatTextView {
    private static final String TAG = "RedBadgeView2";

    private int radius;

    private int borderWidth;

    private int borderColor;

    private int fillColor;
    private int padding;
    Paint borderPaint = new Paint();
    Paint fillPaint = new Paint();
    Paint textPint = new Paint();
    private Paint textPaint = new Paint();


    public RedBadgeView2(Context context) {
        this(context, null);
    }

    public RedBadgeView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RedBadgeView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RedBadgeView);
        borderWidth = typedArray.getDimensionPixelSize(R.styleable.RedBadgeView_border_width, 0);
        padding = typedArray.getDimensionPixelSize(R.styleable.RedBadgeView_padding, 0);
        borderColor = typedArray.getColor(R.styleable.RedBadgeView_border_color, Color.RED);
        fillColor = typedArray.getColor(R.styleable.RedBadgeView_fill_color, Color.RED);

        borderPaint.setAntiAlias(true);
        borderPaint.setColor(borderColor);

        fillPaint.setAntiAlias(true);
        fillPaint.setColor(fillColor);

        // 文字
        textPaint.setStrokeWidth(0);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.text_size_10));
        textPaint.setTypeface(Typeface.DEFAULT);
        textPaint.setAntiAlias(true);

        typedArray.recycle();
        setGravity(Gravity.CENTER);
        int i = DensityUtil.dp2px(context, 2);
        setPadding(i, i, i, i);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        if (measuredHeight >= measuredWidth) {
//            int max = Math.max(measuredWidth, measuredHeight);
            setMeasuredDimension(measuredHeight, measuredHeight);
        }

    }

    // 因为宽高都限制了 所以这里只能是内圈画border
    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        int halfWidth = getWidth() / 2;

        int length = getText().length();
        if (length == 1) {
            // 半径
            radius = Math.max(width, height) / 2;
            if (borderWidth > 0) {
                canvas.drawCircle(halfWidth, halfWidth, radius, borderPaint);
            }
            Log.d(TAG, "onDraw:length=1 ");
            canvas.drawCircle(halfWidth, halfWidth, radius - borderWidth, fillPaint);
        } else {
            // 半径选择高度的一半  可以自己写大小
            float textLength = textPaint.measureText(String.valueOf(getText()));
            Log.d(TAG, "onDraw:length=2," + "textLength=" + textLength + ",width=" + width + ",height=" + height);

            radius = height / 2;
            Log.d(TAG, "onDraw:borderWidth=" + borderWidth);
            if (borderWidth > 0) {
                canvas.drawRoundRect(0, 0, width, height, radius, radius, borderPaint);
            }
            float radius = getResources().getDimension(R.dimen.size_8);
            canvas.drawOval(new RectF(0, 0, width, height), fillPaint);
//            canvas.drawRoundRect(borderWidth, borderWidth, width - borderWidth, height - borderWidth, radius - borderWidth, radius - borderWidth, fillPaint);
        }
        super.onDraw(canvas);
    }
}