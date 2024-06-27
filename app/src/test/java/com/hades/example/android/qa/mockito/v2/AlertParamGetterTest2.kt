package com.hades.example.android.qa.mockito.v2

import com.hades.example.android.qa.mockito.AlertParam
import com.hades.example.android.qa.mockito.DefaultAlertConfigure
import com.hades.example.android.qa.mockito.DefaultUIConfigure
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class AlertParamGetterTest2 {
    private val defaultTitle = "Default title"
    private val customTitle = "Custom title"

    @Test
    fun alertParam_1() {// ok
        val getter = Mockito.mock(
            AlertParamGetter2::class.java
        )
        val param = AlertParam()

        val configure: DefaultUIConfigure? = null
        Mockito.`when`(getter.defaultTitle).thenReturn(defaultTitle)

        Mockito.`when`(getter.getDefaultAlertParam(param)).thenCallRealMethod()
        Mockito.doCallRealMethod().`when`(getter).setCustomAlertParam(param, configure)
        Mockito.doCallRealMethod().`when`(getter).getAlertParam(param, configure)

        getter.getAlertParam(param, configure)
        Assert.assertEquals(defaultTitle, param.title)
    }

    @Test
    fun alertParam_2() {
        // ok
        val getter = Mockito.mock(
            AlertParamGetter2::class.java
        )
        val param = AlertParam()

        val configure = Mockito.mock(DefaultUIConfigure::class.java)
        val defaultAlertConfigure = Mockito.mock(
            DefaultAlertConfigure::class.java
        )
        Mockito.`when`(configure.defaultAlertConfigure).thenReturn(defaultAlertConfigure)
        Mockito.`when`(defaultAlertConfigure.title).thenReturn(customTitle)

        Mockito.`when`(getter.defaultTitle).thenReturn(defaultTitle)

        Mockito.`when`(getter.getDefaultAlertParam(param)).thenCallRealMethod()
        Mockito.doCallRealMethod().`when`(getter).setCustomAlertParam(param, configure)
        Mockito.doCallRealMethod().`when`(getter).getAlertParam(param, configure)

        getter.getAlertParam(param, configure)
        Assert.assertEquals(customTitle, param.title)
    }
}