package com.hades.example.android.qa.mockito.v1;

import static org.mockito.Mockito.mock;

import android.app.Activity;

import com.hades.example.android.qa.mockito.AlertParam;
import com.hades.example.android.qa.mockito.DefaultAlertConfigure;
import com.hades.example.android.qa.mockito.DefaultUIConfigure;

import org.junit.Assert;
import org.junit.Test;

public class AlertParamGetterTest3 {

    private final String defaultTitle = "Default title";
    private final String customTitle = "Custom title";

    @Test
    public void getAlertParam_1() { // ok
        DefaultUIConfigure configure = null;
        com.hades.example.android.qa.mockito.v1.AlertParamGetter3 getter = new com.hades.example.android.qa.mockito.v1.AlertParamGetter3(mock(Activity.class), configure) {
            public String getDefaultTitle() {
                return defaultTitle;
            }
        };
        AlertParam param = getter.getAlertParam();
        Assert.assertEquals(defaultTitle, param.getTitle());
    }

    @Test
    public void getAlertParam_2() { // ok
        DefaultUIConfigure configure = new DefaultUIConfigure();
        configure.setDefaultAlertConfigure(new DefaultAlertConfigure());
        configure.getDefaultAlertConfigure().setTitle(customTitle);

        com.hades.example.android.qa.mockito.v1.AlertParamGetter3 getter = new com.hades.example.android.qa.mockito.v1.AlertParamGetter3(mock(Activity.class), configure) {
            public String getDefaultTitle() {
                return defaultTitle;
            }
        };
        AlertParam param = getter.getAlertParam();
        Assert.assertEquals(customTitle, param.getTitle());
    }
}