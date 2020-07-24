package com.hades.example.android.widget._edittext;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.hades.example.android.R;
import com.hades.example.android.lib.base.BaseFragment;

public class TestEditTextFragment extends BaseFragment {
    private EditText editText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.widget_edittext, container, false);
        editText = view.findViewById(R.id.editText);
        view.findViewById(R.id.showAsPassword).setOnClickListener(view1 -> showAsPassword());
        return view;
    }

    private void showAsPassword() {
        // not work
//        if (editText.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
//            editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//        } else {
//            editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
//        }

        // work
        if (editText.getTransformationMethod() instanceof HideReturnsTransformationMethod) {
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
    }
}