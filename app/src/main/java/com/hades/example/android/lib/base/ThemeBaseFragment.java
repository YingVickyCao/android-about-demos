package com.hades.example.android.lib.base;

import android.app.TaskStackBuilder;

import com.hades.example.android.R;
import com.hades.example.java.lib.MemoryCache;


public class ThemeBaseFragment extends BaseFragment {

    protected void setLightTheme() {
        boolean isRedTheme = MemoryCache.getInstance().isRedTheme();
        if (isRedTheme) {
            return;
        }
        MemoryCache.getInstance().useRedTheme(true);

        applyTheme();
    }

    protected void setDarkTheme() {
        boolean isRedTheme = MemoryCache.getInstance().isRedTheme();
        if (!isRedTheme) {
            return;
        }
        MemoryCache.getInstance().useRedTheme(false);

        applyTheme();
    }

    protected void applyTheme() {
        getActivity().finish();
        TaskStackBuilder.create(getActivity()).addNextIntent(getActivity().getIntent()).startActivities();
    }

    protected void setTheme() {
        boolean isLightTheme = MemoryCache.getInstance().isRedTheme();
        if (isLightTheme) {
            getActivity().setTheme(R.style.AppTheme_Light);
        } else {
            getActivity().setTheme(R.style.AppTheme_Dark);
        }
    }
}
