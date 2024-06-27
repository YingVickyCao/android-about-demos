package com.hades.example.android.qa;

import android.app.Activity;

import com.hades.example.android.R;

class AlertParamGetter {
    private Activity context;
    private DefaultUIConfigure configure;

    public AlertParamGetter(Activity context, DefaultUIConfigure configure) {
        this.context = context;
        this.configure = configure;
    }

    public AlertParam getAlertParam() {
        AlertParam param = getDefaultAlertParam();
        setCustomAlertParam(param);
        return param;
    }

    public AlertParam getDefaultAlertParam() {
        AlertParam param = new AlertParam();
        param.setTitle(getDefaultTitle());
        return param;
    }

    public void setCustomAlertParam(AlertParam param) {
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
