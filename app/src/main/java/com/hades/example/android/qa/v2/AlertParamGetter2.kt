package com.hades.example.android.qa.v2

import android.app.Activity
import com.hades.example.android.R
import com.hades.example.android.qa.AlertParam
import com.hades.example.android.qa.DefaultUIConfigure

internal class AlertParamGetter2(private val context: Activity) {
    fun getAlertParam(param: AlertParam, configure: DefaultUIConfigure?) {
        getDefaultAlertParam(param)
        setCustomAlertParam(param, configure)
    }

    fun getDefaultAlertParam(param: AlertParam): AlertParam {
        param.title = defaultTitle
        return param
    }

    fun setCustomAlertParam(param: AlertParam, configure: DefaultUIConfigure?) {
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

    val defaultTitle: String
        get() = context.getString(R.string.default_title)
}
