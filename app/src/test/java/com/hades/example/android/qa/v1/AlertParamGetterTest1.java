package com.hades.example.android.qa.v1;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.hades.example.android.qa.AlertParam;
import com.hades.example.android.qa.DefaultAlertConfigure;
import com.hades.example.android.qa.DefaultUIConfigure;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class AlertParamGetterTest1 {

    private final String defaultTitle = "Default title";
    private final String customTitle = "Custom title";

    @Test
    public void getAlertParam_1() { // ok
        AlertParamGetter getter = mock(AlertParamGetter.class);
        when(getter.getConfigure()).thenReturn(null);
        when(getter.getDefaultTitle()).thenReturn(defaultTitle);

        when(getter.getDefaultAlertParam()).thenCallRealMethod();
        when(getter.getAlertParam()).thenCallRealMethod();

        AlertParam param = getter.getAlertParam();
        Assert.assertEquals(defaultTitle, param.getTitle());
    }

    @Test
    public void getAlertParam_2() { // not ok
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