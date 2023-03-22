package com.hades.example.android.others.biometric;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.TextView;

import com.hades.example.android.R;
import com.hades.example.android.others.biometric.login_state.FailedLoginFormState;
import com.hades.example.android.others.biometric.login_state.LoginFormState;

/**
 * Fingerprint
 * Iris
 * Face
 */

public class TestBiometricActivity extends AppCompatActivity {
    private TextView mUserName;
    private TextView mPassword;
    private TextWatcher mTextWatcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biometric);

        mUserName = findViewById(R.id.userName);
        mPassword = findViewById(R.id.password);

        mTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                onLoginDataChanged(String.valueOf(mUserName.getText()), String.valueOf(mPassword.getText()));
            }
        };
        mUserName.addTextChangedListener(mTextWatcher);
        mPassword.addTextChangedListener(mTextWatcher);
    }

    private void onLoginDataChanged(String userName, String password) {
        LoginFormState state;
        if (!isUserNameValid(userName)) {
            state = new FailedLoginFormState(R.string.biometric_invalid_username, null);
        } else if ()
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