package com.hades.example.android.widget.snackbar;

import static com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class XSnackbar {
    Snackbar mSnackbar;

    private XSnackbar(Snackbar snackbar) {
        mSnackbar = snackbar;
    }

    public void show() {
        if (null != mSnackbar) {
            mSnackbar.show();
        }
    }

    public void dismiss() {
        if (null != mSnackbar) {
            mSnackbar.dismiss();
        }
    }

    public boolean isShown() {
        if (null != mSnackbar) {
            mSnackbar.isShown();
        }
        return false;
    }

    @NonNull
    public static XSnackbar make(@NonNull View view, @NonNull CharSequence text, @BaseTransientBottomBar.Duration int duration) {
        return new XSnackbar(Snackbar.make(view, text, duration));
    }

    @NonNull
    public static XSnackbar make(@NonNull Context context, @NonNull View view, @NonNull CharSequence text, @BaseTransientBottomBar.Duration int duration) {
        return new XSnackbar(Snackbar.make(context, view, text, duration));
    }

    @NonNull
    public static XSnackbar make(@NonNull View view, @StringRes int resId, @BaseTransientBottomBar.Duration int duration) {
        return new XSnackbar(Snackbar.make(view, resId, duration));
    }

    @NonNull
    public XSnackbar setText(@NonNull CharSequence message) {
        if (null != mSnackbar) {
            mSnackbar.setText(message);
        }
        return this;
    }

    @NonNull
    public XSnackbar setText(@StringRes int resId) {
        if (null != mSnackbar) {
            mSnackbar.setText(resId);
        }
        return this;
    }

    @NonNull
    public XSnackbar setAction(@StringRes int resId, View.OnClickListener listener) {
        if (null != mSnackbar) {
            mSnackbar.setAction(resId, listener);
        }
        return this;
    }

    @NonNull
    public XSnackbar setAction(@Nullable CharSequence text, @Nullable final View.OnClickListener listener) {
        if (null != mSnackbar) {
            mSnackbar.setAction(text, listener);
        }
        return this;
    }

    @BaseTransientBottomBar.Duration
    public int getDuration() {
        if (null != mSnackbar) {
            mSnackbar.getDuration();
        }
        return LENGTH_SHORT;
    }

    @NonNull
    public XSnackbar setTextColor(ColorStateList colors) {
        if (null != mSnackbar) {
            mSnackbar.setTextColor(colors);
        }
        return this;
    }

    @NonNull
    public XSnackbar setTextColor(@ColorInt int color) {
        if (null != mSnackbar) {
            mSnackbar.setTextColor(color);
        }
        return this;
    }

    @NonNull
    public XSnackbar setTextMaxLines(int maxLines) {
        if (null != mSnackbar) {
            mSnackbar.setTextMaxLines(maxLines);
        }
        return this;
    }

    @NonNull
    public XSnackbar setActionTextColor(ColorStateList colors) {
        if (null != mSnackbar) {
            mSnackbar.setActionTextColor(colors);
        }
        return this;
    }

    @NonNull
    public XSnackbar setMaxInlineActionWidth(@Dimension int width) {
        if (null != mSnackbar) {
            mSnackbar.setMaxInlineActionWidth(width);
        }
        return this;
    }

    @NonNull
    public XSnackbar setActionTextColor(@ColorInt int color) {
        if (null != mSnackbar) {
            mSnackbar.setActionTextColor(color);
        }
        return this;
    }

    @NonNull
    public XSnackbar setBackgroundTint(@ColorInt int color) {
        if (null != mSnackbar) {
            mSnackbar.setBackgroundTint(color);
        }
        return this;
    }

    @NonNull
    public XSnackbar setBackgroundTintList(@Nullable ColorStateList colorStateList) {
        if (null != mSnackbar) {
            mSnackbar.setBackgroundTintList(colorStateList);
        }
        return this;
    }

    @NonNull
    public XSnackbar setBackgroundTintMode(@Nullable PorterDuff.Mode mode) {
        if (null != mSnackbar) {
            mSnackbar.setBackgroundTintMode(mode);
        }
        return this;
    }

}
