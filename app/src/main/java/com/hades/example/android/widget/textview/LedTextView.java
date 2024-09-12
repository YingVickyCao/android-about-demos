package com.hades.example.android.widget.textview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class LedTextView extends AppCompatTextView {


    public LedTextView(Context context) {
        super(context);
        init(context);
    }

    public LedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LedTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        setTypeface(getFont(context));
    }

    private Typeface getFont(Context context) {
        //1.设置字体，这里是为了设置与数字时钟对应的字体，如果只是要发亮效果可以不创建这个类
        return Typeface.createFromAsset(context.getAssets(), "fonts/digital.ttf");
    }
}