package com.hades.example.android.base;

import com.hades.example.android.R;
import com.hades.example.java.lib.MemoryCache;

// TODO: 2022/6/11
public class ThemeBaseFragment extends BaseFragment {
    protected void setTheme() {
        boolean isLightTheme = MemoryCache.getInstance().isRedTheme();
        if (isLightTheme) {
            getActivity().setTheme(R.style.AppTheme_Light);
        } else {
            getActivity().setTheme(R.style.AppTheme_Dark);
        }
    }
}
