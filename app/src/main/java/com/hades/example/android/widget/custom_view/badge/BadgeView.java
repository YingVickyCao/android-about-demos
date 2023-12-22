package com.hades.example.android.widget.custom_view.badge;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.hades.example.android.R;
import com.hades.utility.android.utils.ThemeUtils;


public class BadgeView extends AppCompatTextView {
    private static final String TAG = "BadgeView";

    private String mBadgeMode;
    private int mSize4IndicatorNoValue;
    private int mLayoutHeight4Indicator;
    private int mLayoutWidth4Indicator9; // [0,9]
    private int mLayoutWidth4Indicator99;// [10,99]
    private int mLayoutWidth4IndicatorValue999;// [100,999]
    private int mLayoutWidth4IndicatorGreatThan999;// 999+
    @ColorInt
    private int mTextColor;
    private int mTextSize;

    public BadgeView(Context context) {
        super(context);
    }

    public BadgeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BadgeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BadgeView, R.attr.BadgeViewStyle, R.style.BadgeView);
        mBadgeMode = typedArray.getString(R.styleable.BadgeView_badge_mode);
        ThemeUtils.printTypedArray(TAG, typedArray);
        mSize4IndicatorNoValue = typedArray.getLayoutDimension(R.styleable.BadgeView_size_when_indicator_no_value, getResources().getDimensionPixelSize(R.dimen.size_17));
        mLayoutHeight4Indicator = typedArray.getLayoutDimension(R.styleable.BadgeView_layout_height_when_indicator_has_value, getResources().getDimensionPixelSize(R.dimen.size_17));
        mLayoutWidth4Indicator9 = typedArray.getLayoutDimension(R.styleable.BadgeView_layout_width_when_indicator_value_0_to_9, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayoutWidth4Indicator99 = typedArray.getLayoutDimension(R.styleable.BadgeView_layout_width_when_indicator_value_10_to_99, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayoutWidth4IndicatorValue999 = typedArray.getLayoutDimension(R.styleable.BadgeView_layout_width_when_indicator_value_100_to_999, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayoutWidth4IndicatorGreatThan999 = typedArray.getLayoutDimension(R.styleable.BadgeView_layout_width_when_indicator_value_great_than_999, ViewGroup.LayoutParams.WRAP_CONTENT);
        mTextColor = typedArray.getColor(R.styleable.BadgeView_android_textColor, getResources().getColor(android.R.color.white, context.getTheme()));
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.BadgeView_android_textSize, getResources().getDimensionPixelSize(R.dimen.text_size_10));
        typedArray.recycle();

        setTextColor(mTextColor);
        ThemeUtils.setTextSizeUnitPx(this, mTextSize);
        setEllipsize(TextUtils.TruncateAt.END);
        setGravity(Gravity.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        onDrawStyle();
    }

    public void onDrawStyle() {
        if (mBadgeMode.equals("0")) {
            setBg4DotMode();
        } else if (mBadgeMode.equals("1")) {
            setBg4IndicatorMde();
        }
    }

    private void setBg4DotMode() {
        setBackgroundResource(R.drawable.ic_badge_17x17);
    }

    private void setBg4IndicatorMde() {
        String text = (String) getText();
        int textLength = null == text ? 0 : text.length();
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (textLength == 0) {
            layoutParams.width = mSize4IndicatorNoValue;
            layoutParams.height = mSize4IndicatorNoValue;
            setBackgroundResource(R.drawable.ic_badge_17x17);
            return;
        }

        if (textLength == 1) {
            layoutParams.width = mLayoutWidth4Indicator9;
            layoutParams.height = mLayoutHeight4Indicator;
            setBackgroundResource(R.drawable.ic_badge_17x17);
            return;
        }

        if (textLength == 2) {
            layoutParams.width = mLayoutWidth4Indicator99;
            layoutParams.height = mLayoutHeight4Indicator;
            setBackgroundResource(R.drawable.xml_badge_oval);
            return;
        }

        if (textLength == 3) {
            layoutParams.width = mLayoutWidth4IndicatorValue999;
            layoutParams.height = mLayoutHeight4Indicator;
            setBackgroundResource(R.drawable.ic_badge_34x17);
            return;
        }
        layoutParams.width = mLayoutWidth4IndicatorGreatThan999;
        layoutParams.height = mLayoutHeight4Indicator;
        setBackgroundResource(R.drawable.ic_badge_34x17);
    }
}