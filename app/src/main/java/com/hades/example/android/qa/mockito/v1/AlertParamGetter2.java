package com.hades.example.android.qa.mockito.v1;

import android.app.Activity;

import com.hades.example.android.R;
import com.hades.example.android.qa.mockito.AlertParam;
import com.hades.example.android.qa.mockito.DefaultUIConfigure;

class AlertParamGetter2 {
    private Activity context;

    public AlertParamGetter2(Activity context) {
        this.context = context;
    }

    public void getAlertParam(AlertParam param, DefaultUIConfigure configure) {
        getDefaultAlertParam(param);
        setCustomAlertParam(param, configure);
    }

    public AlertParam getDefaultAlertParam(AlertParam param) {
        param.setTitle(getDefaultTitle());
        return param;
    }

    public void setCustomAlertParam(AlertParam param, DefaultUIConfigure configure) {
        if (null == configure) {
            return;
        }
        if (null == configure.getDefaultAlertConfigure()) {
            return;
        }
        if (null != configure.getDefaultAlertConfigure().getTitle()) {
            param.setTitle(configure.getDefaultAlertConfigure().getTitle());
        }
    }

    public String getDefaultTitle() {
        return context.getString(R.string.default_title);
    }
}
