package com.hades.example.android.custom_theme;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.hades.example.android.lib.utils.CustomViewTools;

import java.util.Objects;

public class CustomAppCompatButton2 extends AppCompatButton {
    private static final String TAG = "CustomAppCompatButton";

    public CustomAppCompatButton2(@NonNull Context context) {
        this(context, ThemeModules.THEME);
    }

    public CustomAppCompatButton2(@NonNull Context context, @ThemeModules String mode) {
        super(context, null);
        applyTheme(mode);
    }

    public CustomAppCompatButton2(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.buttonStyle);
    }

    public CustomAppCompatButton2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setStyle(context, attrs, defStyleAttr);
//        setStyle(attrs);
    }

    private void setStyle(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, new int[]{}, defStyleAttr, R.style.themeButtonStyle);
        CustomViewTools.printAttributeSet("CustomAppCompatButton", attrs);
        CustomViewTools.printTypedArray("CustomAppCompatButton", typedArray);
        String themeMode = CustomViewTools.getValueFromAttributeSet("CustomAppCompatButton", attrs);
        applyTheme(themeMode);
        typedArray.recycle();
    }

    private void setStyle(@Nullable AttributeSet attrs) {
        String themeMode = CustomViewTools.getValueFromAttributeSet("CustomAppCompatButton", attrs);
        applyTheme(themeMode);
    }

    private void applyTheme(String themeMode) {
        if (Objects.equals(themeMode, ThemeModules.THEME)) {
            setBackgroundResource(R.color.btn);
        } else if (Objects.equals(themeMode, ThemeModules.DARK)) {
            setBackgroundResource(R.color.btn_dark);
        } else if (Objects.equals(themeMode, ThemeModules.LIGHT)) {
            setBackgroundResource(R.color.btn_light);
        }
    }
}
