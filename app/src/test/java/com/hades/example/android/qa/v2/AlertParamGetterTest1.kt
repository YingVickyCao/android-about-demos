package com.hades.example.android.qa.v2

import com.hades.example.android.qa.AlertParam
import com.hades.example.android.qa.DefaultAlertConfigure
import com.hades.example.android.qa.DefaultUIConfigure
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class AlertParamGetterTest1 {
    private val defaultTitle = "Default title"
    private val customTitle = "Custom title"

    @Test
    fun test_alertParam_1() {
        val getter = Mockito.mock(
            AlertParamGetter::class.java
        )
        Mockito.`when`(getter.configure).thenReturn(null)
        Mockito.`when`(getter.getDefaultTitle()).thenReturn(defaultTitle)

        Mockito.`when`(getter.getDefaultAlertParam()).thenCallRealMethod()
        Mockito.`when`(getter.getAlertParam()).thenCallRealMethod()

        val param = getter.getAlertParam()
        Assert.assertEquals(defaultTitle, param.title)
    }

    @Test
    fun alertParam_2() { // not ok
        // not ok
        val getter = Mockito.mock(
            AlertParamGetter::class.java
        )

        val param = AlertParam()

        val configure = Mockito.mock(DefaultUIConfigure::class.java)
        val defaultAlertConfigure = Mockito.mock(
            DefaultAlertConfigure::class.java
        )
        Mockito.`when`(configure.defaultAlertConfigure).thenReturn(defaultAlertConfigure)
        Mockito.`when`(defaultAlertConfigure.title).thenReturn(customTitle)
        Mockito.`when`(getter.getDefaultTitle()).thenReturn(defaultTitle)
        Mockito.`when`(getter.getDefaultAlertParam()).thenCallRealMethod()
        Mockito.doCallRealMethod().`when`(getter).getAlertParam()

        getter.getAlertParam()
        Assert.assertEquals(customTitle, param.title)

    }
}