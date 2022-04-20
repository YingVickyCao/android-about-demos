package com.hades.example.android.widget._edittext;

import androidx.annotation.IdRes;

public class ButtonUtils {

    private static int lastBtnId = -1;
    private static long lastClickTs;
    public static long MIN_CLICK_DELAY_MS = 200;

    public static boolean isFastClick() {
        return isFastClick(-1, MIN_CLICK_DELAY_MS);
    }

    public static boolean isFastClick(@IdRes int btnId) {
        return isFastClick(btnId, MIN_CLICK_DELAY_MS);
    }

    public static boolean isFastClick(@IdRes int btnId, long clickDurationMs) {
        long current = System.currentTimeMillis();
        long duration = current - lastClickTs;
        if ((lastBtnId == btnId) && (0 < duration) && (duration < clickDurationMs)) {
            return true;
        }
        lastBtnId = btnId;
        lastClickTs = current;
        return false;
    }
}