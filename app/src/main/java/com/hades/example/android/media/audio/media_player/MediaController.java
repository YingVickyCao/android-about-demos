package com.hades.example.android.media.audio.media_player;

import android.util.Log;
import android.view.View;

import java.util.Formatter;
import java.util.Locale;

public class MediaController {
    Formatter mFormatter;
    StringBuilder mFormatBuilder;
    View mMediaPlayControlView;
    private static final int sDefaultTimeout = 3000;
    private IPlayer mPlayer;

    public MediaController() {
        init();
    }

    void init() {
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
    }

    public String stringForTime(long timeMs) {
        long totalSeconds = timeMs / 1000L;

        long seconds = totalSeconds % 60L;
        long minutes = (totalSeconds / 60L) % 60;
        long hours = totalSeconds / 3600L;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    public void setMediaPlayControlView(View mediaPlayControlView) {
        mMediaPlayControlView = mediaPlayControlView;
    }

    public void setPlayer(IPlayer player) {
        mPlayer = player;
    }

    private boolean isShowing() {
        return mMediaPlayControlView != null && mMediaPlayControlView.getVisibility() == View.VISIBLE;
    }

    public void show() {
        show(sDefaultTimeout);
    }

    public void show(int timeout) {
        if (null == mMediaPlayControlView) {
            return;
        }
        if (!isShowing()) {
            if (null != mPlayer) {
                mPlayer.setProgress();
            }
            showBar();
        }
        mMediaPlayControlView.post(mShowProgress);

        if (timeout != 0) {
            mMediaPlayControlView.removeCallbacks(mFadeOut);
            mMediaPlayControlView.postDelayed(mFadeOut, timeout);
        }
    }

    public void hide() {
        if (mMediaPlayControlView == null)
            return;

        if (isShowing()) {
            try {
                mMediaPlayControlView.removeCallbacks(mShowProgress);
            } catch (IllegalArgumentException ex) {
                Log.w("MediaController", "already removed");
            }
            hideBar();
        }
    }

    private final Runnable mFadeOut = this::hide;

    private final Runnable mShowProgress = new Runnable() {
        @Override
        public void run() {
            if (mPlayer == null) {
                return;
            }
            mPlayer.setProgress();
            int pos = mPlayer.getCurrentPosition();
            if (isShowing() && mPlayer.isPlaying()) {
                mMediaPlayControlView.postDelayed(mShowProgress, 1000 - (pos % 1000));
            }
        }
    };

    private void showBar() {
        mMediaPlayControlView.setVisibility(View.VISIBLE);
    }

    private void hideBar() {
        mMediaPlayControlView.setVisibility(View.GONE);
    }
}