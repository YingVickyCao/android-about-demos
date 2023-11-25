package com.hades.example.android.custom_theme

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.annotation.StyleRes
import androidx.appcompat.view.ContextThemeWrapper
import com.hades.example.android.lib.utils.CustomViewTools

object ThemeUtil {
    private const val TAG = "ThemeUtil"
    fun Context.toTheme(@StyleRes themeResId: Int): ContextThemeWrapper =
        ContextThemeWrapper(this, themeResId)

    fun Context.toTheme2(attrs: AttributeSet?): Context {
        try {
            if (null == attrs) {
                return this;
            }
            val themeMode: String =
                CustomViewTools.getValueFromAttributeSet("CustomAppCompatButton[2]", attrs);
            Log.d(TAG, "CustomAppCompatButton[2]:themeMode=" + themeMode);
            if (ThemeModules.THEME == themeMode) {
                return ContextThemeWrapper(this, R.style.themeButton)
            } else if (ThemeModules.LIGHT == themeMode) {
                return ContextThemeWrapper(this, R.style.lightButton)
            } else if (ThemeModules.DARK == themeMode) {
                return ContextThemeWrapper(this, R.style.darkButton)
            } else {
                return this;
            }
        } catch (ex: Exception) {
            Log.d(TAG, "toTheme: ex:", ex)
            return this
        }

    }

    fun Context.setTheme3(
        attrs: AttributeSet?, button: CustomAppCompatButton
    ): Context {
        val themeMode = CustomViewTools.getValueFromAttributeSet("CustomAppCompatButton", attrs)
        if (themeMode == ThemeModules.THEME) {
            button.setBackgroundResource(R.color.btn)
        } else if (themeMode == ThemeModules.DARK) {
            button.setBackgroundResource(R.color.btn_dark)
        } else if (themeMode == ThemeModules.LIGHT) {
            button.setBackgroundResource(R.color.btn_light)
        }
        return this
    }
}