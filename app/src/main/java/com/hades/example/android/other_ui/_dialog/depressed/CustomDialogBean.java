package com.hades.example.android.other_ui._dialog.depressed;

import android.view.View;

public class CustomDialogBean {
    String message;
    String title;
    String positiveText;
    View.OnClickListener positiveClickListener;
    String negativeText;
    View.OnClickListener negativeClickListener;
    View customView;
    boolean isCancelable = true;

    public String getMessage() {
        return message;
    }

    public CustomDialogBean setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public CustomDialogBean setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getPositiveText() {
        return positiveText;
    }

    public CustomDialogBean setPositiveText(String positiveText) {
        this.positiveText = positiveText;
        return this;
    }

    public View.OnClickListener getPositiveClickListener() {
        return positiveClickListener;
    }

    public CustomDialogBean setPositiveClickListener(View.OnClickListener positiveClickListener) {
        this.positiveClickListener = positiveClickListener;
        return this;
    }

    public String getNegativeText() {
        return negativeText;
    }

    public CustomDialogBean setNegativeText(String negativeText) {
        this.negativeText = negativeText;
        return this;
    }

    public View.OnClickListener getNegativeClickListener() {
        return negativeClickListener;
    }

    public CustomDialogBean setNegativeClickListener(View.OnClickListener negativeClickListener) {
        this.negativeClickListener = negativeClickListener;
        return this;
    }

    public View getCustomView() {
        return customView;
    }

    public CustomDialogBean setCustomView(View customView) {
        this.customView = customView;
        return this;
    }

    public boolean isCancelable() {
        return isCancelable;
    }

    public CustomDialogBean setCancelable(boolean cancelable) {
        isCancelable = cancelable;
        return this;
    }
}
