package com.hades.example.android.widget.custom_view.shadow;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.hades.example.android.R;

public class ShadowedViewGroup extends ViewGroup {
    private float childBaseMargin;
    private float cornerRadius;
    private Paint mShadowPaint;
    private boolean isShaowEnable;
    private int shadowColor;
    private float shadowRadius;
    private float shadowLeftRadius;
    private float shadowRightRadius;
    private float shadowTopRadius;
    private float shadowBottomRadius;

    public ShadowedViewGroup(Context context) {
        this(context, null);
    }

    public ShadowedViewGroup(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowedViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View child = getChildAt(0);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int childMeasureWidth = child.getMeasuredWidth();
        int childMeasureHeight = child.getMeasuredHeight();
        child.layout((measuredWidth - childMeasureWidth) / 2, (measuredHeight - childMeasureHeight) / 2, (measuredWidth + childMeasureWidth) / 2, (measuredHeight + childMeasureHeight) / 2);
    }

    /**
     * 初始化信息变量
     */
    private void initView(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ShadowViewGroup);
        shadowColor = a.getColor(R.styleable.ShadowViewGroup_shadow_color, Color.RED);

        shadowRadius = a.getDimension(R.styleable.ShadowViewGroup_shadow_radius, 0);
        shadowLeftRadius = a.getDimension(R.styleable.ShadowViewGroup_shadow_leftRadius, shadowLeftRadius);
        shadowRightRadius = a.getDimension(R.styleable.ShadowViewGroup_shadow_rightRadius, shadowRightRadius);
        shadowTopRadius = a.getDimension(R.styleable.ShadowViewGroup_shadow_topRadius, shadowTopRadius);
        shadowBottomRadius = a.getDimension(R.styleable.ShadowViewGroup_shadow_bottomRadius, shadowBottomRadius);

        childBaseMargin = a.getDimension(R.styleable.ShadowViewGroup_child_base_margin, 0);
        cornerRadius = a.getDimension(R.styleable.ShadowViewGroup_container_cornerRadius, 0);

        isShaowEnable = a.getBoolean(R.styleable.ShadowViewGroup_shadow_enable, true);
        a.recycle();
        initShadowPaint();

    }

    private void initShadowPaint() {
        mShadowPaint = new Paint();
        mShadowPaint.setStyle(Paint.Style.FILL);
        mShadowPaint.setAntiAlias(true);
        mShadowPaint.setColor(Color.TRANSPARENT);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        mShadowPaint.setShadowLayer(shadowRadius, 0, 0, shadowColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (getChildCount() != 1) {
            throw new IllegalStateException("子View只能有一个");
        }
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        View child = getChildAt(0);
        LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
        int childBottomMargin = (int) (Math.max(childBaseMargin, layoutParams.bottomMargin) + 1);
        int childLeftMargin = (int) (Math.max(childBaseMargin, layoutParams.leftMargin) + 1);
        int childRightMargin = (int) (Math.max(childBaseMargin, layoutParams.rightMargin) + 1);
        int childTopMargin = (int) (Math.max(childBaseMargin, layoutParams.topMargin) + 1);
        int widthMeasureSpecMode;
        int widthMeasureSpecSize;
        int heightMeasureSpecMode;
        int heightMeasureSpecSize;
        if (widthMode == MeasureSpec.UNSPECIFIED) {
            widthMeasureSpecMode = MeasureSpec.UNSPECIFIED;
            widthMeasureSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        } else {
            if (layoutParams.width == LayoutParams.MATCH_PARENT) {
                widthMeasureSpecMode = MeasureSpec.EXACTLY;
                widthMeasureSpecSize = measuredWidth - childLeftMargin - childRightMargin;
            } else if (LayoutParams.WRAP_CONTENT == layoutParams.width) {
                widthMeasureSpecMode = MeasureSpec.AT_MOST;
                widthMeasureSpecSize = measuredWidth - childLeftMargin - childRightMargin;
            } else {
                widthMeasureSpecMode = MeasureSpec.EXACTLY;
                widthMeasureSpecSize = layoutParams.width;
            }
        }
        if (heightMode == MeasureSpec.UNSPECIFIED) {
            heightMeasureSpecMode = MeasureSpec.UNSPECIFIED;
            heightMeasureSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        } else {
            if (layoutParams.height == LayoutParams.MATCH_PARENT) {
                heightMeasureSpecMode = MeasureSpec.EXACTLY;
                heightMeasureSpecSize = measuredHeight - childBottomMargin - childTopMargin;
            } else if (LayoutParams.WRAP_CONTENT == layoutParams.height) {
                heightMeasureSpecMode = MeasureSpec.AT_MOST;
                heightMeasureSpecSize = measuredHeight - childBottomMargin - childTopMargin;
            } else {
                heightMeasureSpecMode = MeasureSpec.EXACTLY;
                heightMeasureSpecSize = layoutParams.height;
            }
        }
        measureChild(child, MeasureSpec.makeMeasureSpec(widthMeasureSpecSize, widthMeasureSpecMode), MeasureSpec.makeMeasureSpec(heightMeasureSpecSize, heightMeasureSpecMode));
        int parentWidthMeasureSpec = MeasureSpec.getMode(widthMeasureSpec);
        int parentHeightMeasureSpec = MeasureSpec.getMode(heightMeasureSpec);
        int height = measuredHeight;
        int width = measuredWidth;
        int childHeight = child.getMeasuredHeight();
        int childWidth = child.getMeasuredWidth();
        if (parentHeightMeasureSpec == MeasureSpec.AT_MOST) {
            height = childHeight + childTopMargin + childBottomMargin;
        }
        if (parentWidthMeasureSpec == MeasureSpec.AT_MOST) {
            width = childWidth + childRightMargin + childLeftMargin;
        }
        if (width < childWidth + 2 * childBaseMargin) {
            width = (int) (childWidth + 2 * childBaseMargin);
        }
        if (height < childHeight + 2 * childBaseMargin) {
            height = (int) (childHeight + 2 * childBaseMargin);
        }
        if (height != measuredHeight || width != measuredWidth) {
            setMeasuredDimension(width, height);
        }
    }

    static class LayoutParams extends MarginLayoutParams {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (isShaowEnable) {
            View child = getChildAt(0);
            int left = child.getLeft();
            int top = child.getTop();
            int right = child.getRight();
            int bottom = child.getBottom();
//            RectF rectF = new RectF(left, top, right, bottom);
            RectF rectF = new RectF(left - Math.abs(shadowRadius - shadowLeftRadius), top + Math.abs(shadowRadius - shadowTopRadius), right - Math.abs(shadowRadius - shadowRightRadius), bottom - Math.abs(shadowRadius - shadowBottomRadius));
            canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, mShadowPaint);
        }
        super.dispatchDraw(canvas);
    }
}
