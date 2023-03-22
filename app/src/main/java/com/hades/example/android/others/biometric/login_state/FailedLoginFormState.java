package com.hades.example.android.others.biometric.login_state;

import android.content.Intent;

public class FailedLoginFormState extends LoginFormState {
    private Integer userNameError;
    private Integer passwordError;

    public FailedLoginFormState setPasswordError(Integer passwordError) {
        this.passwordError = passwordError;
        return this;
    }

    public FailedLoginFormState setUserNameError(Integer userNameError) {
        this.userNameError = userNameError;
        return this;
    }
}
