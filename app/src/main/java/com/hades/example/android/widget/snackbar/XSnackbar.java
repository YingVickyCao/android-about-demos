package com.hades.example.android.widget.snackbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DimenRes;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.hades.example.android.R;


public class XSnackbar {
    private static final String TAG = "XSnackbar";
    @NonNull
    private final Snackbar mSnackbar;

    private XSnackbar(@NonNull Snackbar snackbar) {
        mSnackbar = snackbar;
    }

    @NonNull
    public Context getContext() {
        return mSnackbar.getContext();
    }

    /**
     * Returns the {@link BaseTransientBottomBar}'s view.
     */
    @NonNull
    public View getView() {
        return mSnackbar.getView();
    }

    public void show() {
        mSnackbar.show();
    }

    public void dismiss() {
        mSnackbar.dismiss();
    }

    public boolean isShown() {
        mSnackbar.isShown();
        return false;
    }

    @NonNull
    public static XSnackbar make(@NonNull View view, @NonNull CharSequence text, @BaseTransientBottomBar.Duration int duration) {
        Snackbar snackbar = Snackbar.make(view, text, duration);
        return new XSnackbar(snackbar).setDefaultStyle();
    }

    @NonNull
    public static XSnackbar make(@NonNull Context context, @NonNull View view, @NonNull CharSequence text, @BaseTransientBottomBar.Duration int duration) {
        Snackbar snackbar = Snackbar.make(context, view, text, duration);
        return new XSnackbar(snackbar).setDefaultStyle();
    }

    @NonNull
    public static XSnackbar make(@NonNull View view, @StringRes int resId, @BaseTransientBottomBar.Duration int duration) {
        Snackbar snackbar = Snackbar.make(view, resId, duration);
        return new XSnackbar(snackbar).setDefaultStyle();
    }

    private XSnackbar setDefaultStyle() {
        setBottomMargin(R.dimen.size_80);
        setMargins(getContext().getResources().getDimensionPixelOffset(R.dimen.size_16),
                0,
                getContext().getResources().getDimensionPixelOffset(R.dimen.size_16),
                getContext().getResources().getDimensionPixelOffset(R.dimen.size_46));
        setBackgroundTint(getContext().getColor(R.color.m_0_light));
        setTextColor(getContext().getColor(R.color.m_0_dark));
        setActionTextColor(getContext().getColor(R.color.red));
        setTextSize(TypedValue.COMPLEX_UNIT_PX, getContent().getResources().getDimensionPixelSize(R.dimen.text_size_15));
        setActionTextSize(TypedValue.COMPLEX_UNIT_PX, getContent().getResources().getDimensionPixelSize(R.dimen.text_size_15));
        return this;
    }

    @NonNull
    public View getContent() {
        return mSnackbar.getView();
    }

    @NonNull
    public TextView getMessageView() {
        View view = mSnackbar.getView();
        Log.d(TAG, "setDefaultStyle: " + view);
        return view.findViewById(R.id.snackbar_text);
    }

    @NonNull
    public Button getActionView() {
        View view = mSnackbar.getView();
        return view.findViewById(R.id.snackbar_action);
    }

    @NonNull
    public XSnackbar setLeftMargin(int leftMargin) {
        View content = getContent();
        if (content.getLayoutParams() instanceof ViewGroup.MarginLayoutParams marginLayoutParams) {
            marginLayoutParams.leftMargin = leftMargin;
            content.setLayoutParams(marginLayoutParams);
        }
        return this;
    }

    @NonNull
    public XSnackbar setRightMargin(int rightMargin) {
        View content = getContent();
        if (content.getLayoutParams() instanceof ViewGroup.MarginLayoutParams marginLayoutParams) {
            marginLayoutParams.rightMargin = rightMargin;
            content.setLayoutParams(marginLayoutParams);
        }
        return this;
    }

    @NonNull
    public XSnackbar setBottomMargin(int bottomMargin) {
        View content = getContent();
        if (content.getLayoutParams() instanceof ViewGroup.MarginLayoutParams marginLayoutParams) {
            marginLayoutParams.bottomMargin = bottomMargin;
            content.setLayoutParams(marginLayoutParams);
        }
        return this;
    }

    @NonNull
    public XSnackbar setMargins(int left, int top, int right, int bottom) {
        View content = getContent();
        if (content.getLayoutParams() instanceof ViewGroup.MarginLayoutParams marginLayoutParams) {
            marginLayoutParams.setMargins(left, top, right, bottom);
            content.setLayoutParams(marginLayoutParams);
        }
        return this;
    }

    @NonNull
    public XSnackbar set(@DimenRes int id) {
        View content = getContent();
        if (content.getLayoutParams() instanceof ViewGroup.MarginLayoutParams marginLayoutParams) {
            marginLayoutParams.leftMargin = getContent().getResources().getDimensionPixelOffset(id);
            marginLayoutParams.rightMargin = getContent().getResources().getDimensionPixelOffset(id);
        }
        return this;
    }

    @NonNull
    public XSnackbar setText(@NonNull CharSequence message) {
        mSnackbar.setText(message);
        return this;
    }

    @NonNull
    public XSnackbar setText(@StringRes int resId) {
        mSnackbar.setText(resId);
        return this;
    }

    @NonNull
    public XSnackbar setAction(@StringRes int resId, View.OnClickListener listener) {
        mSnackbar.setAction(resId, listener);
        return this;
    }

    @NonNull
    public XSnackbar setAction(@Nullable CharSequence text, @Nullable final View.OnClickListener listener) {
        mSnackbar.setAction(text, listener);
        return this;
    }

    @BaseTransientBottomBar.Duration
    public int getDuration() {
        return mSnackbar.getDuration();
    }

    @NonNull
    public XSnackbar setTextColor(ColorStateList colors) {
        mSnackbar.setTextColor(colors);
        return this;
    }

    @NonNull
    public XSnackbar setTextColor(@ColorInt int color) {
        mSnackbar.setTextColor(color);
        return this;
    }

    @NonNull
    public XSnackbar setTextMaxLines(int maxLines) {
        mSnackbar.setTextMaxLines(maxLines);
        return this;
    }

    @NonNull
    public XSnackbar setTextSize(float size) {
        getMessageView().setTextSize(size);
        return this;
    }

    public XSnackbar setTextSize(int unit, float size) {
        getMessageView().setTextSize(unit, size);
        return this;
    }

    @NonNull
    public XSnackbar setActionTextColor(ColorStateList colors) {
        mSnackbar.setActionTextColor(colors);
        return this;
    }

    @NonNull
    public XSnackbar setActionTextSize(float size) {
        getActionView().setTextSize(size);
        return this;
    }

    public XSnackbar setActionTextSize(int unit, float size) {
        getActionView().setTextSize(unit, size);
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
