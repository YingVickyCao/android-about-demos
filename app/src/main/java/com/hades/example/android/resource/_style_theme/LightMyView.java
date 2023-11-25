package com.hades.example.android.resource._style_theme;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.hades.example.android.R;

@SuppressLint("AppCompatCustomView")
public class LightMyView extends MyViewBase {
    public LightMyView(Context context) {
        super(context);
    }

    public LightMyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LightMyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LightMyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @ColorInt
    protected int getColor() {
        return getResources().getColor(R.color.m_0_opacity_80_dark, getContext().getTheme());
    }

    @ColorInt
    protected int getColor2() {
        return getResources().getColor(R.color.m_0_dark, getContext().getTheme());
    }

    @Override
    protected int getAlphaValue() {
        return 204;
    }
}