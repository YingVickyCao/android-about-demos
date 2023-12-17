package com.hades.example.android.widget.custom_view.badge;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;

import com.hades.example.android.R;
import com.hades.example.android.tools.DensityUtil;

// Ref https://blog.csdn.net/FlyPig_Vip/article/details/122408139
// 自适应宽高,文字越多宽高越大
public class RedBadgeView extends androidx.appcompat.widget.AppCompatTextView {
    private int radius;

    private int borderWidth;

    private int borderColor;

    private int fillColor;
    private int padding;

    public RedBadgeView(Context context) {
        this(context, null);
    }

    public RedBadgeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RedBadgeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RedBadgeView);
        borderWidth = typedArray.getDimensionPixelSize(R.styleable.RedBadgeView_border_width, 0);
        padding = typedArray.getDimensionPixelSize(R.styleable.RedBadgeView_padding, 0);
        borderColor = typedArray.getColor(R.styleable.RedBadgeView_border_color, Color.RED);
        fillColor = typedArray.getColor(R.styleable.RedBadgeView_fill_color, Color.RED);
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
        int max = Math.max(measuredWidth, measuredHeight);
        setMeasuredDimension(max, max);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        // 半径
        radius = Math.max(width, height) / 2;
        if (borderWidth > 0) {
            Paint borderPaint = new Paint();
            borderPaint.setColor(borderColor);
            canvas.drawCircle(getWidth() / 2, getWidth() / 2, radius, borderPaint);
        }
        Paint fillPaint = new Paint();
        fillPaint.setAntiAlias(true);
        fillPaint.setColor(fillColor);
        canvas.drawCircle(getWidth() / 2, getWidth() / 2, radius - borderWidth, fillPaint);
        super.onDraw(canvas);
    }
}