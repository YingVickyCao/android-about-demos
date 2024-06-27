package com.hades.example.android.qa.mockito.v2

import android.app.Activity
import com.hades.example.android.R
import com.hades.example.android.qa.mockito.AlertParam
import com.hades.example.android.qa.mockito.DefaultUIConfigure

internal class AlertParamGetter(private val context: Activity, val configure: DefaultUIConfigure?) {
    fun getAlertParam(): AlertParam {
        val param = getDefaultAlertParam()
        setCustomAlertParam(param)
        return param
    }

    fun getDefaultAlertParam(): AlertParam {
        val param = AlertParam()
        param.title = getDefaultTitle()
        return param
    }

    fun setCustomAlertParam(param: AlertParam) {
        if (null == configure) {
            return
        }
        if (null == configure.defaultAlertConfigure) {
            return
        }
        if (null != configure.defaultAlertConfigure!!.title) {
            param.title = configure.defaultAlertConfigure!!.title
        }
    }

    fun getDefaultTitle(): String {
        return context.getString(R.string.default_title)
    }
}
