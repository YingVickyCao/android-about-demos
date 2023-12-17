package com.hades.example.android.custom_theme;

import static com.hades.example.android.custom_theme.ThemeModules.DARK;
import static com.hades.example.android.custom_theme.ThemeModules.LIGHT;
import static com.hades.example.android.custom_theme.ThemeModules.NON;
import static com.hades.example.android.custom_theme.ThemeModules.THEME;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({THEME, LIGHT, DARK, NON})
@Retention(RetentionPolicy.SOURCE)
public @interface ThemeModules {
    String THEME = "0";
    String LIGHT = "1";
    String DARK = "2";
    String NON = "3";
}
