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

public class XSnakebar {
    Snackbar mSnackbar;

    private XSnakebar(Snackbar snackbar) {
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
    public static XSnakebar make(@NonNull View view, @NonNull CharSequence text, @BaseTransientBottomBar.Duration int duration) {
        return new XSnakebar(Snackbar.make(view, text, duration));
    }

    @NonNull
    public static XSnakebar make(@NonNull Context context, @NonNull View view, @NonNull CharSequence text, @BaseTransientBottomBar.Duration int duration) {
        return new XSnakebar(Snackbar.make(context, view, text, duration));
    }

    @NonNull
    public static XSnakebar make(@NonNull View view, @StringRes int resId, @BaseTransientBottomBar.Duration int duration) {
        return new XSnakebar(Snackbar.make(view, resId, duration));
    }

    @NonNull
    public XSnakebar setText(@NonNull CharSequence message) {
        if (null != mSnackbar) {
            mSnackbar.setText(message);
        }
        return this;
    }

    @NonNull
    public XSnakebar setText(@StringRes int resId) {
        if (null != mSnackbar) {
            mSnackbar.setText(resId);
        }
        return this;
    }

    @NonNull
    public XSnakebar setAction(@StringRes int resId, View.OnClickListener listener) {
        if (null != mSnackbar) {
            mSnackbar.setAction(resId, listener);
        }
        return this;
    }

    @NonNull
    public XSnakebar setAction(@Nullable CharSequence text, @Nullable final View.OnClickListener listener) {
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
    public XSnakebar setTextColor(ColorStateList colors) {
        if (null != mSnackbar) {
            mSnackbar.setTextColor(colors);
        }
        return this;
    }

    @NonNull
    public XSnakebar setTextColor(@ColorInt int color) {
        if (null != mSnackbar) {
            mSnackbar.setTextColor(color);
        }
        return this;
    }

    @NonNull
    public XSnakebar setTextMaxLines(int maxLines) {
        if (null != mSnackbar) {
            mSnackbar.setTextMaxLines(maxLines);
        }
        return this;
    }

    @NonNull
    public XSnakebar setActionTextColor(ColorStateList colors) {
        if (null != mSnackbar) {
            mSnackbar.setActionTextColor(colors);
        }
        return this;
    }

    @NonNull
    public XSnakebar setMaxInlineActionWidth(@Dimension int width) {
        if (null != mSnackbar) {
            mSnackbar.setMaxInlineActionWidth(width);
        }
        return this;
    }

    @NonNull
    public XSnakebar setActionTextColor(@ColorInt int color) {
        if (null != mSnackbar) {
            mSnackbar.setActionTextColor(color);
        }
        return this;
    }

    @NonNull
    public XSnakebar setBackgroundTint(@ColorInt int color) {
        if (null != mSnackbar) {
            mSnackbar.setBackgroundTint(color);
        }
        return this;
    }

    @NonNull
    public XSnakebar setBackgroundTintList(@Nullable ColorStateList colorStateList) {
        if (null != mSnackbar) {
            mSnackbar.setBackgroundTintList(colorStateList);
        }
        return this;
    }

    @NonNull
    public XSnakebar setBackgroundTintMode(@Nullable PorterDuff.Mode mode) {
        if (null != mSnackbar) {
            mSnackbar.setBackgroundTintMode(mode);
        }
        return this;
    }

}
