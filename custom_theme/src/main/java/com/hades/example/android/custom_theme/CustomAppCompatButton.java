package com.hades.example.android.custom_theme;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.ViewCompat;

import com.hades.example.android.lib.utils.CustomViewTools;

import java.util.Objects;

public class CustomAppCompatButton extends AppCompatButton {
    private static final String TAG = "CustomAppCompatButton";

    public CustomAppCompatButton(@NonNull Context context) {
        super(context);
        applyTheme(ThemeModules.THEME);
    }

    public CustomAppCompatButton(@NonNull Context context, @ThemeModules String mode) {
        super(context);
        applyTheme(mode);
    }

    public CustomAppCompatButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.buttonStyle);
    }

    public CustomAppCompatButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setStyle(context, attrs, defStyleAttr);
    }

    private void setStyle(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, new int[]{}, defStyleAttr, R.style.themeButtonStyle);
        CustomViewTools.printAttributeSet("CustomAppCompatButton", attrs);
        CustomViewTools.printTypedArray("CustomAppCompatButton", typedArray);
        String themeMode = CustomViewTools.getValueFromAttributeSet("CustomAppCompatButton", attrs);
        applyTheme(themeMode);
        typedArray.recycle();
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
