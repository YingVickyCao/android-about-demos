package com.hades.example.android.qa;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class AlertParamGetterTest2 {

    private final String defaultTitle = "Default title";
    private final String customTitle = "Custom title";

    @Test
    public void getAlertParam_1() { // ok
        AlertParamGetter2 getter = mock(AlertParamGetter2.class);
        AlertParam param = new AlertParam();

        DefaultUIConfigure configure = null;
        when(getter.getDefaultTitle()).thenReturn(defaultTitle);

        when(getter.getDefaultAlertParam(param)).thenCallRealMethod();
        Mockito.doCallRealMethod().when(getter).setCustomAlertParam(param, configure);
        Mockito.doCallRealMethod().when(getter).getAlertParam(param, configure);

        getter.getAlertParam(param, configure);
        Assert.assertEquals(defaultTitle, param.getTitle());
    }

    @Test
    public void getAlertParam_2() { // ok
        AlertParamGetter2 getter = mock(AlertParamGetter2.class);
        AlertParam param = new AlertParam();

        DefaultUIConfigure configure = mock(DefaultUIConfigure.class);
        DefaultAlertConfigure defaultAlertConfigure = mock(DefaultAlertConfigure.class);
        when(configure.getDefaultAlertConfigure()).thenReturn(defaultAlertConfigure);
        when(defaultAlertConfigure.getTitle()).thenReturn(customTitle);

        when(getter.getDefaultTitle()).thenReturn(defaultTitle);

        when(getter.getDefaultAlertParam(param)).thenCallRealMethod();
        Mockito.doCallRealMethod().when(getter).setCustomAlertParam(param, configure);
        Mockito.doCallRealMethod().when(getter).getAlertParam(param, configure);

        getter.getAlertParam(param, configure);
        Assert.assertEquals(customTitle, param.getTitle());
    }
}