package com.hades.example.android.qa;

import android.app.Activity;

import com.hades.example.android.R;
import com.hades.example.android.app_component._activity._life_cycle.A;

class AlertParamGetter3 {
    private Activity context;
    private DefaultUIConfigure configure;

    public AlertParamGetter3(Activity context, DefaultUIConfigure configure) {
        this.context = context;
        this.configure = configure;
    }

    public AlertParam getAlertParam() {
        AlertParam param = getDefaultAlertParam();
        setCustomAlertParam(param, configure);
        return param;
    }

    public AlertParam getDefaultAlertParam() {
        AlertParam param = new AlertParam();
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
