package com.hades.example.android.resource._style_theme;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

public class CustomAppCompatButton extends AppCompatButton {
    public CustomAppCompatButton(@NonNull Context context) {
        super(context);
    }

//    buttonStyle

    public CustomAppCompatButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomAppCompatButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
