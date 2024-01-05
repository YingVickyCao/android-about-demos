package com.hades.example.android.widget.view_animator.adapterviewflipper.v2;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.AdapterViewFlipper;

public class MyAdapterViewFlipper extends AdapterViewFlipper {
    private static final String TAG = "MyAdapterViewFlipper";
    private float mTouchDownX;
    private float mTouchUpX;

    /**
     * Specifies if the animator should wrap from 0 to the end and vice versa
     * or have hard boundaries at the beginning and end
     */
    private boolean mLoopViews = true;
    /**
     * Specifies if this view automatically calls {@link #startFlipping()} when MotionEvent.ACTION_UP
     */
    private boolean isAutoFlip = true;

    public MyAdapterViewFlipper(Context context) {
        super(context);
    }

    public MyAdapterViewFlipper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyAdapterViewFlipper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyAdapterViewFlipper(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    /**
     * Specifies if the animator should wrap from 0 to the end and vice versa or have hard boundaries at the beginning and end
     */
    public boolean isLoopViews() {
        return mLoopViews;
    }

    /**
     * Returns true if the animator should wrap from 0 to the end and vice versa or have hard boundaries at the beginning and end
     */
    public void setLoopViews(boolean mLoopViews) {
        this.mLoopViews = mLoopViews;
    }

    /**
     * Set if this view automatically calls {@link #startFlipping()} when MotionEvent.ACTION_UP
     */
    public boolean isAutoFlip() {
        return isAutoFlip;
    }

    /**
     * Returns true if this view automatically calls {@link #startFlipping()}
     * when MotionEvent.ACTION_UP
     */
    public void setAutoFlip(boolean mAutoFlip) {
        this.isAutoFlip = mAutoFlip;
    }

    @Override
    public void showNext() {
        if (!isLast() || (isLast() && mLoopViews)) {
            super.showNext();
        }
    }

    @Override
    public void showPrevious() {
        if (!isFirst() || (isFirst() && mLoopViews)) {
            super.showPrevious();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
//        Log.d(TAG, "onTouchEvent: ");
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            Log.d(TAG, "onTouchEvent: ACTION_DOWN");
            mTouchDownX = ev.getX();
            return true;
        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            Log.d(TAG, "onTouchEvent: ACTION_UP");
            mTouchUpX = ev.getX();
            if (isMoveLeft()) {
                Log.d(TAG, "onTouchEvent: ACTION_UP - move left");
                showNext();
                if (isLast()) {
                    if (mLoopViews) {
                        if (isAutoFlip()) {
                            startFlipping();
                        }
                    } else {
                        stopFlipping();
                    }
                } else {
                    if (isAutoFlip()) {
                        startFlipping();
                    }
                }
                return true;
            } else if (isMoveRight()) {
                Log.d(TAG, "onTouchEvent: ACTION_UP - move right");
                showPrevious();
                if (isFirst()) {
                    if (mLoopViews) {
                        if (isAutoFlip()) {
                            startFlipping();
                        }
                    } else {
                        stopFlipping();
                    }
                } else {
                    if (isAutoFlip()) {
                        startFlipping();
                    }
                }
                return true;
            }
            return true;
        }
//        return false;
        return super.onTouchEvent(ev);
    }

    private boolean isMoveRight() {
        return mTouchUpX - mTouchDownX > 100;
    }

    private boolean isMoveLeft() {
        return mTouchDownX - mTouchUpX > 100;
    }

    private boolean isLast() {
        Log.d(TAG, "isLast: adapterCount=" + getAdapter().getCount());
        Log.d(TAG, "isLast: getDisplayedChild=" + getDisplayedChild());
        Log.d(TAG, "isLast: getChildCount=" + getChildCount());
        return getDisplayedChild() == (getAdapter().getCount() - 1);
    }

    private boolean isFirst() {
        return getDisplayedChild() == 0;
    }
}
