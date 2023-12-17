package com.hades.example.android.custom_theme;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatButton;

import com.hades.example.android.lib.utils.ThemeTools;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CustomAppCompatButton extends AppCompatButton {
    private static final String TAG = "CustomAppCompatButton";

    public CustomAppCompatButton(@NonNull Context context) {
        this(context, ThemeModules.THEME);
    }

    public CustomAppCompatButton(@NonNull Context context, @ThemeModules String mode) {
        super(context, null);
        applyTheme(mode);
    }

    public CustomAppCompatButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(new ContextThemeWrapper(context, R.style.themeButtonStyle), attrs, R.attr.buttonStyle);
        //        this(context, attrs, R.attr.buttonStyle);
    }

    public CustomAppCompatButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setStyle(context, attrs, defStyleAttr);
//        setStyle(attrs);
    }

    private void setStyle(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, new int[]{}, defStyleAttr, R.style.themeButtonStyle);
        ThemeTools.printAttributeSet("CustomAppCompatButton", attrs);
        ThemeTools.printTypedArray("CustomAppCompatButton", typedArray);
        String themeMode = ThemeTools.getValueFromAttributeSet("CustomAppCompatButton", attrs);
        applyTheme(themeMode, attrs);
        typedArray.recycle();
    }

    private void setStyle(@Nullable AttributeSet attrs) {
        String themeMode = ThemeTools.getValueFromAttributeSet("CustomAppCompatButton", attrs);
        applyTheme(themeMode, attrs);
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

    private void applyTheme(String themeMode, AttributeSet attributeSet) {
        Map<String, String> target = new HashMap<>();
        if (null != attributeSet) {
            target.put("background", null);
            target.put("backgroundTint", null);
            ThemeTools.getValuesFromAttributeSet(attributeSet, target);
        }
        if (null != target.get("background") || null != target.get("backgroundTint")) {
            return;
        }
        if (Objects.equals(themeMode, ThemeModules.THEME)) {
            setBackgroundResource(R.color.btn);
        } else if (Objects.equals(themeMode, ThemeModules.DARK)) {
            setBackgroundResource(R.color.btn_dark);
        } else if (Objects.equals(themeMode, ThemeModules.LIGHT)) {
            setBackgroundResource(R.color.btn_light);
        }
    }
}
