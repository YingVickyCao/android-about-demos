package com.hades.example.android.qa.mockito.v1;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.hades.example.android.qa.mockito.AlertParam;
import com.hades.example.android.qa.mockito.DefaultAlertConfigure;
import com.hades.example.android.qa.mockito.DefaultUIConfigure;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class AlertParamGetterTest2 {

    private final String defaultTitle = "Default title";
    private final String customTitle = "Custom title";

    @Test
    public void getAlertParam_1() { // ok
        com.hades.example.android.qa.mockito.v1.AlertParamGetter2 getter = mock(com.hades.example.android.qa.mockito.v1.AlertParamGetter2.class);
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
        com.hades.example.android.qa.mockito.v1.AlertParamGetter2 getter = mock(com.hades.example.android.qa.mockito.v1.AlertParamGetter2.class);
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