package com.hades.example.android.widget.custom_view.shadow;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.hades.example.android.R;

// TODO:右侧不真实
// TODO：shadow 区域超出Image 太多

/**
 * paint.setShadowLayer
 */
public class ShadowedFrameLayout extends FrameLayout {
    private int mShadowColor;
    private int mBgColor;
    private float mShadowLeftLimit;
    private float mShadowRightLimit;
    private float mShadowTopLimit;
    private float mShadowBottomLimit;
    private float mShadowRadius;
    private float mBgRadius;
    private boolean mInvalidateShadowOnSizeChanged = true;
    private boolean mForceInvalidateShadow = false;
    private Paint mShadowPaint;
    private Paint mBgPaint;

    public ShadowedFrameLayout(Context context) {
        super(context);
        init(context, null);
    }

    public ShadowedFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ShadowedFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setAttributes(context, attrs);
        int left;
        int right;
        int top;
        int bottom;
        if (mShadowLeftLimit != 0) {
            left = (int) mShadowLeftLimit;
        } else {
            left = 0;
        }
        if (mShadowTopLimit != 0) {
            top = (int) mShadowTopLimit;
        } else {
            top = 0;
        }


        if (mShadowRightLimit != 0) {
            right = (int) mShadowRightLimit;
        } else {
            right = 0;
        }

        if (mShadowBottomLimit != 0) {
            bottom = (int) mShadowBottomLimit;
        } else {
            bottom = 0;
        }

        setPadding(left, top, right, bottom);
        mShadowPaint = new Paint();
        mBgPaint = new Paint();
        setShadowPaint();
        setBgPaint();
    }

    private TypedArray getTypedArray(Context context, AttributeSet attributeSet, int[] attr) {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0);
    }

    private void setAttributes(Context context, AttributeSet attrs) {
        TypedArray attr = getTypedArray(context, attrs, R.styleable.ShadowViewGroup);
        try {
            mShadowLeftLimit = attr.getDimension(R.styleable.ShadowViewGroup_shadow_leftRadius, 0);
            mShadowRightLimit = attr.getDimension(R.styleable.ShadowViewGroup_shadow_rightRadius, 0);
            mShadowTopLimit = attr.getDimension(R.styleable.ShadowViewGroup_shadow_topRadius, 0);
            mShadowBottomLimit = attr.getDimension(R.styleable.ShadowViewGroup_shadow_bottomRadius, 0);
            mShadowRadius = attr.getDimension(R.styleable.ShadowViewGroup_shadow_radius, 0);
            mBgRadius = attr.getDimension(R.styleable.ShadowViewGroup_shadow_radius, 0);
            mShadowColor = attr.getColor(R.styleable.ShadowViewGroup_shadow_color, getResources().getColor(R.color.red, null));
            mBgColor = attr.getColor(R.styleable.ShadowViewGroup_bg_color, getResources().getColor(R.color.red, null));
        } finally {
            attr.recycle();
        }
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return 0;
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return 0;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0 && (getBackground() == null || mInvalidateShadowOnSizeChanged || mForceInvalidateShadow)) {
            mForceInvalidateShadow = false;
            setBackgroundCompat(w, h);
        }
    }

//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
//        if (mForceInvalidateShadow) {
//            mForceInvalidateShadow = false;
//            setBackgroundCompat(right - left, bottom - top);
//        }
//    }

    private void setBackgroundCompat(int w, int h) {
        Bitmap bitmap = createShadowBitmap(w, h);
        BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);
        setBackground(drawable);
    }

    private Bitmap createShadowBitmap(int shadowWidth, int shadowHeight) {
        Bitmap output = Bitmap.createBitmap(shadowWidth, shadowHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        RectF shadowRect = new RectF(mShadowLeftLimit, mShadowTopLimit, shadowWidth - mShadowRightLimit, shadowHeight - mShadowBottomLimit);
        canvas.drawRoundRect(shadowRect, 0, 0, mShadowPaint);
//        canvas.drawRoundRect(shadowRect, mBgRadius, mBgRadius, mBgPaint);
        return output;
    }

    private void setShadowPaint() {
        mShadowPaint.setAntiAlias(true);
        mShadowPaint.setColor(mShadowColor);
        mShadowPaint.setStyle(Paint.Style.FILL);

//        LinearGradient maskLinearGradient = new LinearGradient(0, 0, getWidth(), 0
//                , new int[]{mShadowColor, mShadowColor}
//                , new float[]{0, .9F}
//                , Shader.TileMode.CLAMP);
//        mShadowPaint.setShader(maskLinearGradient);

        if (!isInEditMode()) {
            // If mCornerRadius = 0, will remove shadow
            mShadowPaint.setShadowLayer(mShadowRadius, 0, 0, mShadowColor);
        }
    }

    private void setBgPaint() {
        mBgPaint.setColor(mBgColor);
    }
}