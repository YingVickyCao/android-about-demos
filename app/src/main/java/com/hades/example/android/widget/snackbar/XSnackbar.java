package com.hades.example.android.widget.snackbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DimenRes;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.hades.example.android.R;
import com.hades.utility.android.utils.ThemeUtils;


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
        View layout = LayoutInflater.from(getContext()).inflate(R.layout.snackbar_view2, null);
        Snackbar.SnackbarLayout viewSnackbar = (Snackbar.SnackbarLayout) mSnackbar.getView();
//        viewSnackbar.removeAllViews();
        viewSnackbar.addView(layout, 1);
        viewSnackbar.getChildAt(0).setVisibility(View.GONE);


        // content layout
        setMargins(getContext().getResources().getDimensionPixelOffset(R.dimen.size_16), 0, getContext().getResources().getDimensionPixelOffset(R.dimen.size_16), getContext().getResources().getDimensionPixelOffset(R.dimen.size_46));
        setBackgroundTint(getContext().getColor(R.color.m_0_light));
        getContent().setPadding(0, 0, 0, 0);

        int size_10 = getContext().getResources().getDimensionPixelOffset(R.dimen.size_10);
        int size_16 = getContext().getResources().getDimensionPixelSize(R.dimen.size_16);
        int size_30 = getContent().getResources().getDimensionPixelOffset(R.dimen.size_30);

        // status icon
        ViewParent parentOfMessage = getMessageView().getParent();
        Log.d(TAG, "setDefaultStyle: " + parentOfMessage);
        if (parentOfMessage instanceof LinearLayout) {
            ImageView statusIcon = new ImageView(getContext());
            statusIcon.setBackgroundResource(R.drawable.ic_close);
            LinearLayout.LayoutParams statusIconLayoutParams = new LinearLayout.LayoutParams(size_16, size_16);
            statusIconLayoutParams.gravity = Gravity.CENTER_VERTICAL;
            statusIconLayoutParams.leftMargin = size_10;
            statusIcon.setLayoutParams(statusIconLayoutParams);

//            parent.addView(statusIcon, 0);

//            TextView message = getMessageView();
//            parent.removeView(getMessageView());
//            message.setPadding(0, 1, 1, 0);
//            message.setPaddingRelative(0, 1, 1, 0);
//            parent.addView(message, 0);

            TextView message = new TextView(getContext());
            message.setId(R.id.snackbar_text);
            message.setBackgroundColor(getContent().getResources().getColor(R.color.yellow, getContent().getContext().getTheme()));
//            LinearLayout.LayoutParams messageLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            LinearLayout.LayoutParams messageLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            LinearLayout.LayoutParams messageLayoutParams = new LinearLayout.LayoutParams(200, 200);
            messageLayoutParams.gravity = Gravity.CENTER_VERTICAL;
            messageLayoutParams.weight = 1;
            message.setMinHeight(size_30);
            message.setText("abcagaghjhatgayhhaahhahajgkah");
//            parent.addView(message, 0);
        }

        // message
//        getMessageView().setVisibility(View.GONE);
        setTextColor(getContext().getColor(R.color.m_0_dark));
        setTextSize(TypedValue.COMPLEX_UNIT_PX, getContent().getResources().getDimensionPixelSize(R.dimen.text_size_15));
        getMessageView().setBackgroundColor(getContent().getResources().getColor(R.color.green, getContent().getContext().getTheme()));
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getMessageView().getLayoutParams();
        layoutParams.leftMargin = size_10;
        getMessageView().setLayoutParams(layoutParams);
        getMessageView().setPadding(0, 1, 1, 0);
        getMessageView().setPaddingRelative(0, 1, 1, 0);
        getMessageView().setIncludeFontPadding(true);
        Log.d(TAG, "setDefaultStyle: " + ThemeUtils.px2dp(getContext(), 42)); // todo : 16dp

        // action button
        setActionTextColor(getContext().getColor(R.color.red));
        setActionTextSize(TypedValue.COMPLEX_UNIT_PX, getContent().getResources().getDimensionPixelSize(R.dimen.text_size_15));
        getActionView().setBackgroundColor(getContent().getResources().getColor(R.color.blue, getContent().getContext().getTheme()));
        getActionView().setPadding(0, 1, 1, 0);
        ViewGroup.MarginLayoutParams actionLayoutParams = (ViewGroup.MarginLayoutParams) getActionView().getLayoutParams();
        getActionView().setMinHeight(size_30);
        return this;
    }

    @NonNull
    public View getContent() {
//        return mSnackbar.getView().findViewById(R.id.snackbar_content_v2);
        return mSnackbar.getView();
    }

    @NonNull
    public TextView getMessageView() {
        View view = mSnackbar.getView();
        Log.d(TAG, "setDefaultStyle: " + view);
        return view.findViewById(R.id.snackbar_text_v2);
    }

    @NonNull
    public Button getActionView() {
        View view = mSnackbar.getView();
        return view.findViewById(R.id.snackbar_action_v2);
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
//        mSnackbar.setText(message);
        getMessageView().setText(message);
        return this;
    }

    @NonNull
    public XSnackbar setText(@StringRes int resId) {
//        mSnackbar.setText(resId);
        getMessageView().setText(resId);
        return this;
    }

    @NonNull
    public XSnackbar setAction(@StringRes int resId, View.OnClickListener listener) {
//        mSnackbar.setAction(resId, listener);
        getActionView().setText(resId);
        getActionView().setOnClickListener(listener);
        return this;
    }

    @NonNull
    public XSnackbar setAction(@Nullable CharSequence text, @Nullable final View.OnClickListener listener) {
//        mSnackbar.setAction(text, listener);
        getActionView().setText(text);
        getActionView().setOnClickListener(listener);
        return this;
    }

    @BaseTransientBottomBar.Duration
    public int getDuration() {
        return mSnackbar.getDuration();
    }

    @NonNull
    public XSnackbar setTextColor(ColorStateList colors) {
//        mSnackbar.setTextColor(colors);
        getMessageView().setTextColor(colors);
        return this;
    }

    @NonNull
    public XSnackbar setTextColor(@ColorInt int color) {
//        mSnackbar.setTextColor(color);
        getMessageView().setTextColor(color);
        return this;
    }

    @NonNull
    public XSnackbar setTextMaxLines(int maxLines) {
//        mSnackbar.setTextMaxLines(maxLines);
        getMessageView().setMaxLines(maxLines);
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
//        mSnackbar.setActionTextColor(colors);
        getActionView().setTextColor(colors);
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
        mSnackbar.setMaxInlineActionWidth(width);
        return this;
    }

    @NonNull
    public XSnackbar setActionTextColor(@ColorInt int color) {
//        mSnackbar.setActionTextColor(color);
        getActionView().setTextColor(color);
        return this;
    }

    @NonNull
    public XSnackbar setBackgroundTint(@ColorInt int color) {
//        mSnackbar.setBackgroundTint(color);
        getContent().setBackgroundColor(color);
        return this;
    }

    @NonNull
    public XSnackbar setBackgroundTintList(@Nullable ColorStateList colorStateList) {
//        mSnackbar.setBackgroundTintList(colorStateList);
        getContent().setBackgroundTintList(colorStateList);
        return this;
    }

    @NonNull
    public XSnackbar setBackgroundTintMode(@Nullable PorterDuff.Mode mode) {
//        mSnackbar.setBackgroundTintMode(mode);
        getContent().setBackgroundTintMode(mode);
        return this;
    }

}
