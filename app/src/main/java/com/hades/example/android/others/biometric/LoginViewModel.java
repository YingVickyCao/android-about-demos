package com.hades.example.android.others.biometric;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hades.example.android.R;
import com.hades.example.android.others.biometric.login_state.FailedLoginFormState;
import com.hades.example.android.others.biometric.login_state.LoginFormState;

public class LoginViewModel extends ViewModel {
    LiveData<LoginFormState> _loginFrom = new MutableLiveData<>();
    LiveData<LoginResult> _loginResult = new MutableLiveData<>();

    private void onLoginDataChanged(String userName, String password) {
        if (!isUserNameValid(userName)) {
            _loginFrom.setValue(new FailedLoginFormState().setUserNameError(R.string.biometric_invalid_username)) ;
        }
    }

    private boolean isUserNameValid(String userName) {
        if (null != userName && userName.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(userName).matches();
        } else {
            return !userName.isBlank();
        }
    }

    private void isPasswordValid() {
    }
}
