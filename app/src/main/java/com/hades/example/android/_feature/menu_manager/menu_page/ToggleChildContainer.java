package com.hades.example.android._feature.menu_manager.menu_page;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

public class ToggleChildContainer extends LinearLayout {
    /**
     * true:    mExpandStatus children
     * false:   collapse children
     */
    private AtomicBoolean mExpandStatus = new AtomicBoolean(false);

    public ToggleChildContainer(Context context) {
        super(context);
    }

    public ToggleChildContainer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ToggleChildContainer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ToggleChildContainer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public boolean getExpandStatus() {
        return mExpandStatus.get();
    }

    public void setExpandStatus(boolean expand) {
        mExpandStatus.set(expand);
    }

    public void toggleExpandStatus() {
        mExpandStatus.set(!getExpandStatus());
        applyStatus();
    }

    public void applyStatus() {
        setVisibility(getExpandStatus() && getChildCount() > 0 ? VISIBLE : GONE);
    }
}