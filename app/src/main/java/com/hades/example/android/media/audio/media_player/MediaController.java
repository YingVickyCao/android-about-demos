package com.hades.example.android.media.audio.media_player;

import java.util.Formatter;
import java.util.Locale;

public class MediaController {
    Formatter mFormatter;
    StringBuilder mFormatBuilder;

    public MediaController() {
        init();
    }

    void init(){
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
    }

    public String stringForTime(long timeMs) {
        long totalSeconds = timeMs / 1000L;

        long seconds = totalSeconds % 60L;
        long minutes = (totalSeconds / 60L) % 60;
        long hours   = totalSeconds / 3600L;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }
}
