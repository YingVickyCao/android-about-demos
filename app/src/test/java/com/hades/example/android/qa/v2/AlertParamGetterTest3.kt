package com.hades.example.android.qa.v2

import android.app.Activity
import com.hades.example.android.R
import com.hades.example.android.qa.DefaultAlertConfigure
import com.hades.example.android.qa.DefaultUIConfigure
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

class AlertParamGetterTest3 {
    private val defaultTitle = "Default title"
    private val customTitle = "Custom title"

    @Test
    fun alertParam_1() { // ok
        val configure: DefaultUIConfigure? = null
        val getter: AlertParamGetter3 =
            object : AlertParamGetter3(Mockito.mock(Activity::class.java), configure) {
                override fun getDefaultTitle(): String? {
                    return defaultTitle
                }
            }
        val param = getter.getAlertParam()
        Assert.assertEquals(defaultTitle, param.title)
    }

    @Test
    fun alertParam_2() { // ok
        val configure = DefaultUIConfigure()
        configure.defaultAlertConfigure = DefaultAlertConfigure()
        configure.defaultAlertConfigure?.title = customTitle

        val getter: AlertParamGetter3 = object : AlertParamGetter3(
            Mockito.mock(
                Activity::class.java
            ), configure
        ) {
            override fun getDefaultTitle(): String? {
                return defaultTitle
            }
        }
        val param = getter.getAlertParam()
        Assert.assertEquals(customTitle, param.title)
    }
}