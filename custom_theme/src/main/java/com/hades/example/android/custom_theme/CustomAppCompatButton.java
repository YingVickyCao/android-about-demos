package com.hades.example.android.custom_theme;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.hades.example.android.lib.utils.CustomViewTools;

import java.util.Objects;

public class CustomAppCompatButton extends AppCompatButton {
    private static final String TAG = "CustomAppCompatButton";

    public CustomAppCompatButton(@NonNull Context context) {
        super(context);
    }

    public CustomAppCompatButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.buttonStyle);
    }
    
    public CustomAppCompatButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTheme(context, attrs, defStyleAttr);
    }

    private void setTheme(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, new int[]{}, defStyleAttr, R.style.themeButtonStyle);
        String themeMode = CustomViewTools.getValueFromAttributeSet("CustomAppCompatButton", attrs);
        if (Objects.equals(themeMode, ThemeModules.THEME)) {
            setBackgroundResource(R.color.btn);
        } else if (Objects.equals(themeMode, ThemeModules.DARK)) {
            setBackgroundResource(R.color.btn_dark);
        } else if (Objects.equals(themeMode, ThemeModules.LIGHT)) {
            setBackgroundResource(R.color.btn_light);
        }
        typedArray.recycle();
    }

}
