package com.hades.example.android.widget.edittext;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hades.example.android.R;

public class TestEditTextFragment extends Fragment {
    private static final String TAG = "TestEditTextFragment";
    private EditText editText;

    private FrameLayout editText2Container;
    private EditText editText2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.widget_edittext, container, false);
        editText = view.findViewById(R.id.editText);
        view.findViewById(R.id.showAsPassword).setOnClickListener(view1 -> showAsPassword());

        editText2Container = view.findViewById(R.id.editText2Container);
        editText2Container.getBackground().setLevel(getResources().getInteger(R.integer.edittext_bg_drawable_level_default));
        editText2Container.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d(TAG, "editText2Container onFocusChange: " + hasFocus);
            }
        });
        editText2 = view.findViewById(R.id.editText2);
        editText2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d(TAG, "editText2 onFocusChange: " + hasFocus);
                if (hasFocus) {
                    editText2Container.getBackground().setLevel(getResources().getInteger(R.integer.edittext_bg_drawable_level_focus));
                } else {
                    editText2Container.getBackground().setLevel(getResources().getInteger(R.integer.edittext_bg_drawable_level_default));
                }
            }
        });
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